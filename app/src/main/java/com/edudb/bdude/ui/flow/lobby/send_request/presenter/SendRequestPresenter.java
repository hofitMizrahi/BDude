package com.edudb.bdude.ui.flow.lobby.send_request.presenter;

import com.edudb.bdude.db.DatabaseController;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.ui.flow.lobby.send_request.contract.SendRequestContract;
import javax.inject.Inject;

@PerActivity
public class SendRequestPresenter implements SendRequestContract.Presenter {

    @Inject
    HelpRequest mHelpRequest;

    @Inject
    SendRequestContract.View mView;

    @Inject
    DatabaseController mDbController;

    @Inject
    public SendRequestPresenter() {
    }

    private void onComplete(Void var){
        mView.hideProgressBar();
        mView.getActivity().finish();
        mView.checkLoginAndNavigate(EnumNavigation.MY_REQUESTS);
        mView.hideProgressBar();
    }


    @Override
    public void onStart() {
        mView.initViews();
    }

    public void sendRequest() {
        mView.displayProgressBar();
        mDbController.createRequest(mHelpRequest, this::onComplete);
    }
}
