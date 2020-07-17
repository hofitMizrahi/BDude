package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.adapter.PaymentAdapter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.adapter.EmergencyAdapter;
import com.edudb.bdude.ui.flow.lobby.send_request.view.SendRequestActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CreateHelpRequestActivity extends BaseActivity implements CreateHelpRequestContract.View {

    public static final String POST_OBJ = "post_obj";
    private final int MAX_CHIPS = 20;

    private List<Product> mProductsList;

    @Inject
    CreateHelpRequestPresenter mPresenter;

    @Inject
    User mUser;

    @Inject
    EmergencyAdapter emergencyAdapter;

    @Inject
    PaymentAdapter paybackAdapter;

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
    CheckedEditText mFreeSearch;

    @BindView(R.id.add_chip_item)
    TextView addChipButton;

    @BindView(R.id.chips_group)
    ChipGroup requestedItemsGroup;

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

    @OnClick(R.id.add_chip_item)
    void addChip() {
        if(mProductsList != null && mProductsList.size() <= MAX_CHIPS){
            mFreeSearch.removeError();
            addChipItem();
        }else {
            Toast.makeText(this, R.string.user_cont_add_more_then_20, Toast.LENGTH_LONG).show();
        }
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
                        mPhoneNumber.validate() &&
                        paybackAdapter.getSelectedItem() != null;

        if(!Utils.isNullOrWhiteSpace(mMoreDetails.getText().toString()) && !Utils.blackWordsCheck(mMoreDetails.getText().toString())){
            retVal = false;
        }
        setBtnEnabled(retVal);
    }

    private void addChipView() {
        scrollToTopEditText(needHelpTitle);
        String txt = mFreeSearch.getText().toString();
        if (txt.length() > 0) {
            addChipButton.setEnabled(true);
            counterView.setVisibility(View.VISIBLE);
            chipName.setText(txt);
        } else {
            counterView.setVisibility(View.GONE);
            addChipButton.setEnabled(false);
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
        mFreeSearch.setTextListener(this::onFreeSearchTextChange);
        mNameEt.setTextListener(this::validate);
        mPhoneNumber.setTextListener(this::validate);
        mMyLocation.setClickListener(this::openMap);
        initCategoriesAdapters();
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
        return mMyLocation.getText();
    }

    @Override
    public String getNumber() {
        return mPhoneNumber.getText();
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
        validateBtn();
    }

    @Override
    public void navigateToSendRequestActivity(HelpRequest post) {
        startActivity(new Intent(this, SendRequestActivity.class).putExtra(POST_OBJ, post));
    }

    @Override
    public int getEmergencySelectedStatus() {
        return emergencyAdapter.getSelectedItem() != null? emergencyAdapter.getSelectedItem().first.getValue() : 0;
    }

    @Override
    public int getPaymentSelectedStatus() {
        return paybackAdapter.getSelectedItem() != null ? paybackAdapter.getSelectedItem().first.getValue() : 0;
    }

    private void initCategoriesAdapters() {

        emergencyCategoryRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        payCategoryRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        emergencyCategoryRecycler.setAdapter(emergencyAdapter);
        paybackAdapter.setListener(this::payBackChangeListener);
        payCategoryRecycler.setAdapter(paybackAdapter);
    }

    private void payBackChangeListener(Void aVoid) {
        validateBtn();
    }

    private void addChipItem() {

        String newText  = mFreeSearch.getText();
        boolean itemAlreadyInList = false;

        for(Product item : mProductsList){
            if(item.getProduct().equals(newText)){
                itemAlreadyInList = true;
            }
        }

        if(!itemAlreadyInList) {

            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip_item, null, false);
            chip.setText(newText);

            mProductsList.add(new Product(newText));

            chip.setOnCloseIconClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    requestedItemsGroup.removeView(v);

                    Product removeItem = null;
                    for (Product item : mProductsList) {
                        if (item.getProduct().equals(v.getTag())) {
                            removeItem = item;
                        }
                    }
                    if (removeItem != null) {
                        mProductsList.remove(removeItem);
                        validateBtn();
                    }
                    if(mProductsList.size() == 0){
                        mFreeSearch.showWarning();
                    }
                }
            });
            validateBtn();
            chip.setTag(newText);
            requestedItemsGroup.addView(chip);
        }else {
            Toast.makeText(this, "מוצר זה כבר נמצא ברשימה שלך", Toast.LENGTH_SHORT).show();
        }
        mFreeSearch.setText("");
    }
}
