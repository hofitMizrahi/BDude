package com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.edudb.bdude.db.AlgoliaUtils;
import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.algolia.AlgoliaModel;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.shared_preferences.SharedPrefsController;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class HelpRequestsListPresenter implements HelpRequestsListContract.Presenter {

    private AlgoliaModel mAlgoliaModel;
    private List<Post> mSearchResultItems;

    @Inject
    Query mQuery;

    @Inject
    Index mIndex;

    @Inject
    HelpRequestsListContract.View mView;

    @Inject
    SharedPrefsController mSharedPrefsController;

    @Inject
    FirebaseDbHelper mDataBase;

    @Inject
    HelpRequestsListPresenter() {
    }

    @Override
    public void onStart() {

        mView.displayProgressBar();

        boolean showTermsOfUse = mSharedPrefsController.getBoolean(Constants.SHOW_TERMS_OF_USE);

        if (!showTermsOfUse) {
            mSharedPrefsController.putBoolean(Constants.SHOW_TERMS_OF_USE, true);
            mView.navigateTermsOfUseScreen();
        } else {

            //TODO check location from gps if have permission && gps open - if not use default locatiom
            mView.checkLocation();
        }

        //TODO add location to query

        mIndex.searchAsync(mQuery, this::onSearchComplete);
        //mDataBase.getAllRequestsList(this::displayList);
    }

    private void onSearchComplete(JSONObject jsonObject, AlgoliaException e) {
        mAlgoliaModel = AlgoliaUtils.getAlgoliaResult(jsonObject, e, mSearchResultItems = new ArrayList<>());
        displayList();
    }

    private void displayList() {

        if (mSearchResultItems != null && mSearchResultItems.size() > 0) {
            mView.displayDataList(mSearchResultItems);
        } else {
            mView.displayEmptyView();
        }
        mView.hideProgressBar();
    }

    @Override
    public void createHelpRequestClicked() {
        mView.checkLoginAndNavigate(EnumNavigation.CREATE_POST);
    }

    @Override
    public void onItemClicked(Post request) {
        checkUserLogIn(request);
    }

    private void checkUserLogIn(Post request) {
        if (!SessionManager.getInstance().isUserLogin()) {
            mView.startLogin();
        } else {
            mView.navigateToRequestDetailsScreen(request);
        }
    }
}
