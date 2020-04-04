package com.edudb.bdude.ui.flow.intro.location_approve.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.di.components.DaggerLocationTermsComponent;
import com.edudb.bdude.di.modules.LocationTermsModule;
import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BaseFragment;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.intro.location_approve.contract.LocationTermsContract;
import com.edudb.bdude.ui.flow.intro.location_approve.presenter.LocationTermsPresenter;
import javax.inject.Inject;

import butterknife.OnClick;

public class LocationTermsFragment extends BaseFragment implements LocationTermsContract.View{

    @Inject
    LocationTermsPresenter mPresenter;

    public static LocationTermsFragment getInstance(){
        return new LocationTermsFragment();
    }

    @OnClick(R.id.nextBtn)
    void onNextBtnClicked(){
        mPresenter.askLocation();
        mPresenter.nextStep();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initDependencies() {
        DaggerLocationTermsComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .locationTermsModule(new LocationTermsModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_terms, container, false);
    }

    @Override
    public void initView(String string) {
    }

    @Override
    public EnumStepNumber getStepNumber(){
        return EnumStepNumber.TWO;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }
}
