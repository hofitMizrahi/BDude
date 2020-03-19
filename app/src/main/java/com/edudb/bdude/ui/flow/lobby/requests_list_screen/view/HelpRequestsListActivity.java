package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerHelpRequestsListComponent;
import com.edudb.bdude.di.modules.HelpRequestsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.HelpRequestsRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

public class HelpRequestsListActivity extends BaseActivity implements HelpRequestsListContract.View{

    @Inject
    HelpRequestsRecyclerAdapter mAdapter;

    @Inject
    HelpRequestsListContract.View mView;

    @BindView(R.id.request_recycler_view)
    RecyclerView mRecyclerView;

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
}
