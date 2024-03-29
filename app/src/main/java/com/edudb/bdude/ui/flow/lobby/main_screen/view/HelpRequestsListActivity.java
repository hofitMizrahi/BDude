package com.edudb.bdude.ui.flow.lobby.main_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.components.DaggerHelpRequestsListComponent;
import com.edudb.bdude.di.modules.HelpRequestsModule;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.shared_preferences.SharedPrefsController;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.flow.intro.container.view.IntroTermsActivity;
import com.edudb.bdude.ui.flow.lobby.main_screen.contract.HelpRequestsListContract;
import com.edudb.bdude.ui.flow.lobby.main_screen.presenter.HelpRequestsListPresenter;
import com.edudb.bdude.ui.flow.lobby.main_screen.view.adapter.HelpRequestsRecyclerAdapter;
import com.google.gson.Gson;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class HelpRequestsListActivity extends BaseActivity implements HelpRequestsListContract.View {

    @Inject
    HelpRequestsRecyclerAdapter mAdapter;

    @Inject
    HelpRequestsListPresenter mPresenter;

    @Inject
    SharedPrefsController sharedPrefsController;

    @BindView(R.id.request_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_view)
    TextView mEmptyViewTxt;

    @OnClick(R.id.helpContinueBtn)
    void startHelpRequest() {
        mPresenter.createHelpRequestClicked();
    }

    @OnClick(R.id.btn_private_area)
    void navigateToUserDetails() {
        checkLoginAndNavigate(EnumNavigation.MY_REQUESTS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocalUser();
    }

    private void checkLocalUser() {
        Gson gson = new Gson();
        String json = sharedPrefsController.getString(Constants.LOGGED_IN_USER);
        User user = gson.fromJson(json, User.class);
        if (user != null) {
            SessionManager.getInstance().setCurrentUser(user);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main_requests_list;
    }

    @Override
    public void initDependencies() {
        DaggerHelpRequestsListComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .helpRequestsModule(new HelpRequestsModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void refreshData() {
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
        addTimerToLoadMore();
    }

    @Override
    public void displayDataList(List<Post> posts) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setData(posts, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayEmptyView() {
        mEmptyViewTxt.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void navigateTermsOfUseScreen() {
        startActivity(new Intent(this, IntroTermsActivity.class));
    }

    @Override
    public void displayMoreResults() {
        if (mAdapter != null) {
            Parcelable recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();
            mAdapter.setList(mPresenter.getSearchResultItems());
            mAdapter.notifyDataSetChanged();
            mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
        hideProgressBar();
    }

    private void addTimerToLoadMore() {
        Observable.interval(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::checkLoadMore)
                .subscribe();
    }

    private <R> R checkLoadMore(Long aLong) {
        LinearLayoutManager manager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
        if (manager != null) {
            int lastVisibleItem = manager.findLastVisibleItemPosition();
            if (lastVisibleItem > mPresenter.getSearchResultItems().size() - 5) {
                mPresenter.onResultsScroll(lastVisibleItem);
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        closeApp();
    }
}
