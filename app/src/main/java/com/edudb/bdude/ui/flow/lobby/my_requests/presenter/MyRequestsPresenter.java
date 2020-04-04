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
        if(posts != null && posts.size() > 0){
            mMyList = posts;
            mView.displayList(posts);
        }else {
            mView.showEmptyView();
        }
        mView.hideProgressBar();
    }

    @Inject
    public MyRequestsPresenter() {
    }

    @Override
    public void onItemDeleteClicked(HelpRequest request) {
        mView.displayProgressBar();
        mDataBase.deleteRequest(request.getId(), aVoid -> {
            mMyList.remove(request);

            if(mMyList != null && mMyList.size() > 0){
                mView.refreshList(mMyList);
            }else {
                mView.showEmptyView();
            }
            mView.hideProgressBar();
        });
    }

    @Override
    public void createHelpRequestClicked() {
        mView.checkLoginAndNavigate(EnumNavigation.CREATE_POST);
    }
}
