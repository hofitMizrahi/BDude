package com.edudb.bdude.ui.flow.lobby.request_details.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.db.FirebaseAnalyticsHelper;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.di.components.DaggerRequestDetailsComponent;
import com.edudb.bdude.di.modules.RequestDetailsModule;
import com.edudb.bdude.general.CountryPrefixPhone;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import com.edudb.bdude.ui.flow.lobby.request_details.presenter.RequestDetailsPresenter;
import com.google.android.gms.maps.model.LatLng;

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

    @BindView(R.id.helpBtn)
    Button mHelpBtn;

    @BindView(R.id.phoneContainer)
    View mPhoneContainer;

    @OnClick(R.id.helpBtn)
    void onBtnClicked() {
        mPhoneContainer.setVisibility(View.VISIBLE);
        mHelpBtn.setVisibility(View.GONE);
    }

    @OnClick(R.id.phoneNumber)
    void openWhatsAppClicked() {

        //TODO check what to do??
        //startDial(mRequestDetailsObj.getId(), mRequestDetailsObj.getPhoneNumber());
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

    @Override
    public int getLayoutResource() {
        return R.layout.activity_request_post_details;
    }

    @Override
    public void initDependencies() {
        DaggerRequestDetailsComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
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
        mName.setText(mRequestDetailsObj.getUserName());
        mBodyTxt.setText(mRequestDetailsObj.getBody());
        mTimeTxt.setText(Utils.getTimeFormat(mRequestDetailsObj.getTimestamp()));
        LatLng latLng = new LatLng(mRequestDetailsObj.getGeoloc().getLat(), mRequestDetailsObj.getGeoloc().getLng());
        String kmStr = Utils.round(LocationHelper.getDistance(latLng), 1) + " " + getString(R.string.km);
        mDistance.setText(kmStr);
        mNumber.setText(mRequestDetailsObj.getPhoneNumber());
    }
}
