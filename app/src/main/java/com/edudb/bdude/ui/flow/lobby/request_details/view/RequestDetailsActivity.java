package com.edudb.bdude.ui.flow.lobby.request_details.view;

import android.widget.Button;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.di.components.DaggerRequestDetailsComponent;
import com.edudb.bdude.di.modules.RequestDetailsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import com.edudb.bdude.ui.flow.lobby.request_details.presenter.RequestDetailsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RequestDetailsActivity extends BaseActivity implements RequestDetailsContract.View {

    private boolean isDial = false;

    @Inject
    RequestDetailsPresenter mPresenter;

    @Inject
    HelpRequest mRequestDetailsObj;

    @BindView(R.id.show_phone)
    Button mNumberBtn;

    @OnClick(R.id.show_phone)
    void onBtnClicked() {
        if (!isDial) {
            mNumberBtn.setText(getString(R.string.call_to_number) + mRequestDetailsObj.getPhone_number());
        } else {
            startDial(mRequestDetailsObj.getPhone_number());
        }
        isDial = true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_request_details;
    }

    @Override
    public void initDependencies() {
        DaggerRequestDetailsComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .requestDetailsModule(new RequestDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {

    }
}
