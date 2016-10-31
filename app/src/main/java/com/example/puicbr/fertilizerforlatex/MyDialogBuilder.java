package com.example.puicbr.fertilizerforlatex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by nplayground on 11/1/16.
 */

public class MyDialogBuilder {

    public static Dialog CreateDialog(Context context, String title, String message) {

        String ok = context.getResources().getString(android.R.string.ok);

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return alertDialog;
    }

    public static Dialog CreateDialog(Context context, String message) {
        return CreateDialog(context, "Information", message);
    }

    public static Dialog CreateConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Confirm")
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, onClickListener)
                .setNegativeButton(android.R.string.no, null).create();

        return alertDialog;
    }

}
