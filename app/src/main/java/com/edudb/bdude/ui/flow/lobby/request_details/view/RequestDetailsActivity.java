package com.edudb.bdude.ui.flow.lobby.request_details.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.FirebaseAnalyticsHelper;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.db.modules.Product;
import com.edudb.bdude.di.components.DaggerRequestDetailsComponent;
import com.edudb.bdude.di.modules.RequestDetailsModule;
import com.edudb.bdude.enums.EnumEmergency;
import com.edudb.bdude.enums.EnumPayBack;
import com.edudb.bdude.general.CountryPrefixPhone;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import com.edudb.bdude.ui.flow.lobby.request_details.presenter.RequestDetailsPresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter.ProductsItemsAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RequestDetailsActivity extends BaseActivity implements RequestDetailsContract.View {

    @Inject
    RequestDetailsPresenter mPresenter;

    @Inject
    Post mRequestDetailsObj;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.body)
    TextView mBodyTxt;

    @BindView(R.id.time)
    TextView mTimeTxt;

    @BindView(R.id.distance)
    TextView mDistance;

    @BindView(R.id.phoneNumber)
    TextView mNumber;

    @BindView(R.id.chips_group)
    ChipGroup requestedItemsGroup;

    @BindView(R.id.phoneNumberWhatsApp)
    TextView mWhatsAppNumber;

    @BindView(R.id.helpBtn)
    Button mHelpBtn;

    @BindView(R.id.phoneContainer)
    View mPhoneContainer;

    @BindView(R.id.status_image)
    ImageView mEmergencyStatusImage;

    @BindView(R.id.status_text)
    TextView mEmergencyStatusText;

    @BindView(R.id.pay_back_text)
    TextView mPayBackText;

    @BindView(R.id.payBackTitle)
    TextView mPayBackTextTitle;

    @BindView(R.id.status_emergency_container)
    View mEmergencyContainer;

    @BindView(R.id.more_details_title)
    TextView mBodyTitle;

    @OnClick(R.id.helpBtn)
    void onBtnClicked() {
        mPhoneContainer.setVisibility(View.VISIBLE);
        mHelpBtn.setVisibility(View.GONE);
    }

    @OnClick(R.id.phoneNumber)
    void openDialClicked() {
        startDial(mRequestDetailsObj.getId(), mRequestDetailsObj.getPhoneNumber());
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_request_details;
    }

    @Override
    public void initDependencies() {
        DaggerRequestDetailsComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .requestDetailsModule(new RequestDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {

        if (mRequestDetailsObj.getPhoneNumber() != null && mRequestDetailsObj.getPhoneNumber().length() < 10) {
            mWhatsAppNumber.setVisibility(View.GONE);
        } else {
            mWhatsAppNumber.setVisibility(View.VISIBLE);
            mWhatsAppNumber.setText(mRequestDetailsObj.getPhoneNumber());
        }
        mNumber.setText(mRequestDetailsObj.getPhoneNumber());

        RecyclerView.LayoutManager HorizontalLayout
                = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);

        if(mRequestDetailsObj.getCategory() != 0 && EnumPayBack.getEnumValueByName(mRequestDetailsObj.getCategory()) != null) {
            mPayBackText.setText(Utils.getStringRefundTitle(Objects.requireNonNull(EnumPayBack.getEnumValueByName(mRequestDetailsObj.getCategory()))));
            mPayBackText.setCompoundDrawablesWithIntrinsicBounds(Utils.getIconRefund(Objects.requireNonNull(EnumPayBack.getEnumValueByName(mRequestDetailsObj.getCategory()))), 0, 0, 0);
        }else {
            mPayBackText.setVisibility(View.GONE);
            mPayBackTextTitle.setVisibility(View.GONE);
        }

        if(mRequestDetailsObj.getStatus() != 0){
            mEmergencyContainer.setVisibility(View.VISIBLE);
            mEmergencyStatusText.setText(Utils.getStringEmergencyTitle(Objects.requireNonNull(EnumEmergency.getEnumValueByName(mRequestDetailsObj.getStatus()))));
            mEmergencyStatusImage.setImageResource(Utils.getIconEmergency(Objects.requireNonNull(EnumEmergency.getEnumValueByName(mRequestDetailsObj.getStatus()))));
        }else {
            mEmergencyContainer.setVisibility(View.GONE);
        }

        for(Product item : mRequestDetailsObj.getProducts()){

            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip_item, null, false);
            chip.setText(item.getProduct());
            chip.setCloseIcon(null);
            requestedItemsGroup.addView(chip);
        }

        mName.setText(mRequestDetailsObj.getUserName());

        mName.setText(mRequestDetailsObj.getUserName());

        if(Utils.isNullOrWhiteSpace(mRequestDetailsObj.getBody())){
            mBodyTxt.setVisibility(View.GONE);
            mBodyTitle.setVisibility(View.GONE);
        }else {
            mBodyTxt.setText(mRequestDetailsObj.getBody());
        }
        mTimeTxt.setText(Utils.getTimeFormat(mRequestDetailsObj.getTimestamp()));
        LatLng latLng = new LatLng(mRequestDetailsObj.getGeoloc().getLat(), mRequestDetailsObj.getGeoloc().getLng());
        String kmStr = Utils.round(LocationHelper.getDistance(latLng), 1) + " " + getString(R.string.km);
        mDistance.setText(kmStr);
    }

    @OnClick(R.id.phoneNumberWhatsApp)
    void openWhatsAppClicked() {
        openWhatsApp();
    }

    private void openWhatsApp() {

        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();

        String contact = CountryPrefixPhone.getPhone(countryCodeValue) + mRequestDetailsObj.getPhoneNumber();
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalyticsHelper.PARAM_PHONE_NUMBER, mRequestDetailsObj.getPhoneNumber());
            bundle.putString(FirebaseAnalyticsHelper.PARAM_POST_ID, mRequestDetailsObj.getId());
            FirebaseAnalyticsHelper.LogEvent(this, FirebaseAnalyticsHelper.CONTACT_WHATSAPP, bundle);

            PackageManager pm = getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
