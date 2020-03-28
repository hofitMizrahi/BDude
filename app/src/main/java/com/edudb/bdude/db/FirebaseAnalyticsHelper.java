package com.edudb.bdude.db;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAnalyticsHelper {

    //EVENT IDS
    public static final String NAVIGATE_POST_DETAILS = "navigate_post_details";
    public static final String CONTACT_WHATSAPP = "contact_whatsapp";
    public static final String CONTACT_DIAL = "contact_dial";

    // EVENT PARAMS
    public static final String PARAM_POST_ID = "post_id";
    public static final String PARAM_PHONE_NUMBER = "phone_number";

    public static void LogEvent(Context context, String eventId) {
        FirebaseAnalytics.getInstance(context).logEvent(eventId, null);
    }

    public static void LogEvent(Context context, String eventId, Bundle bundle) {
        FirebaseAnalytics.getInstance(context).logEvent(eventId, bundle);
    }
}
