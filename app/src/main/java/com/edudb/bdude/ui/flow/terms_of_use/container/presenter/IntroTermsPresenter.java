package com.edudb.bdude.ui.flow.terms_of_use.container.presenter;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.flow.terms_of_use.container.contract.IntroTermsContract;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import javax.inject.Inject;

@PerActivity
public class IntroTermsPresenter implements IntroTermsContract.Presenter {

    @Inject
    IntroTermsContract.View mView;

    @Inject
    FirebaseRemoteConfig mConfiguration;

    @Inject
    IntroTermsPresenter() {
    }

    @Override
    public void onStart() {
        mView.initViews();
    }

    @Override
    public void navigateToNextStep(EnumStepNumber stepNumber) {
        switch (stepNumber){
            case ONE:
                mView.navigateToLocationFragment();
                break;
            case TWO:
                mView.navigateHealthWarningsFragment();
                break;
            case THREE:
                mView.getActivity().finish();
                break;
        }
    }
}
