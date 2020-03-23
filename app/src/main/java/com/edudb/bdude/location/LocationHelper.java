package com.edudb.bdude.location;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.edudb.bdude.R;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.base.BaseActivity;
import com.google.firebase.firestore.GeoPoint;

public class LocationHelper {

    public static final int LOCATION_PERMISSION_REQ_CODE = 10;

    private static LocationHelper mInstance;

    public static LocationHelper getInstance() {
        if (mInstance == null)
            mInstance = new LocationHelper();
        return mInstance;
    }

    public void checkLocation(BaseActivity activity) {

        if ((ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            DialogUtil.getSingleButtonInstance(activity, (dialog, i) -> {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                LOCATION_PERMISSION_REQ_CODE);
                        dialog.dismiss();
                    }, activity.getString(R.string.ask_for_location_permission_title)
                    , activity.getString(R.string.ask_for_location_permission_message)
                    , activity.getString(R.string.approve), true);

            //TODO CHECK || INTERNET LOCATION
        } else if (!((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            DialogUtil.getSingleButtonInstance(activity, (dialog, i) -> {

                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }, activity.getString(R.string.ask_for_location_gps_title)
                    , activity.getString(R.string.ask_for_location_gps_message)
                    , activity.getString(R.string.ask_for_location_gps_open_btn), true);

        }else {
            setUserLocation(activity);
        }

    }

    public void setUserLocation(Context context) {
        Location location = Utils.getLastBestLocation(context);
        SessionManager.getInstance().setUserLocation(new GeoPoint(location.getLatitude(), location.getLongitude()));
    }
}

