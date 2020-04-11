package com.edudb.bdude.general;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    @BindView(R.id.searchContainer)
    View mSearchContainer;

    @BindView(R.id.root)
    View mRoot;

    @BindView(R.id.btnLocation)
    Button askLocation;

    @OnClick({R.id.searchContainer, R.id.btnLocation})
    void onGetLocationTextClicked() {
        EventBus.getDefault().post(new ChangeLocationEvent());
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

    public void hideActionBar() {
        mRoot.setVisibility(GONE);
    }

    public void showActionBar() {
        mRoot.setVisibility(VISIBLE);
    }

    public static class LocationMessageEvent {
    }

    public static class ChangeLocationEvent {
    }

    public static class ShareMessageEvent {
    }

    public static class UserRegistrationMessageEvent {
    }

    public void buttonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setAlpha(100);//MainActivity.this.getColor(R.color.test), PorterDuff.Mode.SCREEN);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().setAlpha(255);
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

}

