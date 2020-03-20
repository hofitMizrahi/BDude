package com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter;

import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;
import java.util.List;
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
        mDataBase.getAllRequestsList(this::displayList);
    }

    private void displayList(List<HelpRequest> helpRequests) {

        if(helpRequests != null && helpRequests.size() > 0){
            mView.displayDataList(helpRequests);
        }else {
            mView.displayEmptyView();
        }
    }

    @Override
    public void createHelpRequestClicked() {

        if(!SessionManager.getInstance().isUserLogin()){
            mView.startLogin();
        }else {
            mView.navigateToCreateNewRequestActivity();
        }
    }

    @Override
    public void onItemClicked(HelpRequest request) {
        checkUserLogIn(request);
    }

    private void checkUserLogIn(HelpRequest request){
        if(!SessionManager.getInstance().isUserLogin()){
            mView.startLogin();
        }else {
            mView.navigateToRequestDetailsScreen(request);
        }
    }
}
