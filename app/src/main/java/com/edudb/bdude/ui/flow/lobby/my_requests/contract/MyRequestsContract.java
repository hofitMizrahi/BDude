package com.edudb.bdude.ui.flow.lobby.my_requests.contract;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter.MyRequestsRecyclerAdapter;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public interface MyRequestsContract {

    interface View extends BaseView {
        void displayList(List<HelpRequest> posts);
        void refreshList(List<HelpRequest> list);
        void initView();
        void setCurrentUser();
    }

    interface Presenter extends BasePresenter , MyRequestsRecyclerAdapter.IEventListener {
        void selectLocationClicked();
        void updateLocation(GeoPoint location);
        void onStartReloadData();
        void setUserPhoneClicked(String s);
        void saveUserName(String name);
    }
}
