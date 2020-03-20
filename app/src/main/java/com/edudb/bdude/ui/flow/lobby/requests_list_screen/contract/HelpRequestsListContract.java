package com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract;

import com.edudb.bdude.ui.base.BasePresenter;

public interface HelpRequestsListContract {

    interface View{
        void navigateToLoginScreen();

        void startLogin();
    }

    interface Presenter extends BasePresenter {
        void createHelpRequestClicked();
    }
}
