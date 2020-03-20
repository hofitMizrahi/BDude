package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerHelpRequestsListComponent;
import com.edudb.bdude.di.modules.HelpRequestsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter.HelpRequestsListPresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.HelpRequestsRecyclerAdapter;
import com.edudb.bdude.ui.flow.login.view.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Objects;

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
        return R.layout.activity_reuests_list;
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

}
