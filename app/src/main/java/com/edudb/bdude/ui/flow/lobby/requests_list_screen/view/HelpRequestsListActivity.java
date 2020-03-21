package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.di.components.DaggerHelpRequestsListComponent;
import com.edudb.bdude.di.modules.HelpRequestsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter.HelpRequestsListPresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.HelpRequestsRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpRequestsListActivity extends BaseActivity implements HelpRequestsListContract.View{

    @Inject
    HelpRequestsRecyclerAdapter mAdapter;

    @Inject
    HelpRequestsListPresenter mPresenter;

    @BindView(R.id.request_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_view)
    TextView mEmptyViewTxt;

    @OnClick(R.id.helpContinueBtn)
    void startHelpRequest(){
        mPresenter.createHelpRequestClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main_requests_list;
    }

    @Override
    public void initDependencies() {
        DaggerHelpRequestsListComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .helpRequestsModule(new HelpRequestsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void displayDataList(List<HelpRequest> helpRequests) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setData(helpRequests, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayEmptyView() {
        mEmptyViewTxt.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }


}
