package com.edudb.bdude.ui.flow.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.DatabaseController;
import com.edudb.bdude.db.FirebaseAnalyticsHelper;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.components.DaggerLoginComponent;
import com.edudb.bdude.di.modules.LoginModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.login.contract.LoginContract;
import com.edudb.bdude.ui.flow.login.presenter.LoginPresenter;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    public static final String USER_SAVE = "user_details";
    public static final int RC_SIGN_IN = 555;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Inject
    LoginPresenter mPresenter;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.facebook_login)
    void facebookLoginClicked() {

    }

    @OnClick(R.id.google_login)
    void googleLoginClicked() {
        signIn();
    }

    private void signIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    private void onSignInSuccess(GoogleSignInAccount googleSignInAccount) {

    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        //.requestScopes(Drive.SCOPE_APPFOLDER)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void initDependencies() {
        DaggerLoginComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build().inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            Log.d("TAG", "onActivityResult: requestCode = " + requestCode);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.d("TAG", "onActivityResult: successful");
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(getApplicationContext(), "Sign In Failed", Toast.LENGTH_LONG).show();
                Log.d("TAG", "onActivityResult: failed");
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

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "onComplete: successful");
                    if (mAuth.getCurrentUser() != null) {
                        DatabaseController.getInstance().getCurrentUserDetails(mAuth.getCurrentUser().getUid(), this::saveUserDetails);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            private void saveUserDetails(User user) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra(USER_SAVE, user);
                setResult(BaseActivity.RC_SIGN_IN, resultIntent);
                finish();
            }
        });
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public void signOut(View view) {
        AuthUI.getInstance().signOut(this);
    }

    @Override
    public void startLogin() {
    }
}
