package com.edudb.bdude.di.modules;

import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import com.edudb.bdude.ui.flow.lobby.request_details.view.RequestDetailsActivity;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.terms_of_use.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.terms_of_use.health_terms.contract.HealthTermsContract;

import dagger.Module;
import dagger.Provides;

import static com.edudb.bdude.ui.base.BaseActivity.REQUEST_DETAILS;

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
