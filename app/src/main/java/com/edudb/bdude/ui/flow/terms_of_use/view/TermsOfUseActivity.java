package com.edudb.bdude.ui.flow.terms_of_use.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerTermsOfUseComponent;
import com.edudb.bdude.di.modules.TermsOfUseModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.terms_of_use.contract.TermsOfUseContract;
import com.edudb.bdude.ui.flow.terms_of_use.presenter.TermsOfUsePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TermsOfUseActivity extends BaseActivity implements TermsOfUseContract.View {

    @Inject
    TermsOfUsePresenter mPresenter;

    @OnClick(R.id.btnApprove)
    void onApproveBtnClicked(){
        finish();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_terms_of_use;
    }

    @Override
    public void initDependencies() {
        DaggerTermsOfUseComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .termsOfUseModule(new TermsOfUseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }
}
