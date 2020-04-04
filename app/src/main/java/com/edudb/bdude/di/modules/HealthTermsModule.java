package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.intro.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.intro.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.intro.health_terms.contract.HealthTermsContract;

import dagger.Module;
import dagger.Provides;

@Module
public class HealthTermsModule {

    private HealthTermsContract.View mView;

    public HealthTermsModule(HealthTermsContract.View mView) {
        this.mView = mView;
    }

    @PerFragment
    @Provides
    HealthTermsContract.View provideView(){
        return  mView;
    }

    @PerFragment
    @Provides
    IntroTermsPresenter provideContainerPresenter(){
        return  ((IntroTermsActivity) mView.getActivity()).getPresenter();
    }
}
