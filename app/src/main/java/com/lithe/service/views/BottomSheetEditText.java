package com.lithe.service.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.volleySample.R;
import com.lithe.service.application.AppControl;

public abstract class BottomSheetEditText {

    public BottomSheetEditText(final Context context, String title, boolean isPassword, String buttonTitle, final boolean isCompulsory) {

        final Animation animIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        final Animation animOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
        animIn.setStartOffset(100);

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.bottom_sheet_edit_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onDialogDismiss();
            }
        });

        final RelativeLayout parentLayout = (RelativeLayout) dialog.findViewById(R.id.dialog_parent);

        parentLayout.startAnimation(animIn);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                parentLayout.startAnimation(animOut);
            }
        });

        final TextView textTitle = (TextView) dialog.findViewById(R.id.bottom_sheet_edit_text_title);
        final EditText editText = (EditText) dialog.findViewById(R.id.bottom_sheet_edit_edittext);
        final Button buttonOk = (Button) dialog.findViewById(R.id.bottom_sheet_edit_button_ok);
        final Button buttonCancel = (Button) dialog.findViewById(R.id.bottom_sheet_edit_button_cancel);
        if (isPassword) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        editText.setHint("Enter Remarks");
        textTitle.setText(title);
        buttonOk.setText(buttonTitle);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                parentLayout.startAnimation(animOut);
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String input = editText.getText().toString().trim();
                if (input.isEmpty() && isCompulsory) {
                    AppControl.toastManager.showToast(context, "Please Enter Value");
                } else {
                    animOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            dialog.dismiss();
                            onOkButtonClick(input);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    parentLayout.startAnimation(animOut);
                }
            }
        });

        dialog.show();
    }

    abstract public void onDialogDismiss();

    abstract public void onOkButtonClick(String input);

}
