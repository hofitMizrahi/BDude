package com.edudb.bdude.ui.flow.lobby.request_details.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.di.components.DaggerRequestDetailsComponent;
import com.edudb.bdude.di.modules.RequestDetailsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import com.edudb.bdude.ui.flow.lobby.request_details.presenter.RequestDetailsPresenter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RequestDetailsActivity extends BaseActivity implements RequestDetailsContract.View {

    @Inject
    RequestDetailsPresenter mPresenter;

    @Inject
    Post mRequestDetailsObj;

    @BindView(R.id.title)
    TextView mTitleTxt;

    @BindView(R.id.body)
    TextView mBodyTxt;

    @BindView(R.id.time)
    TextView mTimeTxt;

    @BindView(R.id.show_phone)
    CardView mNumberBtn;

    @BindView(R.id.whatsappCV)
    CardView mWhatsAppBtn;

    @OnClick(R.id.show_phone)
    void onBtnClicked() {
        startDial(mRequestDetailsObj.getPhoneNumber());
    }

    @OnClick(R.id.whatsappCV)
    void openWhatsAppClicked() {
        openWhatsApp();
    }

    private void openWhatsApp() {
        String contact = "+972" + mRequestDetailsObj.getPhoneNumber();
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
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
        return R.layout.activity_request_details;
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

        if(mRequestDetailsObj.getPhoneNumber().length() < 10){
            mWhatsAppBtn.setVisibility(View.GONE);
        }
        mTitleTxt.setText(mRequestDetailsObj.getTitle());
        mBodyTxt.setText(mRequestDetailsObj.getBody());
        mTimeTxt.setText(convertTime(mRequestDetailsObj.getTimestamp()));
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM HH:mm");
        return format.format(date);
    }
}
