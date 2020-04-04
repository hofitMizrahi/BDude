package com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter;

import com.edudb.bdude.db.DatabaseController;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

import javax.inject.Inject;

public class CreateHelpRequestPresenter implements CreateHelpRequestContract.Presenter{

    private static final int MAX_REQUESTS = 5;
    private GeoPoint mSelectedLocation;

    @Inject
    DatabaseController mDataBase;

    @Inject
    CreateHelpRequestContract.View mView;

    @Override
    public void onStart() {
        mView.displayProgressBar();
        mView.initViews();
        mDataBase.getMyRequests(this::displayList);
    }

    private void displayList(List<HelpRequest> posts) {
        if(posts == null || posts.size() >= MAX_REQUESTS){
            mView.showNotAskMoreRequestView();
        }
        mView.hideProgressBar();
    }

    @Inject
    CreateHelpRequestPresenter() {
    }

    @Override
    public void sendRequest() {

        mView.displayProgressBar();
        User user = SessionManager.getInstance().getUser();

        HelpRequest post = new HelpRequest();
        post.setTitle(mView.getFullTitle());
        post.setBody(mView.getBody());
        post.setPhoneNumber(mView.getNumber());
        post.setGeoPoint(mSelectedLocation);
        post.setAddress_text(mView.getLocationName());
        post.setUserName(mView.getName());
        post.setUser_ID(user.getUid());
        post.setTimestamp(System.currentTimeMillis());
        mDataBase.createRequest(post, this::onComplete);
    }

    @Override
    public void selectLocationClicked() {
        mView.openMap();
    }

    @Override
    public void changeLocation(GeoPoint location) {
        mSelectedLocation = location;
        mView.changeLocationText(LocationHelper.getLocationName(mView.getActivity(), location));
    }

    private void onComplete(Void var){
        mView.hideProgressBar();
        mView.getActivity().finish();
        mView.checkLoginAndNavigate(EnumNavigation.MY_REQUESTS);
    }
}
