package com.edudb.bdude.ui.flow.intro.health_terms.presenter;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.ui.flow.intro.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.intro.health_terms.contract.HealthTermsContract;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Inject;

@PerFragment
public class HealthTermsPresenter implements HealthTermsContract.Presenter {

    @Inject
    HealthTermsContract.View mView;

    @Inject
    IntroTermsPresenter mContainerPresenter;

    @Inject
    FirebaseRemoteConfig mConfiguration;

    @Inject
    HealthTermsPresenter() {
    }

    @Override
    public void onStart() {
           mView.displayProgressBar();
        mConfiguration.fetchAndActivate().addOnCompleteListener(task ->
                mView.initText(mConfiguration.getString(Constants.HEALTH_WARNING_IL_KEY)));
           mView.hideProgressBar();
    }

    @Override
    public void nextStep() {
        mContainerPresenter.navigateToNextStep(mView.getStepNumber());
    }
}
