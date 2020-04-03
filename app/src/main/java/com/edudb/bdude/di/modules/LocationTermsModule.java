package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.terms_of_use.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.terms_of_use.health_terms.contract.HealthTermsContract;
import com.edudb.bdude.ui.flow.terms_of_use.location_approve.contract.LocationTermsContract;

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
