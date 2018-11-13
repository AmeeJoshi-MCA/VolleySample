package com.lithe.service.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lithe.service.helpers.PreferenceHelper;
import com.lithe.service.services.ApiUrl;
import com.lithe.service.services.VolleyServiceHandler;
import com.lithe.service.utils.LocationDetector;
import com.lithe.service.utils.ToastManager;

import java.util.Timer;
import java.util.TimerTask;

public class AppControl extends Application {

    private final String TAG = this.getClass().getSimpleName();

    private static PreferenceHelper preferenceHelper;
    public static ToastManager toastManager;
    private static VolleyServiceHandler volleyServiceHandler;
    private boolean isTimerStarted = false;
    private Timer timer;
    private TimerTask timerTask;

    private LocationDetector locationDetector;

    private Location lastLocation;
    private Location currentLocation;

    private int validDistance = 50; //in Meters

    private Context globalContext;

    public AppControl() {

    }

    public AppControl(Context context) {
        this.globalContext = context;
    }

    @Override
    public void onCreate() {
        globalContext = this;
        toastManager = new ToastManager();
        preferenceHelper = new PreferenceHelper(this, PreferenceHelper.PREFERENCE_NAME_APP_DEFAULT);
        volleyServiceHandler = new VolleyServiceHandler();
        ApiUrl.initHost();
        super.onCreate();
    }

    public static PreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    public static VolleyServiceHandler getVolleyServiceHandler() {
        return volleyServiceHandler;
    }

    public Drawable getDrawableByID(int id) {
        return ContextCompat.getDrawable(globalContext, id);
    }

    public Drawable getDrawableWithColor(int drawableID, int colorID) {
        Drawable drawable = getDrawableByID(drawableID);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        drawable = new BitmapDrawable(globalContext.getResources(), bitmap);
        drawable.setColorFilter(new PorterDuffColorFilter(getColorByID(colorID),
                PorterDuff.Mode.SRC_IN));
        return drawable;
    }

    public Drawable getDrawableWithExistingColor(int drawableID, int color) {
        Drawable drawable = getDrawableByID(drawableID);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        drawable = new BitmapDrawable(globalContext.getResources(), bitmap);
        drawable.setColorFilter(new PorterDuffColorFilter(color,
                PorterDuff.Mode.SRC_IN));
        return drawable;
    }

    public int getColorByID(int id) {
        return ContextCompat.getColor(globalContext, id);
    }

    public static String getIntColorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFF & color);
    }

    public static int convertDpToPixel(float dp, DisplayMetrics metrics) {
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboardOnInput(EditText editText, Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public Context getGlobalContext() {
        return globalContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String[] getDisplayResolution(Activity activity) {
        String[] data = new String[2];
        try {
            Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
            display.getMetrics(activity.getResources().getDisplayMetrics());
            Point localPoint = new Point();
            display.getRealSize(localPoint);
            data[0] = "" + localPoint.x;
            data[1] = "" + localPoint.y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // background latlong sync

    public void stopWork() {
        Log.i(TAG, "stopWork()");
        isTimerStarted = false;
        timerTask.cancel();
        timer.cancel();
        locationDetector.stopLocationUpdates();
        locationDetector = null;
    }

    public void startWork() {
        Log.i(TAG, "startWork() START");

        /*String preserveLocation = AppControl.getPreferenceHelper().getPreserveLocation();

        if (preserveLocation != null) {
            String[] latLong = preserveLocation.split(",");
            lastLocation = new Location(LocationManager.GPS_PROVIDER);
            lastLocation.setLongitude(Double.parseDouble(latLong[0]));
            lastLocation.setLongitude(Double.parseDouble(latLong[1]));
        }*/

        locationDetector = new LocationDetector(getGlobalContext());

        if (locationDetector.isLocationPermissionAvailable()) {
            if (!locationDetector.isLocationEnabled()) {

            }

            locationDetector.startLocationUpdates(new LocationListener() {
                @Override
                public void onLocationChanged(Location newLocation) {
                    if (!isTimerStarted) {
                        startTimerWork();
                        isTimerStarted = true;
                    }
                    Log.i("LOCATION", "NEW LOCATION : " + newLocation.getLatitude() + " , " + newLocation.getLongitude());
                    currentLocation = newLocation;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    private void startTimerWork() {
        Log.i(TAG, "startTimerWork()");
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                doWork();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000 * 3/*seconds*/);
    }

    private void doWork() {
        if (currentLocation != null) {
            boolean isValidLocation = false;
            if (lastLocation == null) {
                isValidLocation = true;
            } else {
                float distance = lastLocation.distanceTo(currentLocation);
                Log.i(TAG, "DISTANCE : " + distance);
                if (distance >= validDistance) {
                    isValidLocation = true;
                }
            }

            /*if (isValidLocation) {
                Log.i(TAG, "VALID LOCATION : " + currentLocation.getLatitude() + " , " + currentLocation.getLongitude());
                lastLocation = currentLocation;

                String latLong = String.valueOf(lastLocation.getLatitude()).concat(",")
                        .concat(String.valueOf(lastLocation.getLongitude()));
                AppControl.getPreferenceHelper().setPreserveLocation(latLong);

                makeLocationSyncApiCall(lastLocation);
            }*/

            lastLocation = currentLocation;

            String latLong = String.valueOf(lastLocation.getLatitude()).concat(",")
                    .concat(String.valueOf(lastLocation.getLongitude()));
            AppControl.getPreferenceHelper().setPreserveLocation(latLong);

        }
    }

}