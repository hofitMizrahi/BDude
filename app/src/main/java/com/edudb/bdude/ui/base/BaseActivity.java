package com.edudb.bdude.ui.base;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.edudb.bdude.R;
import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.MyRequestsActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.view.RequestDetailsActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;
import java.util.Objects;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private static final int RC_SIGN_IN = 5;
    public static final String REQUEST_DETAILS = "request_details";

    private ProgressBar mProgressBar;
    private View mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);
        initDependencies();
        hideProgressBar();
        if (getPresenter() != null) {
            getPresenter().onStart();
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

    public void navigateToCreateNewRequestActivity() {

        //TODO enum -> create new request activity
        if(!SessionManager.getInstance().isUserLogin()){
            startLogin();
        }else {
            // startActivity(new Intent(this, CreateHelpRequestActivity.class));
            startActivity(new Intent(this, MyRequestsActivity.class));
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
        }
    }

    private void saveUserDetails(User user){
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
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.PhoneBuilder().build()
                                //,new AuthUI.IdpConfig.AnonymousBuilder().build()
                        ))
                        .setLogo(R.mipmap.ic_launcher)
                        .build(),
                RC_SIGN_IN);
    }
}
