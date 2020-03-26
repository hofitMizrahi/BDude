package com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter;

import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.google.firebase.firestore.GeoPoint;

import java.security.PrivateKey;

import javax.inject.Inject;

public class CreateHelpRequestPresenter implements CreateHelpRequestContract.Presenter{

    private GeoPoint mSelectedLocation;

    @Inject
    FirebaseDbHelper mDataBase;

    @Inject
    CreateHelpRequestContract.View mView;

    @Override
    public void onStart() {
        mView.initViews();
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

        //TODO navigate to my request screen
        mView.hideProgressBar();
        mView.getActivity().finish();
    }
}
