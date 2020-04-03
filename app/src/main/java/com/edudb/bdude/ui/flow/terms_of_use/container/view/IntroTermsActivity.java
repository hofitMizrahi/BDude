package com.edudb.bdude.ui.flow.terms_of_use.container.view;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerIntroTermsComponent;
import com.edudb.bdude.di.modules.IntroTermsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.flow.terms_of_use.container.contract.IntroTermsContract;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.terms_of_use.health_terms.view.HealthTermsFragment;
import com.edudb.bdude.ui.flow.terms_of_use.location_approve.view.LocationTermsFragment;
import com.edudb.bdude.ui.flow.terms_of_use.welcom.view.WelcomeTermsFragment;
import javax.inject.Inject;

public class IntroTermsActivity extends BaseActivity implements IntroTermsContract.View {

    @Inject
    IntroTermsPresenter mPresenter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_terms_of_use;
    }

    @Override
    public void initDependencies() {
        DaggerIntroTermsComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .introTermsModule(new IntroTermsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public IntroTermsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {

        addFragment(getSupportFragmentManager(), R.id.container, WelcomeTermsFragment.getInstance(), false,"");
    }

    @Override
    public void navigateToLocationFragment() {
        addFragment(getSupportFragmentManager(), R.id.container, LocationTermsFragment.getInstance(), false,"");
    }

    @Override
    public void navigateHealthWarningsFragment() {
        addFragment(getSupportFragmentManager(), R.id.container, HealthTermsFragment.getInstance(), false,"");
    }
}
