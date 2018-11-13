package com.lithe.service.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ToastManager {

    private static List<Toast> listToast = new ArrayList<>();

    public void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        listToast.add(toast);
        toast.show();
    }

    public void dismissAllToasts() {
        if (!listToast.isEmpty()) {
            for (Toast toast : listToast) {
                toast.cancel();
            }
            listToast.clear();
        }
    }
}
