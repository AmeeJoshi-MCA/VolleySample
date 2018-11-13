package com.lithe.service.utils;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Krunal on 1/5/2017.
 */

public class GeneralUtils {

    public static int getScaledHeight(Activity activity, double imageWidthInPX, double imageHeightInPX) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidthInPX = size.x;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        double displayWidthInDP = displayWidthInPX / ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        double heightPercent = (imageHeightInPX * 100) / imageWidthInPX;
        double newHeightInDP = (displayWidthInDP * heightPercent) / 100;
        return (int) (newHeightInDP * (displayMetrics.densityDpi / 160f));
    }

    @SuppressWarnings("deprecation")
    @NonNull
    public static Spanned textFromHtml(@NonNull String htmlString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(htmlString);
        }
    }

    public static String hasMapToJsonString(LinkedHashMap<String, String> linkedHashMap) {
        return new Gson().toJson(linkedHashMap);
    }

    public static LinkedHashMap<String, String> hasMapJsonToHashMap(String hasMapJson) {
        LinkedHashMap<String, String> linkedHashMap = null;
        try {
            Type type = new TypeToken<LinkedHashMap<String, String>>() {
            }.getType();
            linkedHashMap = new Gson().fromJson(hasMapJson, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedHashMap;
    }

    public static String stringToMD5(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(value.getBytes());
            byte byteData[] = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByteData : byteData) {
                stringBuilder.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            value = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static String getFilteredStringForLabelCount(String data, String defaultText) {
        if (data != null && !data.trim().isEmpty()) {
            return data;
        } else {
            return defaultText;
        }
    }

    public static String getFilteredStringForLabelText(String data, String defaultText) {
        if (data != null && !data.trim().isEmpty()) {
            return data;
        } else {
            return defaultText;
        }
    }

    public static String encodeBtoA(String value) {
        return new String(Base64.encode(value.getBytes(), Base64.DEFAULT));
    }

    public static String decodeBtoA(String value) {
        return new String(Base64.decode(value, Base64.DEFAULT));
    }

    public static int getRandomNumberInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static String randomStringOfLength(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < length) {
            stringBuilder.append(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        return stringBuilder.substring(0, length);
    }


    public static InputFilter emailTextFilter = new InputFilter() {
        private final String ALLOW_CHAR = "_.,@";

        @Override
        public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int i2, int i3) {
            if (charSequence != null) {
                String input = charSequence.toString().trim();
                for (int i = 0; i < input.length(); i++) {
                    if (!ALLOW_CHAR.contains(input.charAt(i) + "")) {
                        if (!Character.isLetterOrDigit(input.charAt(i))) {
                            return "";
                        }
                    }
                }
            }
            return null;
        }
    };

    public static String limitDecimalPoints(String stringOfDecimal, int limit) {
        if (stringOfDecimal.contains(".")) {
            String[] data = stringOfDecimal.split("\\.");
            if (data[1].length() > 0 && limit <= data[1].length()) {
                stringOfDecimal = data[0] + "." + data[1].substring(0, limit);
            }
        }
        return stringOfDecimal;
    }

}
