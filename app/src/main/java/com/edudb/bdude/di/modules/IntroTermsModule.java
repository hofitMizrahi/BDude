package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.terms_of_use.container.contract.IntroTermsContract;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class IntroTermsModule {

    private IntroTermsContract.View mView;

    public IntroTermsModule(IntroTermsContract.View view) {
        mView = view;
    }

    @Provides
    @PerActivity
    IntroTermsContract.View provideView(){
        return mView;
    }

    @Provides
    @PerActivity
    IntroTermsContract.Presenter providePresenter(IntroTermsPresenter presenter){
        return presenter;
    }
}
