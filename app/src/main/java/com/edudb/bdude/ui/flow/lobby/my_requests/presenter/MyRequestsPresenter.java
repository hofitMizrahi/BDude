package com.edudb.bdude.ui.flow.lobby.my_requests.presenter;

import com.edudb.bdude.db.DatabaseController;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import javax.inject.Inject;

public class MyRequestsPresenter implements MyRequestsContract.Presenter {

    private List<HelpRequest> mMyList;

    @Inject
    DatabaseController mDataBase;

    @Inject
    MyRequestsContract.View mView;

    @Override
    public void onStart() {
    }

    private void displayList(List<HelpRequest> posts) {
        if(posts != null){
            mMyList = posts;
            mView.displayList(posts);
        }
        mView.hideProgressBar();
    }

    @Inject
    MyRequestsPresenter() {
    }

    @Override
    public void selectLocationClicked() {
        mView.openMap();
    }

    @Override
    public void updateLocation(GeoPoint location) {
        mView.displayProgressBar();
        mDataBase.updateUserLocation(mView.getActivity(), location, this::onUpdateSuccess);
    }

    private void onUpdateSuccess(User user) {
        mView.setCurrentUser();
        onStartReloadData();
    }

    @Override
    public void onStartReloadData() {
        mView.displayProgressBar();
        mView.initView();
        mDataBase.getMyRequests(this::displayList);
    }

    @Override
    public void setUserPhoneClicked(String s) {
        mDataBase.updateUserPhone(mView.getActivity(), s, this::onUpdateSuccess);
    }

    @Override
    public void saveUserName(String name) {
        mDataBase.updateUserName(mView.getActivity(), name, this::onUpdateSuccess);
    }

    @Override
    public void onItemDeleteClicked(HelpRequest request) {
        mView.displayProgressBar();
        mDataBase.deleteRequest(request.getId(), aVoid -> {
            mMyList.remove(request);

            if(mMyList != null){
                mView.refreshList(mMyList);
            }
            mView.hideProgressBar();
        });
    }
}
