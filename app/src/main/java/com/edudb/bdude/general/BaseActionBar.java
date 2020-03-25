package com.edudb.bdude.general;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.edudb.bdude.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActionBar extends ConstraintLayout {

    private Context mContext;

    @BindView(R.id.getLocationText)
    TextView mLocationTxt;

    @BindView(R.id.btnUserRegistration)
    TextView mBtnUserRegistration;

    @BindView(R.id.searchContainer)
    View mSearchContainer;

    @OnClick(R.id.btnShare)
    void onShareBtnClicked() {
        EventBus.getDefault().post(new ShareMessageEvent());
    }

    @OnClick(R.id.searchContainer)
    void onGetLocationTextClicked() { EventBus.getDefault().post(new ChangeLocationEvent()); }

    @OnClick(R.id.btnUserRegistration)
    void onUserRegistrationBtnClicked() {
        EventBus.getDefault().post(new UserRegistrationMessageEvent());
    }

    public BaseActionBar(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public BaseActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    protected void init() {
        inflate(getContext(), R.layout.action_bar_options, this);
        ButterKnife.bind(this);
    }

    public BaseActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void setAddress(String address) {
        mLocationTxt.setText(address);
    }

    public void showSearchLine() {
        mSearchContainer.setVisibility(VISIBLE);
    }

    public void removeSearchLine() {
        mSearchContainer.setVisibility(GONE);
    }

    public void removeLoginIcon() {
        mBtnUserRegistration.setVisibility(GONE);
    }

    public static class LocationMessageEvent {
    }

    public static class ChangeLocationEvent {
    }

    public static class ShareMessageEvent {
    }

    public static class UserRegistrationMessageEvent {
    }
}

