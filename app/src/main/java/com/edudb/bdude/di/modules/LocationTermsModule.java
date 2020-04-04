package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.intro.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.intro.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.intro.location_approve.contract.LocationTermsContract;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationTermsModule {

    private LocationTermsContract.View mView;

    public LocationTermsModule(LocationTermsContract.View mView) {
        this.mView = mView;
    }

    @PerFragment
    @Provides
    LocationTermsContract.View provideView(){
        return  mView;
    }

    @PerFragment
    @Provides
    IntroTermsPresenter provideContainerPresenter(){
        return  ((IntroTermsActivity) mView.getActivity()).getPresenter();
    }
}
