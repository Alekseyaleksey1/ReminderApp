package com.reminderapp.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import com.reminderapp.extensions.getHour
import com.reminderapp.extensions.getMinute
import com.reminderapp.mvp.data.entity.Note
import com.reminderapp.ui.activity.HostActivity
import com.reminderapp.util.Constants
import java.util.*


class NoteAlarmReceiver : BroadcastReceiver() {

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O &&
            notificationManager.getNotificationChannel(Constants.ALARM_CHANNEL_ID) == null
        ) {

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            notificationManager.createNotificationChannel(NotificationChannel(
                Constants.ALARM_CHANNEL_ID,
                Constants.ALARM_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
                vibrationPattern =
                    longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes) // todo звук
            })
        }
    }

    override fun onReceive(context: Context, intent: Intent) {


        //   if (intent.action == "action") {


        val notificationManager = MainApplication.applicationContext()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O && notificationManager.getNotificationChannel(
                Constants.ALARM_CHANNEL_ID
            ) == null
        ) {*/

        /*val notificationChannel = NotificationChannel(
            Constants.ALARM_CHANNEL_ID,
            Constants.ALARM_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.BLUE
            enableVibration(true)
            vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            //setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // todo звук
        }*/

        /* notificationChannel.enableLights(true)
         notificationChannel.lightColor = Color.BLUE
         notificationChannel.enableVibration(true)
         //notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // todo звук
         notificationChannel.vibrationPattern =
             longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)*/


        /*notificationManager.createNotificationChannel(NotificationChannel(
            Constants.ALARM_CHANNEL_ID,
            Constants.ALARM_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.BLUE
            enableVibration(true)
            vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            //setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // todo звук
        })*/

        //}


        //   Log.d("receiver", "received")
        //  Log.d("receiverhasnote", intent.hasExtra(Constants.NOTE_KEY).toString())
        //  Log.d("receiverhasnote", intent.hasExtra("string").toString())

        val note = GsonBuilder().create()
            .fromJson(intent.extras?.getString(Constants.NOTE_KEY), Note::class.java)

        //intent.extras?.getString(Constants.NOTE_KEY) //getSerializableExtra(/*Constants.NOTE_KEY*/"noteKey") as Note? //todo тут нота нулл на телефоне!

        //Log.d("receiver", note?.id.toString())
        val intentToStartActivity = PendingIntent.getActivity(//todo name
            context,
            1,//todo requestcode
            Intent(context, HostActivity::class.java).apply {
                putExtra(
                    Constants.STARTED_FROM_RECEIVER_FLAG,//todo hardcoded
                    true
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                action = (System.currentTimeMillis()).toString()//todo можно ли обойти?
            },
            0
        )//todo переход на детаиледФрагмент

        val intentToClearNotification = PendingIntent.getBroadcast(//todo name
            context,
            note?.id!!,//todo requestcode
            Intent(context, NotificationCancelReceiver::class.java).apply {
             //   Log.d("intentToClearNotification", note?.id.toString())
                putExtra(
                    Constants.NOTIFICATION_ID,//todo hardcoded string
                    note.id
                )
            },
            0
        )


        /*val restartAction: NotificationCompat.Action = NotificationCompat.Action(
            R.drawable.ic_launcher, "title",
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                MainApplication.applicationContext(),
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            )
        )*/

        /*val cancelAction = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher,
            "Отменить", intentToClearNotification
        )
            .build()*/

        val collapsedView = RemoteViews(//todo собрать вместе с последующими обращениями
            context.packageName, R.layout.notification_alarm
        )//.setOnClickPendingIntent(R.)

        collapsedView.setTextViewText(R.id.notification_alarm_tv_text, note.noteText)
        collapsedView.setTextViewText(
            R.id.notification_alarm_tv_date,
            String.format(
                Locale.US,
                context.getString(R.string.format_time),
                /*Integer.valueOf(*//*DateWorker.getHour(note.noteDate)*/
                note.noteDate.getHour()/*)*/,//todo надо чет сделать и давать сюда инты
                /*Integer.valueOf(*//*DateWorker.getMinute(note.noteDate)*/
                note.noteDate.getMinute()/*)*/
            )
        )//todo format
        collapsedView.setOnClickPendingIntent(
            R.id.notification_alarm_btn_cancel,
            intentToClearNotification
        )


        val notification: Notification =
            NotificationCompat.Builder(
                MainApplication.applicationContext(),
                Constants.ALARM_CHANNEL_ID
            )
                .setSmallIcon(R.mipmap.ic_transparent_alarmclock)//todo отображение
                //  .setContentTitle(note?.noteDate.toString())
                //  .setContentText(note?.noteText)
                .setContentIntent(intentToStartActivity)
                .setFullScreenIntent(intentToStartActivity, true)
                .setPriority(NotificationCompat.PRIORITY_MAX)

                .setCustomContentView(collapsedView)
                .setOngoing(true)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())

                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                /*.setLargeIcon(
                    BitmapFactory.decodeResource(
                        MainApplication.applicationContext().resources,
                        R.mipmap.ic_transparent_alarmclock//todo отображение
                    )
                )*/

                /*.addAction(*//*restartAction*//*
                        R.mipmap.ic_alarmclock,
                        context.getString(R.string.notificaton_check),
                        intentToStartActivity
                    )//todo hardcoded*/
                // .addAction(
                //     cancelAction

                /*
                R.mipmap.ic_cancelalarm,
                context.getString(R.string.notification_cancel),
                intentToClearNotification*/
                //    )//todo hardcoded
                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                // .setOnlyAlertOnce(true)
                // .setStyle(
                //       androidx.media.app.NotificationCompat.MediaStyle()
                //          .setShowActionsInCompactView(0)
                //  )
                .build()

        //NotificationManagerCompat.from(MainApplication.applicationContext())


        //notification.flags = Notification.FLAG_INSISTENT and Notification.FLAG_AUTO_CANCEL and Notification.FLAG_ONGOING_EVENT
        //Log.d("debug", "noteid - ${note?.id!!}")
        notificationManager.notify(
            note.id,
            notification
        )//todo id //todo норм рандом через id?

        //val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        //  val r = RingtoneManager.getRingtone(MainApplication.applicationContext(), uri)
        // r.play()

        //r.stop()
        //   }
    }

}