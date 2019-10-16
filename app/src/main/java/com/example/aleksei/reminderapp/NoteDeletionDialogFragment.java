package com.example.aleksei.reminderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class NoteDeletionDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {


   @NonNull
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
               //.setTitle("Удалить заметку?")
               .setPositiveButton("Да", this)
               .setNegativeButton("Нет", this)
               .setMessage("Удалить заметку?");
       return adb.create();
   }


   public void onClick(DialogInterface dialog, int which) {

       switch (which) {
           case Dialog.BUTTON_POSITIVE:

               break;
           case Dialog.BUTTON_NEGATIVE:
               break;
       }

   }

   public void onDismiss(DialogInterface dialog) {
       super.onDismiss(dialog);
   }

   public void onCancel(DialogInterface dialog) {
       super.onCancel(dialog);
   }}
