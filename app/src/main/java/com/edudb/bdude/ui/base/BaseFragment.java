package com.edudb.bdude.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.enums.EnumNavigation;
import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.flow.terms_of_use.container.presenter.IntroTermsPresenter;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements BaseView {

    private BaseActivity mBaseActivity;

    public BaseFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        if (getPresenter() != null) {
            getPresenter().onStart();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initDependencies();
    }

    protected abstract void initDependencies();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) getActivity();
        }
    }

    @Override
    public void displayProgressBar() {
        mBaseActivity.displayProgressBar();
    }

    @Override
    public void hideProgressBar() {
        mBaseActivity.hideProgressBar();
    }

    @Override
    public void openMap() {
        mBaseActivity.openMap();
    }

    @Override
    public void checkLocation() {
        mBaseActivity.checkLocation();
    }

    @Override
    public void checkLoginAndNavigate(EnumNavigation tag) {
        mBaseActivity.checkLoginAndNavigate(tag);
    }

    @Override
    public void navigateToRequestDetailsScreen(Post request) {
        mBaseActivity.navigateToRequestDetailsScreen(request);
    }

    public BasePresenter getPresenter() {
        return null;
    }
}
