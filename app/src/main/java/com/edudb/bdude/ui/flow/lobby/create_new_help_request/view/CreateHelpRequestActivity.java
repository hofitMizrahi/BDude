package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

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
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.adapter.CategoryAdapter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_DRAGGING;

public class CreateHelpRequestActivity extends BaseActivity implements CreateHelpRequestContract.View {

    @Inject
    CreateHelpRequestPresenter mPresenter;

    @Inject
    User mUser;

    @Inject
    CategoryAdapter emergencyAdapter;

    @Inject
    CategoryAdapter paybackAdapter;

    @BindView(R.id.my_location_editT)
    TextView mMyLocation;

    @BindView(R.id.more_details_editT)
    EditText mMoreDetails;

    @BindView(R.id.note)
    TextView selectedNote;

    @BindView(R.id.phone_ET)
    EditText mPhoneNumber;

//  TODO ALEX - NO TITLE ANYMORE ? IF SO NEED TO REMOVE
//    @BindView(R.id.need_help_with_editT)
//    EditText mTitle;

    @BindView(R.id.send_help_request)
    Button mHelpButton;

    @BindView(R.id.need_help_title)
    TextView needHelpTitle;

    @BindView(R.id.name_ET)
    EditText mNameEt;

    @BindView(R.id.search_items)
    EditText chipSearch;

    @BindView(R.id.add_chip_item)
    Button addChipButton;

    @BindView(R.id.chips_group)
    ChipGroup requestedItemsGroup;

    @BindView(R.id.items_selected)
    ChipGroup itemsSelected;

    @BindView(R.id.counter)
    TextView counter;

    @BindView(R.id.chip_name)
    TextView chipName;

    @BindView(R.id.counter_view)
    View counterView;

    @BindView(R.id.emergency_recycler)
    RecyclerView emergencyCategoryRecycler;

    @BindView(R.id.pay_chooser_recycler)
    RecyclerView payCategoryRecycler;

    @OnTextChanged({R.id.phone_ET, R.id.more_details_editT, R.id.my_location_editT, R.id.name_ET})
    void onTextChange() {
        validateBtn();
    }

    @OnTextChanged({R.id.search_items})
    void onStartSearch() {
        addChipView();
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

    @OnClick(R.id.increase_counter)
    void increaseCounter() {
        increaseItemCounter();
    }

    @OnClick(R.id.decrease_counter)
    void decreaseCounter() {
        decreaseItemCounter();
    }

    @OnClick(R.id.add_chip_item)
    void addChip() {
        addChipItem();
    }

    @OnClick(R.id.my_location_editT)
    void onSelectLocationClicked() {
        mPresenter.selectLocationClicked();
    }

    @OnClick(R.id.close)
    void goBack() {
        finish();
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

        boolean retVal =
                !Utils.isNullOrWhiteSpace(mMoreDetails.getText().toString()) &&
                        !Utils.isNullOrWhiteSpace(mPhoneNumber.getText().toString()) &&
                        !Utils.isNullOrWhiteSpace(mMyLocation.getText().toString()) &&
                        !Utils.isNullOrWhiteSpace(mNameEt.getText().toString()) &&
                        mPhoneNumber.getText().toString().matches(Constants.PHONE_FULL_REGEX) &&
                        blackWordsCheck(mMoreDetails.getText().toString());

        setBtnEnabled(retVal);
    }

    private void addChipView() {
        scrollToTopEditText(needHelpTitle);
        String txt = chipSearch.getText().toString();
        if (txt.length() > 0) {
            addChipButton.setEnabled(true);
            counterView.setVisibility(View.VISIBLE);
            chipName.setText(txt);
        } else {
            counterView.setVisibility(View.GONE);
            addChipButton.setEnabled(false);
            counter.setText("x1");
        }
    }

    private void scrollToTopEditText(View view) {
        NestedScrollView scrollView = findViewById(R.id.scroll);
        scrollView.post(() -> scrollView.smoothScrollTo(0, view.getBottom()));
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
        initCategoriesAdapters();
        initBottomSheetListener();
    }

    /**
     * bottom sheet state change listener
     * we are changing data when sheet changed state
     */
    private void initBottomSheetListener() {
        BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == STATE_DRAGGING) {
                    invalidateBottomSheetData();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void invalidateBottomSheetData() {

        LayoutInflater inflater = LayoutInflater.from(this);

        //remove previous chips
        itemsSelected.removeAllViews();

        // add chips to bottom sheet
        for (int i = 0; i < requestedItemsGroup.getChildCount(); i++) {
            Chip chipold = (Chip) requestedItemsGroup.getChildAt(i);

            Chip chip = (Chip) inflater.inflate(R.layout.chip_item, null, false);
            chip.setText(chipold.getText());
            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));

            itemsSelected.addView(chip);
        }

        // init emergency & payback categories
        setBottomSheetCategories();

        // set note to bottom sheet
        selectedNote.setText(mMoreDetails.getText().toString());

    }

    private void setBottomSheetCategories() {

        LinearLayout emergencySelected = findViewById(R.id.emergency_selected);
        Pair<String, Integer> emergencyData = emergencyAdapter.getSelectdItem();
        if (emergencyData != null) {
            ImageView emergencyImage = ((ImageView) emergencySelected.getChildAt(0));
            emergencyImage.setImageResource(emergencyData.second);
            emergencyImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorWhite), PorterDuff.Mode.SRC_IN);
            ((TextView) emergencySelected.getChildAt(1)).setText(emergencyData.first);
        }

        LinearLayout paybackSelected = findViewById(R.id.payback_selected);
        Pair<String, Integer> paybackData = paybackAdapter.getSelectdItem();
        if (paybackData != null) {
            ImageView paybackImage = ((ImageView) paybackSelected.getChildAt(0));
            paybackImage.setImageResource(paybackData.second);
            paybackImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorWhite), PorterDuff.Mode.SRC_IN);
            ((TextView) paybackSelected.getChildAt(1)).setText(paybackData.first);
        }

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

    //  TODO ALEX - NO TITLE ANYMORE ? IF SO NEED TO REMOVE
    @Override
    public String getFullTitle() {
        return "";
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
                getString(R.string.approve), false);
    }

    private boolean blackWordsCheck(String text) {
        for (String blackWord : blackWords) {
            if (text.contains(blackWord))
                return false;
        }

        return true;
    }

    private void initCategoriesAdapters() {
        emergencyAdapter.setWorkingData(true);
        emergencyCategoryRecycler.setAdapter(emergencyAdapter);
        paybackAdapter.setWorkingData(false);
        payCategoryRecycler.setAdapter(paybackAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void increaseItemCounter() {
        int counterNum = Integer.parseInt(counter.getText().toString().replace("x", ""));
        counterNum++;
        counter.setText("x" + counterNum);
    }

    @SuppressLint("SetTextI18n")
    private void decreaseItemCounter() {
        int counterNum = Integer.parseInt(counter.getText().toString().replace("x", ""));
        if (counterNum > 1) {
            counterNum--;
            counter.setText("x" + counterNum);
        }
    }

    private void addChipItem() {
        Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip_item, null, false);
        chip.setText(String.format("%s %s", chipSearch.getText(), ((TextView) findViewById(R.id.counter)).getText()));

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestedItemsGroup.removeView(v);
            }
        });

        requestedItemsGroup.addView(chip);
        chipSearch.setText("");
    }
}
