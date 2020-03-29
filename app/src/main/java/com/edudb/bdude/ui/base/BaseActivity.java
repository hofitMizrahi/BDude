package com.edudb.bdude.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edudb.bdude.BuildConfig;
import com.edudb.bdude.R;
import com.edudb.bdude.db.FirebaseAnalyticsHelper;
import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.general.BaseActionBar;
import com.edudb.bdude.general.KeyValue;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.CreateHelpRequestActivity;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.MyRequestsActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.view.RequestDetailsActivity;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.HelpRequestsListActivity;
import com.edudb.bdude.ui.flow.terms_of_use.view.TermsOfUseActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Objects;

import butterknife.ButterKnife;

import static com.edudb.bdude.location.LocationHelper.GPS_OPEN;
import static com.edudb.bdude.location.LocationHelper.LOCATION_PERMISSION_REQ_CODE;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private static final int RC_SIGN_IN = 5;
    public static final int PLACE_PICKER_REQUEST = 6;
    public static final String REQUEST_DETAILS = "request_details";

    private FirebaseAnalytics mFirebaseAnalytics;
    private EnumNavigation mEnumNavigation;
    private Post mTempPost;

    
    private ProgressBar mProgressBar;
    private View mContainer;
    private ViewGroup mActionBarContainer;
    private BaseActionBar mBaseActionBar;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView();
        ButterKnife.bind(this);
        initDependencies();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
    public void onLocationMessageEvent(BaseActionBar.ChangeLocationEvent event) {
        openMap();
    }

    public void openMap() {
        LocationHelper.setMap(this);
    }

    public void logEvent(String key, String value){

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, value);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        //bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(key, bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareMessageEvent(BaseActionBar.ShareMessageEvent event) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "B-dude");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserRegistrationEvent(BaseActionBar.UserRegistrationMessageEvent event) {
        checkLoginAndNavigate(EnumNavigation.MY_REQUESTS);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAskLocationEvent(BaseActionBar.LocationMessageEvent event) {
        checkLocation();
        if (!LocationHelper.userNotHavePermission(this) && LocationHelper.isHaveGpsOpen(this)) {
            searchByNewLocation();
        }
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
        return mBaseActionBar != null ? mBaseActionBar : new BaseActionBar(this);
    }

    protected void setActionBar() {
        Log.d("setActionBar", getClass().getSimpleName());
        mBaseActionBar = getCustomActionBar();
        mActionBarContainer.removeAllViews();

        if (this instanceof HelpRequestsListActivity) {
            mBaseActionBar.showSearchLine();
            mBaseActionBar.setAddress(LocationHelper.getLocationName(this, LocationHelper.mLastLocation));
        } else {
            mBaseActionBar.removeSearchLine();
        }

        if (this instanceof MyRequestsActivity || this instanceof CreateHelpRequestActivity || this instanceof TermsOfUseActivity) {
            mBaseActionBar.removeLoginIcon();
        }

        mActionBarContainer.addView(mBaseActionBar);
        mBaseActionBar.postInvalidate();
    }

    public void startDial(String id, String phoneNumber) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalyticsHelper.PARAM_PHONE_NUMBER, phoneNumber);
            bundle.putString(FirebaseAnalyticsHelper.PARAM_POST_ID, id);
            FirebaseAnalyticsHelper.LogEvent(this, FirebaseAnalyticsHelper.CONTACT_DIAL, bundle);
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
        LocationHelper.getInstance().checkLocation(this);
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

        mEnumNavigation = navigation;

        if (!SessionManager.getInstance().isUserLogin()) {
            startLogin();
        } else {

            switch (navigation) {
                case CREATE_POST:
                    startActivity(new Intent(this, CreateHelpRequestActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    break;
                case MY_REQUESTS:
                    startActivity(new Intent(this, MyRequestsActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    break;
            }
        }
    }

    public void navigateToRequestDetailsScreen(Post request) {
        mTempPost = request;
        mEnumNavigation = EnumNavigation.POST_DETAILS;
        if (!SessionManager.getInstance().isUserLogin()) {
            startLogin();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalyticsHelper.PARAM_POST_ID, request.getId());
            FirebaseAnalyticsHelper.LogEvent(this, FirebaseAnalyticsHelper.NAVIGATE_POST_DETAILS, bundle);
            startActivity(new Intent(this, RequestDetailsActivity.class).putExtra(REQUEST_DETAILS, request));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                FirebaseAnalyticsHelper.LogEvent(this, FirebaseAnalytics.Event.LOGIN);

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    FirebaseDbHelper.getInstance().getCurrentUserDetails(FirebaseAuth.getInstance().getCurrentUser().getUid(), this::saveUserDetails);
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

        } else if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == Activity.RESULT_OK && data != null) {

                if (getActivity() instanceof CreateHelpRequestActivity) {
                    ((CreateHelpRequestPresenter) getPresenter()).changeLocation(LocationHelper.getLocation(data));
                } else {
                    LocationHelper.setLocation(data);
                    searchByNewLocation();
                }
            } else {
                showSnackbar("מיקום לא נמצא");
            }
        }else if(requestCode == GPS_OPEN){

            if (!LocationHelper.userNotHavePermission(this) && LocationHelper.isHaveGpsOpen(this)) {
                LocationHelper.checkLastLocation(this);
                searchByNewLocation();
            }
        }
    }

    private void searchByNewLocation() {
        getCustomActionBar().setAddress(LocationHelper.getLocationName(this, LocationHelper.mLastLocation));
        refreshData();
    }

    protected void refreshData() {
    }

    private void saveUserDetails(User user) {
        SessionManager.getInstance().setCurrentUser(user);

        if (mEnumNavigation == EnumNavigation.POST_DETAILS) {
            navigateToRequestDetailsScreen(mTempPost);
        } else {
            checkLoginAndNavigate(mEnumNavigation);
        }
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
                                new AuthUI.IdpConfig.GoogleBuilder().build()
                                //new AuthUI.IdpConfig.FacebookBuilder().build()
                        ))
                        .setTheme(R.style.AppThemeFirebaseAuth)
                        .setLogo(R.drawable.ic_big_logo)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
