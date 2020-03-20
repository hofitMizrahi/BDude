package com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter;

import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;
import javax.inject.Inject;

@PerActivity
public class HelpRequestsListPresenter implements HelpRequestsListContract.Presenter {

    @Inject
    HelpRequestsListContract.View mView;

    @Inject
    FirebaseDbHelper mDataBase;

    @Inject
    public HelpRequestsListPresenter() {
    }

    @Override
    public void onStart() {
        mDataBase.createRequest();
    }

    @Override
    public void createHelpRequestClicked() {

        if(!SessionManager.getInstance().isUserLogin()){
            mView.startLogin();
        }else {
            //TODO navigate to createNewRequest activity
        }
    }
}
