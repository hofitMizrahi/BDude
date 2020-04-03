package com.edudb.bdude.ui.flow.terms_of_use.health_terms.presenter;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.terms_of_use.health_terms.contract.HealthTermsContract;
import javax.inject.Inject;

@PerFragment
public class HealthTermsPresenter implements HealthTermsContract.Presenter {

    @Inject
    HealthTermsContract.View mView;

    @Inject
    IntroTermsPresenter mContainerPresenter;

    @Inject
    HealthTermsPresenter() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void nextStep() {
        mContainerPresenter.navigateToNextStep(mView.getStepNumber());
    }
}
