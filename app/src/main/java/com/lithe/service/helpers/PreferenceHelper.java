package com.lithe.service.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private SharedPreferences sharedPreferences;

    public static final String PREFERENCE_NAME_APP_DEFAULT = "pref_app_default";

    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String IS_REGISTERED = "IS_REGISTERED";
    public static final String IS_DAY_STARTED = "IS_DAY_STARTED";
    public static final String IS_ACCEPTED = "IS_ACCEPTED";
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PASS = "USER_PASS";
    public static final String LOAD_AUTH = "LOAD_AUTH";
    public static final String LOGIN_RESPONSE = "LOGIN_RESPONSE";
    public static final String STOCK_ON_HAND_RESPONSE = "STOCK_ON_HAND_RESPONSE";
    public static final String TODAY_PLAN_RESPONSE = "TODAY_PLAN_RESPONSE";
    public static final String RECONSIGN_RESPONSE = "RECONSIGN_RESPONSE";
    public static final String AUTHENTICATION_SECURITY_TYPE = "AUTHENTICATION_SECURITY_TYPE";
    public static final String FCM_TOKEN = "FCM_TOKEN";
    public static final String SERVER_TOKEN = "SERVER_TOKEN";
    public static final String FIELD_PRF_FCM_USER_ID = "field_prf_fcm_user_id";
    public static final String FIELD_PRF_FCM_USER_CREATED = "field_prf_fcm_user_created";
    public static final String FIELD_PRF_FCM_DEVICE_TOKEN = "field_prf_fcm_device_token";
    public static final String FIELD_PRF_USER_CREATED_TIME = "field_prf_user_created_time";
    public static final String FIELD_PRF_DIVICE_OTHER_DATA = "field_prf_divice_other_data";
    public static final String FIELD_PRF_DIVICE_DISPLAY_RESOLUTION = "field_prf_divice_display_resolution";
    public static final String FIELD_PRF_USER_DEFAULT_EMAIL_ID = "field_prf_user_default_email_id";
    public static final String FIELD_PRF_CREATED_TIME_STAMP_FOR_FCM_DATA = "field_prf_created_time_stamp_for_fcm_data";
    public static final String IS_SUBSCRIPTION_ON = "IS_SUBSCRIPTION_ON";
    public static final String IS_QR_CODE_ON = "IS_QR_CODE_ON";
    public static final String CANCEL_REMARKS = "CANCEL_REMARKS";
    public static final String PRESERVE_LOCATION = "PRESERVE_LOCATION";
    public static final String IS_LOCATION_SYNC_SERVICE_STARTED = "IS_LOCATION_SYNC_SERVICE_STARTED";

    public static final String VAL_AUTHENTICATION_PIN_SECURITY = "VAL_AUTHENTICATION_PIN_SECURITY";
    public static final String VAL_AUTHENTICATION_FINGERPRINT_SECURITY = "VAL_AUTHENTICATION_FINGERPRINT_SECURITY";

    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String DEVICE_NAME = "DEVICE_NAME";
    public static final String DEVICE_ACTIVATE_DATE = "DEVICE_ACTIVATE_DATE";

    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String USER_ID = "USER_ID";

    public PreferenceHelper(Context context, String preferenceName) {
        sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public static String getPreferenceName() {
        return PREFERENCE_NAME_APP_DEFAULT;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setIsLogin(boolean value) {
        sharedPreferences.edit().putBoolean(IS_LOGIN, value).apply();
    }

    public boolean getIsLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void setIsRegistered(boolean value) {
        sharedPreferences.edit().putBoolean(IS_REGISTERED, value).apply();
    }

    public void setIsAccepted(boolean value) {
        sharedPreferences.edit().putBoolean(IS_ACCEPTED, value).apply();
    }

    public boolean getIsAccepted() {
        return sharedPreferences.getBoolean(IS_ACCEPTED, false);
    }

    public boolean getIsRegistered() {
        return sharedPreferences.getBoolean(IS_REGISTERED, false);
    }

    public void setIsDayStarted(boolean value) {
        sharedPreferences.edit().putBoolean(IS_DAY_STARTED, value).apply();
    }

    public boolean getIsDayStarted() {
        return sharedPreferences.getBoolean(IS_DAY_STARTED, false);
    }

    public void setUserName(String value) {
        sharedPreferences.edit().putString(USER_NAME, value).apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, null);
    }

    public void setUserPass(String value) {
        sharedPreferences.edit().putString(USER_PASS, value).apply();
    }

    public String getUserPass() {
        return sharedPreferences.getString(USER_PASS, null);
    }

    public void setAuthenticationSecurityType(String value) {
        sharedPreferences.edit().putString(AUTHENTICATION_SECURITY_TYPE, value).apply();
    }

    public String getAuthenticationSecurityType() {
        return sharedPreferences.getString(AUTHENTICATION_SECURITY_TYPE, null);
    }

    public void setLoginResponse(String value) {
        sharedPreferences.edit().putString(LOGIN_RESPONSE, value).apply();
    }

    public String getLoginResponse() {
        return sharedPreferences.getString(LOGIN_RESPONSE, null);
    }

    public void setStockOnHandResponse(String value) {
        sharedPreferences.edit().putString(STOCK_ON_HAND_RESPONSE, value).apply();
    }

    public String getStockOnHandResponse() {
        return sharedPreferences.getString(STOCK_ON_HAND_RESPONSE, null);
    }

    public void setTodayPlanResponse(String value) {
        sharedPreferences.edit().putString(TODAY_PLAN_RESPONSE, value).apply();
    }

    public String getTodayPlanResponse() {
        return sharedPreferences.getString(TODAY_PLAN_RESPONSE, null);
    }

    public void setCustomerId(String value) {
        sharedPreferences.edit().putString(CUSTOMER_ID, value).apply();
    }

    public String getCustomerId() {
        return sharedPreferences.getString(CUSTOMER_ID, null);
    }

    public void setServerToken(String value) {
        sharedPreferences.edit().putString(SERVER_TOKEN, value).apply();
    }

    public String getServerToken() {
        return sharedPreferences.getString(SERVER_TOKEN, null);
    }

    public void setDeviceId(String deviceId) {
        sharedPreferences.edit().putString(DEVICE_ID, deviceId).apply();
    }

    public String getDeviceId() {
        return sharedPreferences.getString(DEVICE_ID, null);
    }

    public void setDeviceName(String deviceName) {
        sharedPreferences.edit().putString(DEVICE_NAME, deviceName).apply();
    }

    public String getDeviceName() {
        return sharedPreferences.getString(DEVICE_NAME, null);
    }

    public void setDeviceActivateDate(String deviceActivateDate) {
        sharedPreferences.edit().putString(DEVICE_ACTIVATE_DATE, deviceActivateDate).apply();
    }

    public String getDeviceActivateDate() {
        return sharedPreferences.getString(DEVICE_ACTIVATE_DATE, null);
    }

    public void setClientId(String clientId) {
        sharedPreferences.edit().putString(CLIENT_ID, clientId).apply();
    }

    public String getClientId() {
        return sharedPreferences.getString(CLIENT_ID, null);
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString(USER_ID, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void setLoadAuthResponse(String value) {
        sharedPreferences.edit().putString(LOAD_AUTH, value).apply();
    }

    public String getLoadAuthResponse() {
        return sharedPreferences.getString(LOAD_AUTH, null);
    }

    public void setFcmToken(String value) {
        sharedPreferences.edit().putString(FCM_TOKEN, value).apply();
    }

    public String getFcmToken() {
        return sharedPreferences.getString(FCM_TOKEN, null);
    }

    public void setReconsignResponse(String value) {
        sharedPreferences.edit().putString(RECONSIGN_RESPONSE, value).apply();
    }

    public String getReconsignResponse() {
        return sharedPreferences.getString(RECONSIGN_RESPONSE, null);
    }

    public String getFcmUserId() {
        return sharedPreferences.getString(FIELD_PRF_FCM_USER_ID, "VAL_DEFAULT");
    }

    public void setFcmUserId(String value) {
        sharedPreferences.edit().putString(FIELD_PRF_FCM_USER_ID, value).apply();
    }

    public boolean getFcmUserCreated() {
        return sharedPreferences.getBoolean(FIELD_PRF_FCM_USER_CREATED, false);
    }

    public void setFcmUserCreated(boolean value) {
        sharedPreferences.edit().putBoolean(FIELD_PRF_FCM_USER_CREATED, value).apply();
    }

    public String getFcmDeviceToken() {
        return sharedPreferences.getString(FIELD_PRF_FCM_DEVICE_TOKEN, null);
    }

    public void setFcmDeviceToken(String value) {
        sharedPreferences.edit().putString(FIELD_PRF_FCM_DEVICE_TOKEN, value).apply();
    }

    public void setUserCreatedTime(String value) {
        sharedPreferences.edit().putString(FIELD_PRF_USER_CREATED_TIME, value).apply();
    }

    public String getUserCreatedTime() {
        return sharedPreferences.getString(FIELD_PRF_USER_CREATED_TIME, null);
    }

    public void setDeviceOtherData(String value) {
        sharedPreferences.edit().putString(FIELD_PRF_DIVICE_OTHER_DATA, value).apply();
    }

    public String getDeviceOtherData() {
        return sharedPreferences.getString(FIELD_PRF_DIVICE_OTHER_DATA, null);
    }

    public void setDeviceDisplayResolution(String value) {
        sharedPreferences.edit().putString(FIELD_PRF_DIVICE_DISPLAY_RESOLUTION, value).apply();
    }

    public String getDeviceDisplayResolution() {
        return sharedPreferences.getString(FIELD_PRF_DIVICE_DISPLAY_RESOLUTION, null);
    }

    public String getUserDefaultEmailId() {
        return sharedPreferences.getString(FIELD_PRF_USER_DEFAULT_EMAIL_ID, null);
    }

    public void setUserDefaultEmailId(String value) {
        sharedPreferences.edit().putString(FIELD_PRF_USER_DEFAULT_EMAIL_ID, value).apply();
    }

    public void setFCMCreatedBy(String value) {
        sharedPreferences.edit().putString(FIELD_PRF_CREATED_TIME_STAMP_FOR_FCM_DATA, value).apply();
    }

    public String getFCMCreatedBy() {
        return sharedPreferences.getString(FIELD_PRF_CREATED_TIME_STAMP_FOR_FCM_DATA, null);
    }

    public void setIsSubscriptionOn(String value) {
        sharedPreferences.edit().putString(IS_SUBSCRIPTION_ON, value).apply();
    }

    public String getIsSubscriptionOn() {
        return sharedPreferences.getString(IS_SUBSCRIPTION_ON, null);
    }

    public void setIsQrCodeOn(String value) {
        sharedPreferences.edit().putString(IS_QR_CODE_ON, value).apply();
    }

    public String getIsQrCodeOn() {
        return sharedPreferences.getString(IS_QR_CODE_ON, null);
    }

    public void setCancelRemarks(String value) {
        sharedPreferences.edit().putString(CANCEL_REMARKS, value).apply();
    }

    public String getCancelRemarks() {
        return sharedPreferences.getString(CANCEL_REMARKS, null);
    }

    public void setPreserveLocation(String value) {
        sharedPreferences.edit().putString(PRESERVE_LOCATION, value).apply();
    }

    public String getPreserveLocation() {
        return sharedPreferences.getString(PRESERVE_LOCATION, null);
    }

    public void setIsLocationSyncServiceStarted(boolean value) {
        sharedPreferences.edit().putBoolean(IS_LOCATION_SYNC_SERVICE_STARTED, value).apply();
    }

    public boolean getIsLocationSyncServiceStarted() {
        return sharedPreferences.getBoolean(IS_LOCATION_SYNC_SERVICE_STARTED, false);
    }
}