package com.edudb.bdude.ui.flow.intro.welcom.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.di.components.DaggerWelcomeTermsComponent;
import com.edudb.bdude.di.modules.WelcomeTermsModule;
import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BaseFragment;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.intro.welcom.contract.WelcomeTermsContract;
import com.edudb.bdude.ui.flow.intro.welcom.presenter.WelcomeTermsPresenter;
import javax.inject.Inject;

import butterknife.OnClick;

public class WelcomeTermsFragment extends BaseFragment implements WelcomeTermsContract.View {

    @Inject
    WelcomeTermsPresenter mPresenter;

    @Override
    public void initView(String string) {
    }

    @OnClick(R.id.nextBtn)
    void onNextBtnClicked(){
        mPresenter.nextStep();
    }

    public static WelcomeTermsFragment getInstance(){
        return new WelcomeTermsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_terms, container, false);
    }

    @Override
    protected void initDependencies() {
        DaggerWelcomeTermsComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .welcomeTermsModule(new WelcomeTermsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public EnumStepNumber getStepNumber(){
        return EnumStepNumber.ONE;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }
}
