package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.terms_of_use.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.terms_of_use.location_approve.contract.LocationTermsContract;
import com.edudb.bdude.ui.flow.terms_of_use.welcom.contract.WelcomeTermsContract;

import dagger.Module;
import dagger.Provides;

@Module
public class WelcomeTermsModule {

    private WelcomeTermsContract.View mView;

    public WelcomeTermsModule(WelcomeTermsContract.View mView) {
        this.mView = mView;
    }

    @PerFragment
    @Provides
    WelcomeTermsContract.View provideView(){
        return  mView;
    }

    @PerFragment
    @Provides
    IntroTermsPresenter provideContainerPresenter(){
        return  ((IntroTermsActivity) mView.getActivity()).getPresenter();
    }
}
