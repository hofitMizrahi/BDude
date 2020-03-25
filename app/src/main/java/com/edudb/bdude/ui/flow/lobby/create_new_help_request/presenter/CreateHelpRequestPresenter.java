package com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter;

import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;

import javax.inject.Inject;

public class CreateHelpRequestPresenter implements CreateHelpRequestContract.Presenter{

    @Inject
    FirebaseDbHelper mDataBase;

    @Inject
    CreateHelpRequestContract.View mView;

    @Override
    public void onStart() {
    }

    @Inject
    CreateHelpRequestPresenter() {
    }

    @Override
    public void sendRequest() {

        User user = SessionManager.getInstance().getUser();

        HelpRequest post = new HelpRequest();
        post.setTitle(mView.getFullTitle());
        post.setBody(mView.getBody());
        post.setGender(mView.getGender());
        post.setPhoneNumber(mView.getNumber());
        post.setGeoPoint(mView.getLocation());
        post.setUserName(user.getName());
        post.setUser_ID(user.getUid());

        mDataBase.createRequest(post);
    }
}
