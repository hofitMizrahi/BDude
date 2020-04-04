package com.edudb.bdude.ui.flow.intro.location_approve.presenter;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.intro.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.intro.location_approve.contract.LocationTermsContract;
import javax.inject.Inject;

@PerFragment
public class LocationTermsPresenter implements LocationTermsContract.Presenter {

    @Inject
    IntroTermsPresenter mContainerPresenter;

    @Inject
    LocationTermsContract.View mView;

    @Inject
    LocationTermsPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void nextStep() {
        mContainerPresenter.navigateToNextStep(mView.getStepNumber());
    }

    @Override
    public void askLocation() {
        mView.checkLocation();
    }
}
