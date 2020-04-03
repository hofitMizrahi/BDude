package com.edudb.bdude.ui.flow.terms_of_use.welcom.presenter;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.terms_of_use.welcom.contract.WelcomeTermsContract;
import javax.inject.Inject;

@PerFragment
public class WelcomeTermsPresenter implements WelcomeTermsContract.Presenter {

    @Inject
    IntroTermsPresenter mContainerPresenter;

    @Inject
    WelcomeTermsContract.View mView;

    @Inject
    WelcomeTermsPresenter() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void nextStep() {
        mContainerPresenter.navigateToNextStep(mView.getStepNumber());
    }
}
