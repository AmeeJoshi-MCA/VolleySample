package com.lithe.service.utils;

import android.content.Intent;

public interface ActivityResultListener {
    void onActivityListen(int requestCode, int resultCode, Intent data);
}
