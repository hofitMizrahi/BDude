package com.edudb.bdude.ui.flow.terms_of_use.presenter;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.ui.flow.terms_of_use.contract.TermsOfUseContract;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Inject;

@PerActivity
public class TermsOfUsePresenter implements TermsOfUseContract.Presenter {

    @Inject
    TermsOfUseContract.View mView;

    @Inject
    FirebaseRemoteConfig mConfiguration;

    @Inject
    TermsOfUsePresenter() {
    }

    @Override
    public void onStart() {
        mView.checkLocation();
        mConfiguration.fetchAndActivate().addOnCompleteListener(task ->
                mView.initText(mConfiguration.getString(Constants.HEALTH_WARNING_KEY)));
    }
}
