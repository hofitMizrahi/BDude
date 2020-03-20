package com.edudb.bdude.ui.flow.lobby.my_requests.contract;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter.MyRequestsRecyclerAdapter;

import java.util.List;

public interface MyRequestsContract {

    interface View{
        void displayList(List<HelpRequest> helpRequests);
        void showEmptyView();
        void refreshList(List<HelpRequest> list);
    }

    interface Presenter extends BasePresenter , MyRequestsRecyclerAdapter.IEventListener {
    }
}
