package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.Product;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.components.DaggerCreateHelpRequestComponent;
import com.edudb.bdude.di.modules.CreateHelpRequestModule;
import com.edudb.bdude.general.CheckedEditText;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.adapter.CategoryAdapter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;
import com.edudb.bdude.ui.flow.lobby.send_request.view.SendRequestActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_DRAGGING;

public class CreateHelpRequestActivity extends BaseActivity implements CreateHelpRequestContract.View {

    public static final String POST_OBJ = "post_obj";

    private List<Product> mProductsList;

    @Inject
    CreateHelpRequestPresenter mPresenter;

    @Inject
    User mUser;

    @Inject
    CategoryAdapter emergencyAdapter;

    @Inject
    CategoryAdapter paybackAdapter;

    @BindView(R.id.my_location)
    CheckedEditText mMyLocation;

    @BindView(R.id.more_details_editT)
    EditText mMoreDetails;

    @BindView(R.id.phone)
    CheckedEditText mPhoneNumber;

    @BindView(R.id.send_help_request)
    TextView mHelpButton;

    @BindView(R.id.need_help_title)
    TextView needHelpTitle;

    @BindView(R.id.name)
    CheckedEditText mNameEt;

    @BindView(R.id.search_items)
    CheckedEditText EditTextFreeSearch;

    @BindView(R.id.add_chip_item)
    TextView addChipButton;

    @BindView(R.id.chips_group)
    ChipGroup requestedItemsGroup;

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

    @OnClick(R.id.send_help_request)
    void onCreatePostClicked() {
        if (mHelpButton.isEnabled()) {
            mPresenter.sendRequest();
        }
    }

    @OnTextChanged(R.id.more_details_editT)
    void onTextChange() {
       validateBtn();
    }

    @OnClick(R.id.btn_return)
    void returnBackClicked() {
        finish();
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

    @OnClick(R.id.my_location)
    void onSelectLocationClicked() {
        mPresenter.selectLocationClicked();
    }

    private void validateBtn() {

        boolean retVal =
                        !Utils.isNullOrWhiteSpace(mMyLocation.getText()) &&
                        !Utils.isNullOrWhiteSpace(mNameEt.getText()) &&
                        mProductsList.size() > 0 &&
                        mPhoneNumber.validate();

        if(!Utils.isNullOrWhiteSpace(mMoreDetails.getText().toString()) && !Utils.blackWordsCheck(mMoreDetails.getText().toString())){
            retVal = false;
        }
        setBtnEnabled(retVal);
    }

    private void addChipView() {
        scrollToTopEditText(needHelpTitle);
        String txt = EditTextFreeSearch.getText().toString();
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

    private void setBtnEnabled(boolean validate) {
        mHelpButton.setEnabled(validate);
    }

    @Override
    public void initViews() {
        mProductsList = new ArrayList<>();
        EditTextFreeSearch.setTextListener(this::onFreeSearchTextChange);
        mNameEt.setTextListener(this::validate);
        mPhoneNumber.setTextListener(this::validate);
        mMyLocation.setClickListener(this::openMap);
        initCategoriesAdapters();
        //initBottomSheetListener();
    }

    private void openMap(Void v) {
        openMap();
    }

    private void validate(String s) {
        validateBtn();
    }

    private void onFreeSearchTextChange(String s) {
        addChipView();
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
        return mNameEt.getText();
    }

    @Override
    public String getBody() {
        return mMoreDetails.getText().toString();
    }

    @Override
    public void showNotAskMoreRequestView() {
        DialogUtil.getSingleButtonInstance(this, (dialog, whith) -> {
                    finish();
                }, getString(R.string.pay_attention_please),
                getString(R.string.you_cont_ask_more_five_requests),
                getString(R.string.approve), false);
    }

    @Override
    public List<Product> getProducts() {
        return mProductsList;
    }

    @Override
    public void setLocationAddress(String locationName) {
        mMyLocation.setText(locationName);
    }

    @Override
    public void navigateToSendRequestActivity(HelpRequest post) {
        startActivity(new Intent(this, SendRequestActivity.class).putExtra(POST_OBJ, post));
    }

    private void initCategoriesAdapters() {

        emergencyCategoryRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        payCategoryRecycler.setLayoutManager(new GridLayoutManager(this, 3));

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

    private void decreaseItemCounter() {
        int counterNum = Integer.parseInt(counter.getText().toString().replace(getString(R.string.x), ""));
        if (counterNum > 1) {
            counterNum--;
            counter.setText(getString(R.string.x) + counterNum);
        }
    }

    private void addChipItem() {
        Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip_item, null, false);
        int count =  Integer.parseInt(counter.getText().toString().replace(getString(R.string.x), ""));
        chip.setText(String.format("%s %s", EditTextFreeSearch.getText(), count));

        mProductsList.add(new Product(EditTextFreeSearch.getText(), count));

        chip.setOnCloseIconClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //TODO remove chip from ArrayList && validate;

                requestedItemsGroup.removeView(v);
            }
        });

        validateBtn();
        requestedItemsGroup.addView(chip);
        EditTextFreeSearch.setText("");
    }


    /**
     * bottom sheet state change listener
     * we are changing data when sheet changed state
     */
//    private void initBottomSheetListener() {
//        BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
//        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == STATE_DRAGGING) {
//                    invalidateBottomSheetData();
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//            }
//        });
//    }

//    private void invalidateBottomSheetData() {
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//
//        //remove previous chips
//        itemsSelected.removeAllViews();
//
//        // add chips to bottom sheet
//        for (int i = 0; i < requestedItemsGroup.getChildCount(); i++) {
//            Chip chipold = (Chip) requestedItemsGroup.getChildAt(i);
//
//            Chip chip = (Chip) inflater.inflate(R.layout.chip_item, null, false);
//            chip.setText(chipold.getText());
//            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
//
//            itemsSelected.addView(chip);
//        }
//
//        // init emergency & payback categories
//        setBottomSheetCategories();
//
//        // set note to bottom sheet
//        selectedNote.setText(mMoreDetails.getText().toString());
//
//    }
//
//    private void setBottomSheetCategories() {
//
//        LinearLayout emergencySelected = findViewById(R.id.emergency_selected);
//        Pair<String, Integer> emergencyData = emergencyAdapter.getSelectdItem();
//        if (emergencyData != null) {
//            ImageView emergencyImage = ((ImageView) emergencySelected.getChildAt(0));
//            emergencyImage.setImageResource(emergencyData.second);
//            emergencyImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorWhite), PorterDuff.Mode.SRC_IN);
//            ((TextView) emergencySelected.getChildAt(1)).setText(emergencyData.first);
//        }
//
//        LinearLayout paybackSelected = findViewById(R.id.payback_selected);
//        Pair<String, Integer> paybackData = paybackAdapter.getSelectdItem();
//        if (paybackData != null) {
//            ImageView paybackImage = ((ImageView) paybackSelected.getChildAt(0));
//            paybackImage.setImageResource(paybackData.second);
//            paybackImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorWhite), PorterDuff.Mode.SRC_IN);
//            ((TextView) paybackSelected.getChildAt(1)).setText(paybackData.first);
//        }
//    }

}
