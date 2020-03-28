package com.edudb.bdude.general.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static boolean isNullOrWhiteSpace(String string) {
        return string == null || string.isEmpty() || string.equals("null");
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

    public static String getTimeFormat(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM HH:mm");
        return format.format(date);
    }
}
