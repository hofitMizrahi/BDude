package com.edudb.bdude.ui.flow.terms_of_use.presenter;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.terms_of_use.contract.TermsOfUseContract;

import javax.inject.Inject;

@PerActivity
public class TermsOfUsePresenter implements TermsOfUseContract.Presenter {

    @Inject
    TermsOfUseContract.View mView;

    @Inject
    public TermsOfUsePresenter() {
    }

    @Override
    public void onStart() {
        mView.checkLocation();
    }
}
