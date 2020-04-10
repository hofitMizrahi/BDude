package com.edudb.bdude.general;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.edudb.bdude.R;
import com.edudb.bdude.general.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class CheckedEditText extends ConstraintLayout {

    private Context mContext;

    @BindView(R.id.warning)
    ImageView mWarningIV;

    @BindView(R.id.name_ET)
    EditText mEditText;

    String mRegex;
    boolean mRequired;

    @OnTextChanged(R.id.name_ET)
    void onTextChange(){
        Utils.setViewVisibility(mWarningIV, Utils.isNullOrWhiteSpace(mEditText.getText().toString()), View.GONE);
    }

    public CheckedEditText(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public CheckedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public CheckedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs){
        inflate(getContext(), R.layout.control_checked_edit_text, this);
        ButterKnife.bind(this);

        if (attrs != null) {

            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CheckedEditText);
            String text = a.getString(R.styleable.CheckedEditText_android_text);
            String hint = a.getString(R.styleable.CheckedEditText_android_hint);
            Drawable drawableStart = a.getDrawable(R.styleable.CheckedEditText_drawable_start);
            int inputType = a.getInt(R.styleable.CheckedEditText_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
            mRegex = a.getString(R.styleable.CheckedEditText_regex);
            mRequired = a.getBoolean(R.styleable.CheckedEditText_required, false);
            boolean edit = a.getBoolean(R.styleable.CheckedEditText_edit, true);

            mEditText.setText(text != null ? text : "");
            mEditText.setHint(hint != null ? hint : "");
            mEditText.setInputType(inputType);

            if(drawableStart != null) {
                drawableStart.setBounds(0, 0, 55, 55);
                mEditText.setCompoundDrawablesRelative(drawableStart, null, null, null);
                mEditText.setCompoundDrawablePadding(16);
            }
            Utils.setViewVisibility(mWarningIV, Utils.isNullOrWhiteSpace(mEditText.getText().toString()), View.GONE);

            a.recycle();
        }
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    public void setText(String text) {
        mEditText.setText(text);
    }
}
