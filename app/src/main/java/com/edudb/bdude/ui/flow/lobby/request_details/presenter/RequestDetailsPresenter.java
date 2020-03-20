package com.edudb.bdude.ui.flow.lobby.request_details.presenter;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import javax.inject.Inject;

@PerActivity
public class RequestDetailsPresenter implements RequestDetailsContract.Presenter{

    @Inject
    RequestDetailsContract.View mView;

    @Inject
    public RequestDetailsPresenter() {
    }

    @Override
    public void onStart() {
    }
}
