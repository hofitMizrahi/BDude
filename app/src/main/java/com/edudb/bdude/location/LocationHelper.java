package com.edudb.bdude.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.edudb.bdude.R;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.ui.base.BaseActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.schibstedspain.leku.LocationPickerActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;

public class LocationHelper {

    public static GeoPoint mLastLocation = new GeoPoint(32.069424, 34.783667);

    public static final int GPS_OPEN = 999;
    public static final int LOCATION_PERMISSION_REQ_CODE = 10;
    private static LocationHelper mInstance;

    public static LocationHelper getInstance() {
        if (mInstance == null)
            mInstance = new LocationHelper();
        return mInstance;
    }

    public static void setLocation(Intent data) {
        double latitude = data.getDoubleExtra(LATITUDE, 0.0);
        double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
        mLastLocation = new GeoPoint(latitude, longitude);
    }

    public static GeoPoint getLocation(Intent data) {
        double latitude = data.getDoubleExtra(LATITUDE, 0.0);
        double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
        return new GeoPoint(latitude, longitude);
    }

    public static void checkLastLocation(Context context) {

        if (!userNotHavePermission(context) && isHaveGpsOpen(context)) {
            setUserLocation(getCurrentLocation(context));
        }
    }

    public void checkLocation(BaseActivity activity) {

        if (userNotHavePermission(activity)) {

            DialogUtil.getSingleButtonInstance(activity, (dialog, i) -> {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                LOCATION_PERMISSION_REQ_CODE);
                        dialog.dismiss();
                    }, activity.getString(R.string.ask_for_location_permission_title)
                    , activity.getString(R.string.ask_for_location_permission_message)
                    , activity.getString(R.string.approve), true);

        } else if (!isHaveGpsOpen(activity)) {

            DialogUtil.getSingleButtonInstance(activity, (dialog, i) -> {

                        activity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_OPEN);

                    }, activity.getString(R.string.ask_for_location_gps_title)
                    , activity.getString(R.string.ask_for_location_gps_message)
                    , activity.getString(R.string.ask_for_location_gps_open_btn), true);

        } else {
            setUserLocation(getCurrentLocation(activity));
        }
    }

    public static boolean isHaveGpsOpen(Context context) {
        return ((LocationManager) context.getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean userNotHavePermission(Context context) {
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
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

    public static Location getCurrentLocation(Context context) {
        return getLastBestLocation(context);
    }

    private static void setUserLocation(Location location) {
        mLastLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
    }

    public static String getLocationName(Context context, GeoPoint geoPoint) {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(geoPoint.getLatitude(), geoPoint.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0) {

            String str = "";
            if (!Utils.isNullOrWhiteSpace(addresses.get(0).getLocality())) {
                str += addresses.get(0).getLocality();
            }
            if (!Utils.isNullOrWhiteSpace(addresses.get(0).getThoroughfare())) {
                str += ", " + addresses.get(0).getThoroughfare();
            }
            return str;
        }
        return "";
    }

    public static double getDistance(LatLng endP) {

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
        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                .withLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude())
                .withGeolocApiKey(activity.getString(R.string.google_api_key)) // https://github.com/AdevintaSpain/Leku#geocoding-api-fallback
                .withGooglePlacesEnabled()
                .shouldReturnOkOnBackPressed()
                .withZipCodeHidden()
                .withGoogleTimeZoneEnabled()
                .build(activity);

        activity.startActivityForResult(locationPickerIntent, BaseActivity.PLACE_PICKER_REQUEST);
    }
}

