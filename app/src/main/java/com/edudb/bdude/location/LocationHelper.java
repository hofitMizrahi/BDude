package com.edudb.bdude.location;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.edudb.bdude.R;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.base.BaseActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.sucho.placepicker.AddressData;
import com.sucho.placepicker.Constants;
import com.sucho.placepicker.MapType;
import com.sucho.placepicker.PlacePicker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper {

    //jerusalem TODO change
    public static GeoPoint mLastLocation = new GeoPoint(31.7477639, 35.1859154);

    public static final int LOCATION_PERMISSION_REQ_CODE = 10;

    private static LocationHelper mInstance;

    public static LocationHelper getInstance() {
        if (mInstance == null)
            mInstance = new LocationHelper();
        return mInstance;
    }

    public static void setLocation(Intent data) {
        AddressData place = data.getParcelableExtra(Constants.ADDRESS_INTENT);
        mLastLocation = new GeoPoint(place.getLatitude(), place.getLongitude());
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

        } else {
            setUserLocation(activity);
        }

    }

    public void setUserLocation(Context context) {
        Location location = Utils.getLastBestLocation(context);
        mLastLocation = new GeoPoint(location.getLatitude(), location.getLongitude());

        //TODO check if need to save this location to user data
        //SessionManager.getInstance().setUserLocation();
    }

    public static String getLocationName(Context context) {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(LocationHelper.mLastLocation.getLatitude(), LocationHelper.mLastLocation.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO fix string if null
        if (addresses != null && addresses.size() > 0) {
            String locationString = addresses.get(0).getLocality() + ", " + addresses.get(0).getThoroughfare();
            return locationString;
        }
        return "";
    }

    public static double getDistance(LatLng endP){

        double distance = 0;
        Location locationA = new Location(LocationManager.GPS_PROVIDER);
        locationA.setLatitude(mLastLocation.getLatitude());
        locationA.setLongitude(mLastLocation.getLongitude());
        Location locationB = new Location(LocationManager.GPS_PROVIDER);
        locationB.setLatitude(endP.latitude);
        locationB.setLongitude(endP.longitude);
        distance = locationA.distanceTo(locationB);

        return distance / 1000;
    }

    public static void setMap(BaseActivity activity) {
        Intent intent = new PlacePicker.IntentBuilder()
                .setLatLong(mLastLocation.getLatitude(), mLastLocation.getLongitude())
                .showLatLong(true)
                .setMapZoom(12.0f)
                .setAddressRequired(true)
                .hideMarkerShadow(true)
                .setMarkerImageImageColor(R.color.colorPrimary)
                .setMapType(MapType.NORMAL)
                .onlyCoordinates(true)
                .build( activity);
        activity.startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST);
    }
}

