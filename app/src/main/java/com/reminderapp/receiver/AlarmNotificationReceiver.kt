package com.reminderapp.receiver

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.gson.GsonBuilder
import com.reminderapp.MainApplication
import com.reminderapp.R
import com.reminderapp.extensions.getDayOfMonth
import com.reminderapp.extensions.getHour
import com.reminderapp.extensions.getMinute
import com.reminderapp.mvp.data.NoteRepository
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.ui.activity.HostActivity
import com.reminderapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AlarmNotificationReceiver : BroadcastReceiver() {

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {

        notificationManager = MainApplication.applicationContext()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()

        when (intent.action) {
            Constants.ACTION_SHOW_NOTIFICATION -> showNotification(context, intent)
            Constants.ACTION_CANCEL_NOTIFICATION -> cancelNotification(intent)
            Intent.ACTION_BOOT_COMPLETED -> restartAllAlarms()
        }
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O &&
            notificationManager.getNotificationChannel(Constants.ALARM_CHANNEL_ID) == null
        ) {

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val notificationChannel = NotificationChannel(
                Constants.ALARM_CHANNEL_ID,
                Constants.ALARM_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
                vibrationPattern =
                    longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                setSound(
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE),
                    audioAttributes
                )
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun showNotification(context: Context, intent: Intent) {
        val note = GsonBuilder().create()
            .fromJson(intent.extras?.getString(Constants.NOTE_KEY), Note::class.java)
        val intentToStartActivity = PendingIntent.getActivity(
            context,
            1,
            Intent(context, HostActivity::class.java).apply {
                putExtra(
                    Constants.STARTED_FROM_RECEIVER_FLAG,
                    true
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                action = (System.currentTimeMillis()).toString()
            },
            0
        )

        val intentToClearNotification = PendingIntent.getBroadcast(
            context,
            note?.id!!,
            Intent(
                context, AlarmNotificationReceiver::class.java
            ).apply {
                action = Constants.ACTION_CANCEL_NOTIFICATION
                putExtra(
                    Constants.NOTIFICATION_ID,
                    note.id
                )
            },
            0
        )

        val collapsedView = RemoteViews(
            context.packageName, R.layout.notification_alarm
        )

        collapsedView.setTextViewText(R.id.notification_alarm_tv_text, note.noteText)
        collapsedView.setTextViewText(
            R.id.notification_alarm_tv_date,
            String.format(
                Locale.US,
                context.getString(R.string.format_time),
                note.noteDate.getHour(),
                note.noteDate.getMinute()
            )
        )
        collapsedView.setOnClickPendingIntent(
            R.id.notification_alarm_btn_cancel,
            intentToClearNotification
        )


        val notification: Notification =
            NotificationCompat.Builder(
                MainApplication.applicationContext(),
                Constants.ALARM_CHANNEL_ID
            )
                .setSmallIcon(R.mipmap.ic_transparent_alarmclock)
                .setContentIntent(intentToStartActivity)
                .setFullScreenIntent(intentToStartActivity, true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCustomContentView(collapsedView)
                .setOngoing(true)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                .setLights(Color.BLUE, 100, 200)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                .build()

        notificationManager.notify(
            note.id,
            notification
        )
    }

    private fun cancelNotification(intent: Intent) {
        notificationManager.cancel(
            intent.getIntExtra(Constants.NOTIFICATION_ID, 0)
        )
    }

    private fun restartAllAlarms() {
        val repository = NoteRepository()
        CoroutineScope(Default).launch {
            val allNotes = repository.getNotes()
            withContext(Main) {
                val alarmManager: AlarmManager =
                    MainApplication.applicationContext()
                        .getSystemService(Context.ALARM_SERVICE) as AlarmManager
                for (note in allNotes) {
                    if (note.noteDate < Calendar.getInstance().time) {
                        if (Calendar.getInstance().time.getDayOfMonth() < note.noteDate.getDayOfMonth()) {
                            withContext(Default) { repository.deleteNoteFromDatabase(note) }
                        }
                    } else {
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            note.noteDate.time,
                            PendingIntent.getBroadcast(
                                MainApplication.applicationContext(),
                                note.id,
                                Intent(
                                    MainApplication.applicationContext(),
                                    AlarmNotificationReceiver::class.java
                                ).apply {
                                    putExtra(
                                        Constants.NOTE_KEY,
                                        GsonBuilder().create().toJson(note)
                                    )
                                    action = Constants.ACTION_SHOW_NOTIFICATION
                                },
                                0
                            )
                        )
                    }
                }
            }
        }
    }
}