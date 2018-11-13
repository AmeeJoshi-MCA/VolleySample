package com.lithe.service.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class EditTextDelayedTextWatcher implements TextWatcher {

    private Timer timer;
    private EditText editText;
    private int delay = 500;
    private boolean allowDelayedTextChangeCall = true;

    private DelayedTextChangeListener delayedTextChangeListener;

    public EditTextDelayedTextWatcher(EditText editText, int delay) {
        this.editText = editText;
        this.delay = delay;
        timer = new Timer();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (delayedTextChangeListener != null) {
            delayedTextChangeListener.beforeTextChanged(editText.getText().toString());
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (delayedTextChangeListener != null) {
            delayedTextChangeListener.onTextChanged(editText.getText().toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (allowDelayedTextChangeCall) {
                    editText.post(new Runnable() {
                        @Override
                        public void run() {
                            if (delayedTextChangeListener != null) {
                                delayedTextChangeListener.onDelayedTextChange(editText.getText().toString());
                            }
                        }
                    });
                } else {
                    allowDelayedTextChangeCall = true;
                }
            }
        }, delay);
    }

    public void setAllowDelayedTextChangeCall(boolean allowDelayedTextChangeCall) {
        this.allowDelayedTextChangeCall = allowDelayedTextChangeCall;
    }

    public EditTextDelayedTextWatcher setDelayedTextChangeListener(DelayedTextChangeListener delayedTextChangeListener) {
        this.delayedTextChangeListener = delayedTextChangeListener;
        return this;
    }

    public interface DelayedTextChangeListener {
        void beforeTextChanged(String text);

        void onTextChanged(String text);

        void onDelayedTextChange(String text);
    }

}
