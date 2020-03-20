package com.edudb.bdude.ui.flow.login.contract;

import com.edudb.bdude.ui.base.BasePresenter;

public interface LoginContract {

    interface View{
        void startLogin();
    }

    interface Presenter extends BasePresenter {
    }
}
