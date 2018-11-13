package com.lithe.service.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by lithe on 7/6/2018.
 */

public class LocationDetector {

    private Context context;

    private Location locationNetwork, locationGPS;
    private LocationListener locationListenerNetwork, locationListenerGPS, locationListener;
    private LocationManager locationManager;

    public LocationDetector(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationListenerNetwork = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationNetwork = location;
                sendBestLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                locationListener.onStatusChanged(provider, status, extras);
            }

            @Override
            public void onProviderEnabled(String provider) {
                locationListener.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                locationListener.onProviderDisabled(provider);
            }
        };

        locationListenerGPS = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationGPS = location;
                sendBestLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                locationListener.onStatusChanged(provider, status, extras);
            }

            @Override
            public void onProviderEnabled(String provider) {
                locationListener.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                locationListener.onProviderDisabled(provider);
            }
        };
    }

    private void sendBestLocation() {
        if (locationNetwork != null && locationGPS != null) {
            if (isBetterLocation(locationGPS, locationNetwork)) {
                locationListener.onLocationChanged(locationGPS);
            } else {
                locationListener.onLocationChanged(locationNetwork);
            }
        } else if (locationNetwork != null) {
            locationListener.onLocationChanged(locationNetwork);
        } else if (locationGPS != null) {
            locationListener.onLocationChanged(locationGPS);
        }
    }

    public boolean isLocationPermissionAvailable() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void startLocationUpdates(LocationListener locationListener) {
        setLocationListener(locationListener);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    locationListenerNetwork);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListenerGPS);
        }
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(locationListenerNetwork);
        locationManager.removeUpdates(locationListenerGPS);
    }

    private boolean isBetterLocation(Location location, Location currentBestLocation) {
        final int TWO_MINUTES = 1000 * 60 * 2;

        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate) {
            return true;
        }
        return false;
    }
}
