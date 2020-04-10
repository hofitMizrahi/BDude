package com.edudb.bdude.ui.flow.lobby.send_request.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.di.components.DaggerSendRequestComponent;
import com.edudb.bdude.di.modules.SendRequestModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.send_request.contract.SendRequestContract;
import com.edudb.bdude.ui.flow.lobby.send_request.presenter.SendRequestPresenter;

import javax.inject.Inject;

public class SendRequestActivity extends BaseActivity implements SendRequestContract.View {

    @Inject
    SendRequestPresenter mPresenter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_send_request;
    }

    @Override
    public void initDependencies() {
        DaggerSendRequestComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .sendRequestModule(new SendRequestModule(this))
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
