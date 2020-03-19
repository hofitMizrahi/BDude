package com.edudb.bdude;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edudb.bdude.ui.base.BaseActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void signIn(View view) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
//                                new AuthUI.IdpConfig.FacebookBuilder().build(), TODO add facebook_application_id, https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#facebook, https://console.firebase.google.com/project/bdude-d4c05/authentication/providers
//                                new AuthUI.IdpConfig.TwitterBuilder().build(),
//                                new AuthUI.IdpConfig.MicrosoftBuilder().build(),
//                                new AuthUI.IdpConfig.YahooBuilder().build(),
//                                new AuthUI.IdpConfig.AppleBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.PhoneBuilder().build()
                                //                       ,new AuthUI.IdpConfig.AnonymousBuilder().build()
                        ))
                        .setLogo(R.mipmap.ic_launcher)
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                showSnackbar("Successfully signed in!");
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

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public void signOut(View view) {
        AuthUI.getInstance().signOut(this);
    }

    @Override
    public int getLayoutResource() {
        return 0;
    }

    @Override
    public void initDependencies() {

    }
}
