package com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.ui.base.BasePresenter;

import java.util.List;

public interface HelpRequestsListContract {

    interface View{
        void startLogin();
        void displayDataList(List<HelpRequest> helpRequests);
        void navigateToCreateNewRequestActivity();
        void displayEmptyView();
    }

    interface Presenter extends BasePresenter {
        void createHelpRequestClicked();
    }
}
