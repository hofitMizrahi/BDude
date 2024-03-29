package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.intro.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.intro.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.intro.welcom.contract.WelcomeTermsContract;

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
