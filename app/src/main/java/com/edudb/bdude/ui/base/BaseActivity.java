package com.edudb.bdude.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edudb.bdude.R;
import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.general.BaseActionBar;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.CreateHelpRequestActivity;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.MyRequestsActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.view.RequestDetailsActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.sucho.placepicker.AddressData;
import com.sucho.placepicker.Constants;
import com.sucho.placepicker.MapType;
import com.sucho.placepicker.PlacePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Objects;

import butterknife.ButterKnife;

import static com.edudb.bdude.location.LocationHelper.LOCATION_PERMISSION_REQ_CODE;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private static final int RC_SIGN_IN = 5;
    public static final String REQUEST_DETAILS = "request_details";

    private LocationHelper mLocationHelper = LocationHelper.getInstance();

    private final int PLACE_PICKER_REQUEST = 1;

    private ProgressBar mProgressBar;
    private View mContainer;
    private ViewGroup mActionBarContainer;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView();
        ButterKnife.bind(this);
        initDependencies();
        hideProgressBar();
        if (getPresenter() != null) {
            getPresenter().onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(this::setActionBar, 100);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationMessageEvent(BaseActionBar.LocationMessageEvent event) {
        checkLocation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareMessageEvent(BaseActionBar.ShareMessageEvent event) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_info));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserRegistrationEvent(BaseActionBar.UserRegistrationMessageEvent event) {
        checkLoginAndNavigate(EnumNavigation.MY_REQUESTS);
    }

    public void displayProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mContainer.setVisibility(View.GONE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mContainer.setVisibility(View.VISIBLE);
    }

    public BaseActionBar getCustomActionBar() {
        return new BaseActionBar(this);
    }


    protected void setActionBar() {
        Log.d("setActionBar", getClass().getSimpleName());
        BaseActionBar actionBar = getCustomActionBar();
        mActionBarContainer.removeAllViews();
        mActionBarContainer.addView(actionBar);
        actionBar.postInvalidate();
    }

    public void startDial(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phoneNumber)));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setContentView() {

        View root = getLayoutInflater().inflate(R.layout.activity_base, null);
        setContentView(root);

        if (getLayoutResource() != 0) {
            View view = getLayoutInflater().inflate(getLayoutResource(), null);
            mProgressBar = findViewById(R.id.progress_bar);
            mContainer = findViewById(R.id.content_container);
            mActionBarContainer = findViewById(R.id.action_bar_container);
            ViewGroup mContentContainer = findViewById(R.id.content_container);
            mContentContainer.addView(view);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public BasePresenter getPresenter() {
        return null;
    }

    public abstract int getLayoutResource();

    public abstract void initDependencies();

    @Override
    public void checkLocation() {
        Intent intent = new PlacePicker.IntentBuilder()
                .setLatLong(32.073580, 34.788050)
                .showLatLong(true)
                .setMapZoom(12.0f)
                .setAddressRequired(true)
                .hideMarkerShadow(true)
                .setMarkerImageImageColor(R.color.colorPrimary)
                .setMapType(MapType.NORMAL)
                .onlyCoordinates(true)  //Get only Coordinates from Place Picker
                .build(this);
        startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST);
        //mLocationHelper.checkLocation(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocation();
            }
        }
    }

    @Override
    public void checkLoginAndNavigate(EnumNavigation navigation) {

        if (!SessionManager.getInstance().isUserLogin()) {
            startLogin();
        } else {

            switch (navigation) {
                case CREATE_POST:
                    startActivity(new Intent(this, CreateHelpRequestActivity.class));
                    break;
                case MY_REQUESTS:
                    startActivity(new Intent(this, MyRequestsActivity.class));
                    break;
            }
        }
    }

    public void navigateToRequestDetailsScreen(HelpRequest request) {
        startActivity(new Intent(this, RequestDetailsActivity.class).putExtra(REQUEST_DETAILS, request));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDbHelper.getInstance().getCurrentUserDetails(uid, this::saveUserDetails);
                }

            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar("Sign in cancelled");
                    return;
                }

                if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar("No Internet connection");
                    return;
                }

                showSnackbar("Unknown error");
                Log.e("BDUDE", "Sign-in error: ", response.getError());
            }
        } else if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                AddressData addressData = data.getParcelableExtra(Constants.ADDRESS_INTENT);
                Toast.makeText(this, addressData.getLatitude() + "   " + addressData.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveUserDetails(User user) {
        SessionManager.getInstance().setCurrentUser(user);
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public void signOut(View view) {
        AuthUI.getInstance().signOut(this);
    }

    public void startLogin() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                //new AuthUI.IdpConfig.FacebookBuilder().build(), TODO add facebook_application_id, https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#facebook, https://console.firebase.google.com/project/bdude-d4c05/authentication/providers
                                new AuthUI.IdpConfig.EmailBuilder().build()
                                //new AuthUI.IdpConfig.PhoneBuilder().build()
                        ))
                        .setTheme(R.style.AppThemeFirebaseAuth)
                        .setLogo(R.mipmap.ic_launcher)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
