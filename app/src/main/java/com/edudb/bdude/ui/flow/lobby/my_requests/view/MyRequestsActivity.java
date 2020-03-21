package com.edudb.bdude.ui.flow.lobby.my_requests.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.components.DaggerMyRequestsComponent;
import com.edudb.bdude.di.modules.MyRequestsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract;
import com.edudb.bdude.ui.flow.lobby.my_requests.presenter.MyRequestsPresenter;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter.MyRequestsRecyclerAdapter;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class MyRequestsActivity extends BaseActivity implements MyRequestsContract.View {

    @Inject
    MyRequestsPresenter mPresenter;

    @Inject
    User mCurrentUser;

    @Inject
    MyRequestsRecyclerAdapter mAdapter;

    @BindView(R.id.my_requests_recycler_view)
    RecyclerView mRecycler;

    @BindView(R.id.title_name)
    TextView mTitleWithUserName;

    @BindView(R.id.empty_view)
    TextView mEmptyViewTxt;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_my_requests;
    }

    @Override
    public void initDependencies() {
        DaggerMyRequestsComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .myRequestsModule(new MyRequestsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void displayList(List<HelpRequest> helpRequests) {
        mEmptyViewTxt.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setDate(helpRequests, mPresenter);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        String str = "";
        if(mCurrentUser.getName() != null && !mCurrentUser.equals("")){
            str = mCurrentUser.getName();
        }
        mTitleWithUserName.setText(String.format("%s %s", getString(R.string.hello), str));
    }

    @Override
    public void showEmptyView() {
        mEmptyViewTxt.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
    }

    @Override
    public void refreshList(List<HelpRequest> list) {
        mAdapter.refreshData(list);
        mAdapter.notifyDataSetChanged();
    }
}
