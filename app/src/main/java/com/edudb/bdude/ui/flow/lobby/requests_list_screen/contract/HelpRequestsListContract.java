package com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.HelpRequestsRecyclerAdapter;

import java.util.List;

public interface HelpRequestsListContract {

    interface View extends BaseView {
        void startLogin();
        void showTermsOfUse();
        void displayDataList(List<HelpRequest> helpRequests);
        void navigateToCreateNewRequestActivity();
        void displayEmptyView();
        void navigateToRequestDetailsScreen(HelpRequest request);
    }

    interface Presenter extends BasePresenter, HelpRequestsRecyclerAdapter.InteractionListener {
        void createHelpRequestClicked();
    }
}
