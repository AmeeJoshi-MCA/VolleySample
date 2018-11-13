package com.lithe.service.services;

import android.util.Log;

import com.lithe.service.application.AppControl;

public class ServerTokenHandler {

    public static final String KEY_AUTHORIZATION = "Authorization";
    private static final String VAL_DEFAULT_SERVER_TOKEN = "undefined";

    public static String getServerToken() {
        String serverToken;
        if (isServerTokenExists()) {
            String serverTokenFromPreferences = AppControl.getPreferenceHelper().getServerToken();
            serverToken = "Bearer " + serverTokenFromPreferences;
        } else {
            serverToken = getDefaultServerToken();
        }
        Log.i("ServerTokenHandler", "getServerToken() - concat string with preference: " + serverToken);
        return serverToken;
    }

    public static String getDefaultServerToken(){
        return "Bearer " + VAL_DEFAULT_SERVER_TOKEN;
    }

    public static void updateServerToken(String serverToken) {
        Log.i("ServerTokenHandler", "updateServerToken() new token: " + serverToken);
        if (serverToken != null) {
            serverToken = serverToken.trim().replace("Bearer", "").trim();
            if (!serverToken.isEmpty()) {
                AppControl.getPreferenceHelper().setServerToken(serverToken);
            } else {
                Log.i("ServerTokenHandler", "updateServerToken() - token is empty");
            }
        } else {
            Log.i("ServerTokenHandler", "updateServerToken() - token is null");
        }
    }

    public static boolean isServerTokenExists() {
        return AppControl.getPreferenceHelper().getServerToken() != null;
    }
}
