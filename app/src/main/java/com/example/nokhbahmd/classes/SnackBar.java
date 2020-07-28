package com.example.nokhbahmd.classes;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBar {

    public static void SnackBarMessage(View view, CharSequence message, int duration, int color) {
        //****************************** SnackBar *************************
        /**/
        Snackbar snack = Snackbar.make(view, message, duration);
        /**/
        snack.setAction("Ok", View::onCancelPendingInputEvents);//

        /**/
        snack.setActionTextColor(Color.WHITE);
        /**/
        View snackview = snack.getView();
        /**/
        snackview.setBackgroundColor(color);
        /**/
        snack.show();
        //*****************************************************************
    }
}
