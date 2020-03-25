package com.edudb.bdude.general.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class Utils {

    public static boolean isNullOrWhiteSpace(String string) {
        return string == null || string.isEmpty() || string.equals("null");
    }

    public static String getNonNullText(String text) {
        return isNullOrWhiteSpace(text) ? "" : text;
    }

    @SuppressLint("MissingPermission")
    public static Location getLastBestLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    public static <T> T tryParseJson(String string, Class<T> clazz) {
        try {
            return new Gson().fromJson(string, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("tryParseJson", string);
        }
        return null;
    }

    public static <T> T tryParseJsonType(String string, Type clazz) {
        try {
            return new Gson().fromJson(string, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("tryParseJson", string);
        }
        return null;
    }

    public static String parseToJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
