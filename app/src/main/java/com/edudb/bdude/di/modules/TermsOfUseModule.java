package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.terms_of_use.contract.TermsOfUseContract;
import com.edudb.bdude.ui.flow.terms_of_use.presenter.TermsOfUsePresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class TermsOfUseModule {

    private TermsOfUseContract.View mView;

    public TermsOfUseModule(TermsOfUseContract.View view) {
        mView = view;
    }

    @Provides
    @PerActivity
    TermsOfUseContract.View provideView(){
        return mView;
    }

    @Provides
    @PerActivity
    TermsOfUseContract.Presenter providePresenter(TermsOfUsePresenter presenter){
        return presenter;
    }
}
