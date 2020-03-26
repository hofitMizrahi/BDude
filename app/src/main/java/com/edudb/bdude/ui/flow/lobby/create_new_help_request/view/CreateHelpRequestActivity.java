package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.components.DaggerCreateHelpRequestComponent;
import com.edudb.bdude.di.modules.CreateHelpRequestModule;
import com.edudb.bdude.enums.EnumGender;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;
import com.google.firebase.firestore.GeoPoint;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class CreateHelpRequestActivity extends BaseActivity implements CreateHelpRequestContract.View {

    @Inject
    CreateHelpRequestPresenter mPresenter;

    @Inject
    User mUser;

    @BindView(R.id.my_location_editT)
    TextView mMyLocation;

    @BindView(R.id.more_details_editT)
    EditText mMoreDetails;

    @BindView(R.id.phone_ET)
    EditText mPhoneNumber;

    @BindView(R.id.need_help_with_editT)
    EditText mTitle;

    @BindView(R.id.send_help_request)
    Button mHelpButton;

    @BindView(R.id.name_ET)
    EditText mNameEt;

    @OnTextChanged({R.id.phone_ET, R.id.more_details_editT, R.id.need_help_with_editT, R.id.my_location_editT, R.id.name_ET})
    void onTextChange() {
        validateBtn();
    }

    @OnTextChanged({R.id.phone_ET})
    void onPhoneChange() {
        validateBtn();
    }

    @OnFocusChange({R.id.phone_ET})
    void onPhoneFocusChange() {
        if (!mPhoneNumber.isFocused()) {
            checkMobileNumberError();
        }
    }

    @OnClick(R.id.send_help_request)
    void onCreatePostClicked() {
        if (mHelpButton.isEnabled()) {
            mPresenter.sendRequest();
        }
    }

    @OnClick(R.id.my_location_editT)
    void onSelectLocationClicked() {
        mPresenter.selectLocationClicked();
    }

    private void validateBtn() {

        boolean retVal = !Utils.isNullOrWhiteSpace(mTitle.getText().toString());
        retVal &= !Utils.isNullOrWhiteSpace(mMoreDetails.getText().toString());
        retVal &= !Utils.isNullOrWhiteSpace(mPhoneNumber.getText().toString());
        retVal &= !Utils.isNullOrWhiteSpace(mMyLocation.getText().toString());
        retVal &= !Utils.isNullOrWhiteSpace(mNameEt.getText().toString());
        retVal &= mPhoneNumber.getText().toString().matches(Constants.PHONE_FULL_REGEX);

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
        if (Utils.isNullOrWhiteSpace(mPhoneNumber.getText().toString()) || !mPhoneNumber.getText().toString().matches(Constants.PHONE_FULL_REGEX)) {
            mPhoneNumber.setError(getString(R.string.check_the_number));
        } else {
            mPhoneNumber.setError(null);
        }
    }

    private void setBtnEnabled(boolean validate) {
        mHelpButton.setEnabled(validate);
    }

    @Override
    public void initViews() {
        mNameEt.setText(mUser.getName());
    }

    @Override
    public String getLocationName() {
        return mMyLocation.getText().toString();
    }

    @Override
    public String getNumber() {
        return mPhoneNumber.getText().toString();
    }

    @Override
    public String getName() {
        return mNameEt.getText().toString();
    }

    @Override
    public String getBody() {
        return mMoreDetails.getText().toString();
    }

    @Override
    public String getFullTitle() {
        return mTitle.getText().toString();
    }

    @Override
    public void changeLocationText(String locationName) {
        mMyLocation.setText(locationName);
    }

}
