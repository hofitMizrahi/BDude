package com.edudb.bdude.ui.flow.intro.container.view;

import android.Manifest;
import android.app.Fragment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.di.components.DaggerIntroTermsComponent;
import com.edudb.bdude.di.modules.IntroTermsModule;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.flow.intro.container.contract.IntroTermsContract;
import com.edudb.bdude.ui.flow.intro.container.presenter.IntroTermsPresenter;
import com.edudb.bdude.ui.flow.intro.health_terms.view.HealthTermsFragment;
import com.edudb.bdude.ui.flow.intro.location_approve.view.LocationTermsFragment;
import com.edudb.bdude.ui.flow.intro.welcom.view.WelcomeTermsFragment;
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
        DaggerIntroTermsComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
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
        addFragment(getSupportFragmentManager(), R.id.container, LocationTermsFragment.getInstance(), true,"");
    }

    @Override
    public void navigateHealthWarningsFragment() {
        addFragment(getSupportFragmentManager(), R.id.container, HealthTermsFragment.getInstance(), true,"");
    }

    @Override
    public void onBackPressed() {

        if(getFragmentCount() == 0){
            closeApp();
        }else {
            super.onBackPressed();
        }
    }

    protected int getFragmentCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }
}
