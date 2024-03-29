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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.edudb.bdude.BuildConfig;
import com.edudb.bdude.R;
import com.edudb.bdude.db.FirebaseAnalyticsHelper;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.general.BaseActionBar;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.CreateHelpRequestActivity;
import com.edudb.bdude.ui.flow.lobby.my_requests.presenter.MyRequestsPresenter;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.MyRequestsActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.view.RequestDetailsActivity;
import com.edudb.bdude.ui.flow.intro.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.lobby.main_screen.view.HelpRequestsListActivity;
import com.edudb.bdude.ui.flow.lobby.send_request.view.SendRequestActivity;
import com.edudb.bdude.ui.flow.login.view.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.ButterKnife;
import static com.edudb.bdude.location.LocationHelper.GPS_OPEN;
import static com.edudb.bdude.location.LocationHelper.LOCATION_PERMISSION_REQ_CODE;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public static final int RC_SIGN_IN = 5;
    public static final int PLACE_PICKER_REQUEST = 6;
    public static final String REQUEST_DETAILS = "request_details";

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

    public void addFragment(FragmentManager fragmentManager, int containerId, BaseFragment fragment, boolean addToBackStack, String tag) {
        if (!isFinishing()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction().add(containerId, fragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commitAllowingStateLoss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareMessageEvent(BaseActionBar.ShareMessageEvent event) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "B-dude");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        mBaseActionBar = getCustomActionBar();
        mActionBarContainer.removeAllViews();

        if (this instanceof SendRequestActivity
                || this instanceof MyRequestsActivity
                || this instanceof IntroTermsActivity
                || this instanceof LoginActivity
                || this instanceof CreateHelpRequestActivity
                || this instanceof RequestDetailsActivity) {
            mBaseActionBar.hideActionBar();
        } else {
            mBaseActionBar.setAddress(LocationHelper.getLocationName(this, LocationHelper.mLastLocation));
            mBaseActionBar.showActionBar();
        }
        mActionBarContainer.addView(mBaseActionBar);
        mBaseActionBar.postInvalidate();
    }

    public void navigateToMyRequest() {

        TaskStackBuilder.create(this)
                .addNextIntent(new Intent(this, HelpRequestsListActivity.class))
                .addNextIntent(new Intent(this, MyRequestsActivity.class))
                .startActivities();
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

    public void closeApp() {
        DialogUtil.getSingleButtonInstance(this, (dialog, i) -> {

                    this.finishAffinity();

                }, getString(R.string.pay_attention_please)
                , getString(R.string.exit_app)
                , getString(R.string.yes), true);
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
                    navigateToMyRequest();
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

        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == Activity.RESULT_OK && data != null) {

                if (getActivity() instanceof CreateHelpRequestActivity) {
                    ((CreateHelpRequestPresenter) getPresenter()).updateLocation(LocationHelper.getLocation(data));

                } else if (getActivity() instanceof MyRequestsActivity) {
                    ((MyRequestsPresenter) getPresenter()).updateLocation(LocationHelper.getLocation(data));

                } else {
                    LocationHelper.setLocation(data);
                    searchByNewLocation();
                }
            } else {
                showSnackbar("מיקום לא נמצא");
            }
        } else if (requestCode == GPS_OPEN) {

            if (!LocationHelper.userNotHavePermission(this) && LocationHelper.isHaveGpsOpen(this)) {
                LocationHelper.checkLastLocation(this);
                searchByNewLocation();
            }

        } else if (requestCode == RC_SIGN_IN && data != null) {
            navigateAfterLogin();
        }
        hideProgressBar();
    }

    private void searchByNewLocation() {
        getCustomActionBar().setAddress(LocationHelper.getLocationName(this, LocationHelper.mLastLocation));
        refreshData();
    }

    protected void refreshData() {
    }

    protected void navigateAfterLogin() {
        if (mEnumNavigation == EnumNavigation.POST_DETAILS) {
            navigateToRequestDetailsScreen(mTempPost);
        } else {
            checkLoginAndNavigate(mEnumNavigation);
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public void startLogin() {
        startActivityForResult(new Intent(this, LoginActivity.class), RC_SIGN_IN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
