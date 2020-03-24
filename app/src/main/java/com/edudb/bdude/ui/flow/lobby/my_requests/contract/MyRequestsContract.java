package com.edudb.bdude.ui.flow.lobby.my_requests.contract;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter.MyRequestsRecyclerAdapter;

import java.util.List;

public interface MyRequestsContract {

    interface View extends BaseView {
        void displayList(List<HelpRequest> posts);
        void showEmptyView();
        void refreshList(List<HelpRequest> list);
        void initView();
    }

    interface Presenter extends BasePresenter , MyRequestsRecyclerAdapter.IEventListener {
        void createHelpRequestClicked();
    }
}
