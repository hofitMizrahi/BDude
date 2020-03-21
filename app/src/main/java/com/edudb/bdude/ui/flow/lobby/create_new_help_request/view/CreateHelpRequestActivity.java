package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerCreateHelpRequestComponent;
import com.edudb.bdude.di.components.DaggerMyRequestsComponent;
import com.edudb.bdude.di.modules.CreateHelpRequestModule;
import com.edudb.bdude.di.modules.MyRequestsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;

public class CreateHelpRequestActivity extends BaseActivity implements CreateHelpRequestContract.View {

    final String regex = "^(0)[1-9]{1}[0-9]{7}$|^(05)[0-9]{8}$";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    boolean needHelpWithFieldIsValid = false;
    boolean mMoreDetailsFieldIsValid = false;
    boolean mPhoneNumberIsValid = false;

    @Inject
    CreateHelpRequestPresenter mPresenter;

    @BindView(R.id.need_help_with_editT)
    EditText mNeedHelpWith;

    @BindView(R.id.more_details_editT)
    EditText mMoreDetails;

    @BindView(R.id.phone_ET)
    EditText  mPhoneNumber;

    @BindView(R.id.send_help_request)
    Button mHelpButton;

    @BindView((R.id.main_layout))
    LinearLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mMainLayout.setOnTouchListener((v, event) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            checkNeedHelpWithValidity();
            checkMoreDetailsValidity();
            checkPhoneNumberValidity();
            checkEnableButton();
            return true;
        });

        mNeedHelpWith.setOnFocusChangeListener((v, hasFocus) -> {
           if(!hasFocus)
               checkNeedHelpWithValidity();
        });

        mMoreDetails.setOnFocusChangeListener((v, hasFocus) -> {
           if(!hasFocus)
               checkMoreDetailsValidity();
        });
        mPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                checkPhoneNumberValidity();
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_create_help_request;
    }

    @Override
    public void initDependencies() {
        DaggerCreateHelpRequestComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .createHelpRequestModule(new CreateHelpRequestModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    private void checkPhoneNumberValidity()
    {
        String phoneNumber = mPhoneNumber.getText().toString();
        Matcher matcher = pattern.matcher(phoneNumber);

        if(!matcher.matches())
        {
            mPhoneNumber.setError("בדוק שנית את המספר שהוכנס");
            mPhoneNumberIsValid = false;
        }
        else
        {
            mPhoneNumber.setError(null);
            mPhoneNumberIsValid = true;
        }
        checkEnableButton();

    }

    private void checkNeedHelpWithValidity()
    {
        if(mNeedHelpWith.getText().toString().length() < 1) {
            mNeedHelpWith.setError("יש לציין עזרה נחוצה");
            needHelpWithFieldIsValid = false;
        }
        else if(mNeedHelpWith.getText().toString().length() >= 1) {
            mNeedHelpWith.setError(null);
            needHelpWithFieldIsValid = true;
        }

        checkEnableButton();
    }

    private void checkMoreDetailsValidity()
    {
        if(mMoreDetails.getText().toString().length() < 1){
            mMoreDetailsFieldIsValid = false;
            mMoreDetails.setError("יש להכניס פירוט נוסף");
        }
        else if(mMoreDetails.getText().toString().length() >= 1)
        {
            mMoreDetails.setError(null);
            mMoreDetailsFieldIsValid = true;
        }

        checkEnableButton();
    }

    private void checkEnableButton()
    {
        if(needHelpWithFieldIsValid && mMoreDetailsFieldIsValid && mPhoneNumberIsValid)
            mHelpButton.setEnabled(true);
        else
            mHelpButton.setEnabled(false);
    }
}
