package com.edudb.bdude.ui.flow.intro.health_terms.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.di.components.DaggerHealthTermsComponent;
import com.edudb.bdude.di.modules.HealthTermsModule;
import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BaseFragment;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.intro.health_terms.contract.HealthTermsContract;
import com.edudb.bdude.ui.flow.intro.health_terms.presenter.HealthTermsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HealthTermsFragment extends BaseFragment implements HealthTermsContract.View {

    private String mLink;

    @Inject
    HealthTermsPresenter mPresenter;

    @BindView(R.id.healthLink)
    TextView healthLink;

    @BindView(R.id.healthText)
    TextView healthText;

    @OnClick(R.id.nextBtn)
    void onNextBtnClicked() {
        mPresenter.nextStep();
    }

    @OnClick(R.id.healthLink)
    void onLinkClicked() {
        //TODO add this to config
        String url = "https://www.health.gov.il/Subjects/disease/corona/Pages/default.aspx";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public static HealthTermsFragment getInstance() {
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
        DaggerHealthTermsComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .healthTermsModule(new HealthTermsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public EnumStepNumber getStepNumber() {
        return EnumStepNumber.THREE;
    }

    @Override
    public void initText(String terms, String link) {
        healthText.setText(terms);
        healthLink.setText(Html.fromHtml(getString(R.string.link_health_terms)));
        mLink = link;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

}
