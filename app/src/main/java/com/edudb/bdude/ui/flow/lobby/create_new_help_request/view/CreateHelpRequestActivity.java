package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.components.DaggerCreateHelpRequestComponent;
import com.edudb.bdude.di.modules.CreateHelpRequestModule;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;

import java.util.Arrays;
import java.util.List;

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

    List<String> blackWords = Arrays.asList(
            "anal",
            "anus",
            "arse",
            "ass",
            "ass fuck",
            "ass hole",
            "assfucker",
            "asshole",
            "assshole",
            "bastard",
            "bitch",
            "black cock",
            "bloody hell",
            "boong",
            "cock",
            "cockfucker",
            "cocksuck",
            "cocksucker",
            "coon",
            "coonnass",
            "crap",
            "cunt",
            "cyberfuck",
            "damn",
            "darn",
            "dick",
            "dirty",
            "douche",
            "dummy",
            "erect",
            "erection",
            "erotic",
            "escort",
            "fag",
            "faggot",
            "fuck",
            "Fuck off",
            "fuck you",
            "fuckass",
            "fuckhole",
            "god damn",
            "gook",
            "hard core",
            "hardcore",
            "homoerotic",
            "hore",
            "lesbian",
            "lesbians",
            "mother fucker",
            "motherfuck",
            "motherfucker",
            "negro",
            "nigger",
            "orgasim",
            "orgasm",
            "penis",
            "penisfucker",
            "piss",
            "piss off",
            "porn",
            "porno",
            "pornography",
            "pussy",
            "retard",
            "sadist",
            "sex",
            "sexy",
            "shit",
            "slut",
            "son of a bitch",
            "suck",
            "tits",
            "viagra",
            "whore",
            "xxx",
            "אנאלי",
            "פי הטבעת",
            "התחת",
            "אידיוט",
            "חור תחת",
            "ממזר",
            "בון",
            "זין",
            "קוקסינקר",
            "שטויות",
            "כוס",
            "פאקינג",
            "לעזאזל",
            "מלוכלך",
            "זקוף",
            "זקפה",
            "ארוטי",
            "ליווי",
            "הומו",
            "זיון",
            "תזדיין",
            "לזיין",
            "לעזאזל",
            "הארדקור",
            "הומורוטי",
            "חורה",
            "לסבית",
            "לסביות",
            "כושי",
            "אורגסים",
            "אורגזמה",
            "איבר המין",
            "שתן",
            "להשתין",
            "פורנו",
            "פורנוגרפיה",
            "מפגר",
            "סדיסט",
            "סקס",
            "חרא",
            "שרמוטה",
            "למצוץ",
            "ציצים",
            "ויאגרה",
            "זונה");

    private void validateBtn() {

        boolean retVal = !Utils.isNullOrWhiteSpace(mTitle.getText().toString()) &&
                !Utils.isNullOrWhiteSpace(mMoreDetails.getText().toString()) &&
                !Utils.isNullOrWhiteSpace(mPhoneNumber.getText().toString()) &&
                !Utils.isNullOrWhiteSpace(mMyLocation.getText().toString()) &&
                !Utils.isNullOrWhiteSpace(mNameEt.getText().toString()) &&
                mPhoneNumber.getText().toString().matches(Constants.PHONE_FULL_REGEX) &&
                blackWordsCheck(mTitle.getText().toString()) &&
                blackWordsCheck(mMoreDetails.getText().toString());

        setBtnEnabled(retVal);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_create_help_request;
    }

    @Override
    public void initDependencies() {
        DaggerCreateHelpRequestComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
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
       // mNameEt.setText(mUser.getName());
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

    @Override
    public void showNotAskMoreRequestView() {
        DialogUtil.getSingleButtonInstance(this, (dialog, whith) -> {
                    finish();
                }, getString(R.string.pay_attention_please),
                getString(R.string.you_cont_ask_more_five_requests),
                getString(R.string.approve), false );
    }

    private boolean blackWordsCheck(String text) {
        for (String blackWord : blackWords) {
            if (text.contains(blackWord))
                return false;
        }

        return true;
    }
}
