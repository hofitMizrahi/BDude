package com.edudb.bdude.ui.flow.lobby.my_requests.presenter;

import com.edudb.bdude.db.DatabaseController;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract;
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
        mView.displayProgressBar();
        mView.initView();
        mDataBase.getMyRequests(this::displayList);
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
