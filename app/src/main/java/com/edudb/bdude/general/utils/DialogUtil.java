package com.edudb.bdude.general.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.edudb.bdude.R;

public class DialogUtil {

    public static void getSingleButtonInstance(Context aContext, DialogInterface.OnClickListener aPositiveButtonClick,
                                               String aTitle, String aMessage, String aBtnText, boolean isCancelable) {
        if (aContext instanceof Activity && !((Activity) aContext).isDestroyed() && !(((Activity) aContext).isFinishing())) {

            if (!Utils.isNullOrWhiteSpace(aMessage)) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(aContext, R.style.AlertDialogCustom);
                alertDialogBuilder.setTitle(aTitle);
                alertDialogBuilder.setMessage(aMessage);
                TextView mDialogTextView = new TextView(aContext);
                mDialogTextView.setPadding(5, 50, 5, 5);
                mDialogTextView.setGravity(Gravity.END);
                alertDialogBuilder.setView(mDialogTextView);
                alertDialogBuilder.setCancelable(isCancelable).setPositiveButton(aBtnText, aPositiveButtonClick);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
    }
}
