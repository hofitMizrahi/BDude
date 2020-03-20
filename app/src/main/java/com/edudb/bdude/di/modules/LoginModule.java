package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.login.contract.LoginContract;
import com.edudb.bdude.ui.flow.login.presenter.LoginPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private LoginContract.View mView;

    public LoginModule(LoginContract.View view) {
        mView = view;
    }

    @PerActivity
    @Provides
    LoginContract.View provideView(){
        return mView;
    }

    @PerActivity
    @Provides
    LoginContract.Presenter providePresenter(LoginPresenter presenter){
        return presenter;
    }
}
