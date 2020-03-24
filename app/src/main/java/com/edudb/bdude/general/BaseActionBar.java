package com.edudb.bdude.general;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.edudb.bdude.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActionBar extends ConstraintLayout {

    private Context mContext;

    @OnClick(R.id.btnLocation)
    void onLocationBtnClicked() {
        EventBus.getDefault().post(new LocationMessageEvent());
    }

    @OnClick(R.id.btnShare)
    void onShareBtnClicked() {
        EventBus.getDefault().post(new ShareMessageEvent());
    }

    @OnClick(R.id.getLocationText)
    void onGetLocationTextClicked() { EventBus.getDefault().post(new LocationMessageEvent()); }

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


    public static class LocationMessageEvent {
    }

    public static class ShareMessageEvent {
    }

    public static class UserRegistrationMessageEvent {
    }
}

