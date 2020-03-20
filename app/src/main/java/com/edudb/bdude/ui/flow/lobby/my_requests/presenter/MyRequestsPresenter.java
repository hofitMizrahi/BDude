package com.edudb.bdude.ui.flow.lobby.my_requests.presenter;

import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract;
import javax.inject.Inject;

public class MyRequestsPresenter implements MyRequestsContract.Presenter {

    @Inject
    FirebaseDbHelper mDataBase;

    @Override
    public void onStart() {
    }

    @Inject
    public MyRequestsPresenter() {
    }
}
