package com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter;

import android.util.Log;

import com.algolia.search.saas.AbstractQuery;
import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.edudb.bdude.db.AlgoliaUtils;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.db.modules.algolia.AlgoliaModel;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.shared_preferences.SharedPrefsController;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class HelpRequestsListPresenter implements HelpRequestsListContract.Presenter {

    private final int GAP_TO_LOAD_MORE = 20;
    private List<Post> mSearchResultItems;
    private AlgoliaModel mResult;

    @Inject
    Query mQuery;

    @Inject
    Index mIndex;

    @Inject
    HelpRequestsListContract.View mView;

    @Inject
    SharedPrefsController mSharedPrefsController;

    private boolean mIsLoading;

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
        }

        mQuery.setPage(0);
        mQuery.setAroundLatLng(new AbstractQuery.LatLng(LocationHelper.mLastLocation.getLatitude(), LocationHelper.mLastLocation.getLongitude()));
        mIndex.searchAsync(mQuery, this::onSearchComplete);
    }

    private void onSearchComplete(JSONObject jsonObject, AlgoliaException e) {
        mResult = AlgoliaUtils.getAlgoliaResult(jsonObject, e, mSearchResultItems = new ArrayList<>());
        if(mResult != null) {
            displayList();
        }
    }

    private void displayList() {

        if (mSearchResultItems != null && mSearchResultItems.size() > 0) {
            mView.displayDataList(mSearchResultItems);
        } else {
            mView.displayEmptyView();
        }
        mView.hideProgressBar();
    }

    public void loadMore() {
        Log.d("algolia", "loadMore");
        mIsLoading = true;
        if ((mSearchResultItems.size() / mResult.getHitsPerPage()) - 1 == mQuery.getPage()) {
            mView.displayProgressBar();
            Integer currentPage = mQuery.getPage();
            currentPage = currentPage == null ? 0 : currentPage;
            mQuery.setPage(currentPage + 1);
            mIndex.searchAsync(mQuery, this::onLoadMoreComplete);
        }
    }

    @Override
    public boolean canLoadMore() {
        return mResult != null && mResult.getPage() < mResult.getNbPages() && !mIsLoading && mResult.getNbHits() > mSearchResultItems.size();
    }

    private void onLoadMoreComplete(JSONObject jsonObject, AlgoliaException e) {
        mIsLoading = false;
        List<Post> items = new ArrayList<>();
        mResult = AlgoliaUtils.getAlgoliaResult(jsonObject, e, items);
        mSearchResultItems.addAll(items);
        displayResults();
        mView.hideProgressBar();
    }

    private void displayResults() {
        if (mQuery.getPage() != null || mQuery.getPage() != 0) {
            mView.displayMoreResults();
        }
    }

    @Override
    public void createHelpRequestClicked() {
        mView.checkLoginAndNavigate(EnumNavigation.CREATE_POST);
    }

    @Override
    public void onResultsScroll(int lastVisibleItem) {
        if (lastVisibleItem >= mSearchResultItems.size() - GAP_TO_LOAD_MORE && canLoadMore()) {
            loadMore();
        }
    }

    @Override
    public List<Post> getSearchResultItems() {
        return mSearchResultItems;
    }

    @Override
    public void onItemClicked(Post request) {
        checkUserLogIn(request);
    }

    private void checkUserLogIn(Post request) {
        mView.navigateToRequestDetailsScreen(request);
    }
}
