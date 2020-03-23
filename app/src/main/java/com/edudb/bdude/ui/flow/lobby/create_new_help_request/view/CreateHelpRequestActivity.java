package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerCreateHelpRequestComponent;
import com.edudb.bdude.di.modules.CreateHelpRequestModule;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class CreateHelpRequestActivity extends BaseActivity implements CreateHelpRequestContract.View {

    //final Pattern pattern = Pattern.compile(Constants.PHONE_FULL_REGEX, Pattern.MULTILINE);

    @Inject
    CreateHelpRequestPresenter mPresenter;

    @BindView(R.id.need_help_with_editT)
    EditText mNeedHelpWith;

    @BindView(R.id.my_location_editT)
    EditText mMyLocation;

    @BindView(R.id.more_details_editT)
    EditText mMoreDetails;

    @BindView(R.id.phone_ET)
    EditText mPhoneNumber;

    @BindView(R.id.send_help_request)
    Button mHelpButton;

    @BindView(R.id.man_avatar)
    RadioButton mManAvatar;

    @BindView(R.id.women_avatar)
    RadioButton mWomenAvatar;

    @OnClick({R.id.women_avatar, R.id.man_avatar})
    void onIconClicked() {
        validateBtn();
    }

    @OnTextChanged({R.id.phone_ET, R.id.more_details_editT, R.id.need_help_with_editT,R.id.my_location_editT})
    void onTextChange() {
        validateBtn();
    }

    @OnTextChanged({R.id.phone_ET})
    void onPhoneChange() {
        validateBtn();
    }

    @OnFocusChange({R.id.phone_ET})
    void onPhoneFocusChange() {
        if(!mPhoneNumber.isFocused()){
            checkMobileNumberError();
        }
    }

    @OnClick(R.id.send_help_request)
    void onCreatePostClicked(){
        if(mHelpButton.isEnabled()){
            mPresenter.sendRequest();
        }
    }

    private void validateBtn() {

        boolean retVal = !Utils.isNullOrWhiteSpace(mNeedHelpWith.getText().toString());
        retVal &= !Utils.isNullOrWhiteSpace(mMoreDetails.getText().toString());
        retVal &= !Utils.isNullOrWhiteSpace(mPhoneNumber.getText().toString());
        retVal &= !Utils.isNullOrWhiteSpace(mMyLocation.getText().toString());
        retVal &= mPhoneNumber.getText().toString().matches(Constants.PHONE_FULL_REGEX);
        retVal &= mManAvatar.isChecked() || mWomenAvatar.isChecked();

        setBtnEnabled(retVal);
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

    private void checkMobileNumberError() {
        if(Utils.isNullOrWhiteSpace(mPhoneNumber.getText().toString()) || !mPhoneNumber.getText().toString().matches(Constants.PHONE_FULL_REGEX)) {
            mPhoneNumber.setError("בדוק שנית את המספר שהוכנס");
        }else {
            mPhoneNumber.setError(null);
        }
    }

    private void setBtnEnabled(boolean validate) {
        mHelpButton.setEnabled(validate);
    }

    @Override
    public void initViews() {
    }
}
