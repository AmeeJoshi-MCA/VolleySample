/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lithe.service.services;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIDService";

    private static TokenReceiveListener tokenReceiveListener;

    @SuppressLint("LongLogTag")
    public static void createInstance() {
        Log.i(TAG, "createInstance() , Call");
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        if (fcmToken != null) {
            Log.i(TAG, "createInstance() , FCM Token is Available");
            Log.i(TAG, "FCM Token : " + fcmToken);
            if (tokenReceiveListener != null) {
                tokenReceiveListener.onTokenReceive(fcmToken);
            }
        } else {
            Log.i(TAG, "createInstance() , FCM Token is Unavailable");
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onTokenRefresh() {
        Log.i(TAG, "onTokenRefresh() , Call");
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        if (fcmToken != null) {
            Log.i(TAG, "onTokenRefresh() , FCM Token is Available \n FCM Token : " + fcmToken);
            if (tokenReceiveListener != null) {
                tokenReceiveListener.onTokenReceive(fcmToken);
            }
        } else {
            Log.i(TAG, "onTokenRefresh() , FCM Token is Unavailable");
            if (tokenReceiveListener != null) {
                tokenReceiveListener.onTokenReceive(null);
            }
        }
    }

    public static void setTokenReceiveListener(TokenReceiveListener tokenReceiveListener) {
        MyFirebaseInstanceIDService.tokenReceiveListener = tokenReceiveListener;
    }

    public interface TokenReceiveListener {
        void onTokenReceive(String token);
    }
}