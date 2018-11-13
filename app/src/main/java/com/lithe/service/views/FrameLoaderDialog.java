package com.lithe.service.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.volleySample.R;


public class FrameLoaderDialog {

    private Context context;

    private Dialog dialog;

    private View viewRotateOuter;
    private View viewRotateInner;

    private Animation animationOuter;
    private Animation animationInner;

    public FrameLoaderDialog(Context context) {
        this.context = context;

        dialog = new Dialog(context);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.frame_loader_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewRotateOuter = dialog.findViewById(R.id.frame_loader_view_image_outer);
        viewRotateInner = dialog.findViewById(R.id.frame_loader_view_image_inner);

        viewRotateOuter.setBackground(getDrawableWithColor(R.drawable.loader_image_thin, R.color.white));
        viewRotateInner.setBackground(getDrawableWithColor(R.drawable.loader_image_thin, R.color.white));

        animationOuter = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        animationOuter.setDuration(800);
        animationOuter.setInterpolator(new LinearInterpolator());
        animationOuter.setRepeatMode(1);
        animationOuter.setRepeatCount(-1);

        animationInner = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        animationInner.setDuration(1000);
        animationInner.setInterpolator(new LinearInterpolator());
        animationInner.setRepeatMode(1);
        animationInner.setRepeatCount(-1);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                viewRotateOuter.clearAnimation();
                viewRotateInner.clearAnimation();
            }
        });
    }

    public void show() {
        viewRotateOuter.clearAnimation();
        viewRotateInner.clearAnimation();

        viewRotateOuter.setAnimation(animationOuter);
        viewRotateInner.setAnimation(animationInner);

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    private Drawable getDrawableWithColor(int drawableID, int colorID) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableID);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        drawable = new BitmapDrawable(context.getResources(), bitmap);
        drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, colorID), PorterDuff.Mode.SRC_IN));
        return drawable;
    }
}
