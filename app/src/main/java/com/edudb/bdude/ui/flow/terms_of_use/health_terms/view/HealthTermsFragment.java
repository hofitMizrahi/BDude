package com.edudb.bdude.ui.flow.terms_of_use.health_terms.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerHealthTermsComponent;
import com.edudb.bdude.di.modules.HealthTermsModule;
import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BaseFragment;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.terms_of_use.health_terms.contract.HealthTermsContract;
import com.edudb.bdude.ui.flow.terms_of_use.health_terms.presenter.HealthTermsPresenter;

import javax.inject.Inject;

import butterknife.OnClick;

public class HealthTermsFragment extends BaseFragment implements HealthTermsContract.View {

    @Inject
    HealthTermsPresenter mPresenter;

    @OnClick(R.id.nextBtn)
    void onNextBtnClicked(){
        mPresenter.nextStep();
    }

    public static HealthTermsFragment getInstance(){
        return new HealthTermsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health_terms, container, false);
    }

    @Override
    public void initViews() {
    }

    @Override
    protected void initDependencies() {
        DaggerHealthTermsComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .healthTermsModule(new HealthTermsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public EnumStepNumber getStepNumber(){
        return EnumStepNumber.THREE;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }
}
