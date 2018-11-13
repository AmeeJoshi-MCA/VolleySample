package com.lithe.service.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.lithe.service.application.AppControl;
import com.lithe.service.utils.Constants;
import com.lithe.service.utils.LocationDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LocationTrackingService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    private boolean isWorkAssigned = false;
    private boolean isTimerStarted = false;
    private boolean isSyncInIdle = true;

    private Timer timer;
    private TimerTask timerTask;

    private LocationDetector locationDetector;

    private Location lastLocation;
    private Location currentLocation;

    private int validDistance = 100; //in Meters

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand()");
        if (!isWorkAssigned) {
            startWork();
            isWorkAssigned = true;
        }
        return START_STICKY;
    }

    private void stopWork() {
        Log.i(TAG, "stopWork()");
        isTimerStarted = false;
        timerTask.cancel();
        timer.cancel();
        locationDetector.stopLocationUpdates();
        locationDetector = null;
    }

    private void startWork() {
        Log.i(TAG, "startWork() START");

        String preserveLocation = AppControl.getPreferenceHelper().getPreserveLocation();

        if (preserveLocation != null) {
            String[] latLong = preserveLocation.split(",");
            lastLocation = new Location(LocationManager.GPS_PROVIDER);
            lastLocation.setLongitude(Double.parseDouble(latLong[0]));
            lastLocation.setLongitude(Double.parseDouble(latLong[1]));
        }

        locationDetector = new LocationDetector(this);

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

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        try {
            PendingIntent service = PendingIntent.getService(
                    getApplicationContext(), 1001,
                    new Intent(getApplicationContext(), this.getClass()),
                    PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 2000, service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        stopWork();
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
        timer.scheduleAtFixedRate(timerTask, 0, 1000 * 30/*seconds*/);
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

            if (isValidLocation) {
                Log.i(TAG, "VALID LOCATION : " + currentLocation.getLatitude() + " , " + currentLocation.getLongitude());
                lastLocation = currentLocation;

                String latLong = String.valueOf(lastLocation.getLatitude()).concat(",")
                        .concat(String.valueOf(lastLocation.getLongitude()));
                AppControl.getPreferenceHelper().setPreserveLocation(latLong);


            }
        }
    }

}