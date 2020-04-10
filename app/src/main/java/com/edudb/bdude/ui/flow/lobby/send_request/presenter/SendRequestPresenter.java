package com.edudb.bdude.ui.flow.lobby.send_request.presenter;

import com.edudb.bdude.db.DatabaseController;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.send_request.contract.SendRequestContract;
import javax.inject.Inject;

@PerActivity
public class SendRequestPresenter implements SendRequestContract.Presenter {

    @Inject
    SendRequestContract.View mView;

    @Inject
    DatabaseController mDbController;

    @Inject
    public SendRequestPresenter() {
    }

    @Override
    public void onStart() {
        mView.initViews();
    }
}
