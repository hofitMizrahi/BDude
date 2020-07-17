package com.edudb.bdude.general.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.edudb.bdude.R;
import com.edudb.bdude.enums.EnumEmergency;
import com.edudb.bdude.enums.EnumPayBack;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.edudb.bdude.enums.EnumEmergency.AGE_AT_RISK;
import static com.edudb.bdude.enums.EnumEmergency.ISOLATE;
import static com.edudb.bdude.enums.EnumEmergency.SICK;

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


    public static void setViewVisibility(View view, Boolean isVisible, int nonVisibleMode) {
        if (view == null)
            return;
        if (isVisible != null) {
            if (isVisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(nonVisibleMode);

            }
        }
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

    public static boolean blackWordsCheck(String text){

        for (String blackWord : blackWords) {
            if (text.contains(blackWord))
                return false;
        }

        return true;
    }

    static List<String> blackWords = Arrays.asList(
            "anal",
            "anus",
            "arse",
            "ass",
            "ass fuck",
            "ass hole",
            "assfucker",
            "asshole",
            "assshole",
            "bastard",
            "bitch",
            "black cock",
            "bloody hell",
            "boong",
            "cock",
            "cockfucker",
            "cocksuck",
            "cocksucker",
            "coon",
            "coonnass",
            "crap",
            "cunt",
            "cyberfuck",
            "damn",
            "darn",
            "dick",
            "dirty",
            "douche",
            "dummy",
            "erect",
            "erection",
            "erotic",
            "escort",
            "fag",
            "faggot",
            "fuck",
            "Fuck off",
            "fuck you",
            "fuckass",
            "fuckhole",
            "god damn",
            "gook",
            "hard core",
            "hardcore",
            "homoerotic",
            "hore",
            "lesbian",
            "lesbians",
            "mother fucker",
            "motherfuck",
            "motherfucker",
            "negro",
            "nigger",
            "orgasim",
            "orgasm",
            "penis",
            "penisfucker",
            "piss",
            "piss off",
            "porn",
            "porno",
            "pornography",
            "pussy",
            "retard",
            "sadist",
            "sex",
            "sexy",
            "shit",
            "slut",
            "son of a bitch",
            "suck",
            "tits",
            "viagra",
            "whore",
            "xxx",
            "אנאלי",
            "פי הטבעת",
            "התחת",
            "אידיוט",
            "חור תחת",
            "ממזר",
            "בון",
            "זין",
            "קוקסינקר",
            "שטויות",
            "כוס",
            "פאקינג",
            "לעזאזל",
            "מלוכלך",
            "זקוף",
            "זקפה",
            "ארוטי",
            "ליווי",
            "הומו",
            "זיון",
            "תזדיין",
            "לזיין",
            "לעזאזל",
            "הארדקור",
            "הומורוטי",
            "חורה",
            "לסבית",
            "לסביות",
            "כושי",
            "אורגסים",
            "אורגזמה",
            "איבר המין",
            "שתן",
            "להשתין",
            "פורנו",
            "פורנוגרפיה",
            "מפגר",
            "סדיסט",
            "סקס",
            "חרא",
            "שרמוטה",
            "למצוץ",
            "ציצים",
            "ויאגרה",
            "זונה");



    public static String getStringRefundTitle(EnumPayBack item){

        String title = "";
        switch (item){
            case BRING_BACK:
                title = "מבקש בהשאלה";
                break;
            case CONTRIBUTION:
                title = "מבקש תרומה";
                break;
            case MONEY_TRANSFER:
                title = "אחזיר לך את הכסף";
                break;
        }
        return title;
    }

    public static String getStringEmergencyTitle(EnumEmergency item){

        String title = "";
        switch (item){
            case SICK:
                title = "חולה";
                break;
            case ISOLATE:
                title = "בבידוד";
                break;
            case AGE_AT_RISK:
                title = "גיל בסיכון";
                break;
        }
        return title;
    }

    public static int getIconRefund(EnumPayBack item){

        int ic = 0;
        switch (item){
            case BRING_BACK:
                ic = R.drawable.ic_payback_loan;
                break;
            case CONTRIBUTION:
                ic = R.drawable.ic_payback_contribution;
                break;
            case MONEY_TRANSFER:
                ic = R.drawable.ic_payback_pay;
                break;
        }
        return ic;
    }

    public static int getIconEmergency(EnumEmergency item){

        int ic = 0;
        switch (item){
            case AGE_AT_RISK:
                ic = R.drawable.ic_emergency_sikun;
                break;
            case ISOLATE:
                ic = R.drawable.ic_emergency_bdude;
                break;
            case SICK:
                ic = R.drawable.ic_emergency_sick;
                break;
        }
        return ic;
    }
}
