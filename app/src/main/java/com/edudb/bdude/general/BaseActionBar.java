package com.edudb.bdude.general;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.edudb.bdude.R;
import butterknife.ButterKnife;

public class BaseActionBar extends ConstraintLayout {

    private String mTitle;
    private Context mContext;

//    @BindView(R.id.textViewLeft)
//    @Nullable
//    TextView mTextViewLeft;

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
//
//    @OnClick(R.id.btnClose)
//    public void navigateBack() {
//        if (getContext() instanceof Activity) {
//            ((Activity) getContext()).onBackPressed();
//        }
//    }

}

