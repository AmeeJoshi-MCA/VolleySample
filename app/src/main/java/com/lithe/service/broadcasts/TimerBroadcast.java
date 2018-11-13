package com.lithe.service.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimerBroadcast extends BroadcastReceiver {

    private static OnTimeTickListener onTimeTickListener;

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(Intent.ACTION_TIME_TICK)) {
            if (onTimeTickListener != null) {
                onTimeTickListener.onTimeTick();
            }
        }
    }

    public static void setOnTimeTickListener(OnTimeTickListener onTimeTickListener) {
        TimerBroadcast.onTimeTickListener = onTimeTickListener;
    }

    public interface OnTimeTickListener {
        void onTimeTick();
    }

}