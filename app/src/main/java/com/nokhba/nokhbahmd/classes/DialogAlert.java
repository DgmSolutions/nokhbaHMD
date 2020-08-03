package com.nokhba.nokhbahmd.classes;

import android.app.Activity;
import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;
import com.nokhba.nokhbahmd.MainActivity;
import com.nokhba.nokhbahmd.R;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

public class DialogAlert {
    public static void ShowEndDialog(Activity activity){
        BottomSheetMaterialDialog mDialog = new BottomSheetMaterialDialog.Builder(activity).setAnimation(R.raw.sended)
                .setTitle("تم الإرسال بنجاح")
                .setMessage("ستقوم النخبة بالاتصال بك في أقرب وقت")
                .setCancelable(true)
                .setPositiveButton("حسنا", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .build();
        LottieAnimationView animationView = mDialog.getAnimationView();
        animationView.setPadding(100, 100, 100, 100);
        animationView.loop(false);
        // Show Dialog
        mDialog.show();
    }
}
