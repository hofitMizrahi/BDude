package com.edudb.bdude.ui.flow.login.presenter;

import com.edudb.bdude.ui.flow.login.contract.LoginContract;

import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter {

    @Inject
    LoginContract.View mView;

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void onStart() {
    }
}
