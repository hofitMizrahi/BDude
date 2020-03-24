package com.edudb.bdude.ui.flow.lobby.request_details.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.di.components.DaggerRequestDetailsComponent;
import com.edudb.bdude.di.modules.RequestDetailsModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import com.edudb.bdude.ui.flow.lobby.request_details.presenter.RequestDetailsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RequestDetailsActivity extends BaseActivity implements RequestDetailsContract.View {

    @Inject
    RequestDetailsPresenter mPresenter;

    @Inject
    HelpRequest mRequestDetailsObj;

    @BindView(R.id.title)
    TextView mTitleTxt;

    @BindView(R.id.body)
    TextView mBodyTxt;

    @BindView(R.id.show_phone)
    Button mNumberBtn;

    @BindView(R.id.whatapp_btn)
    Button mWhatsAppBtn;

    @OnClick(R.id.show_phone)
    void onBtnClicked() {
        startDial(mRequestDetailsObj.getPhone_number());
    }

    @OnClick(R.id.whatapp_btn)
    void openWhatsAppClicked() {
        openWhatsApp();
    }

    private void openWhatsApp() {
        String contact = "+972" + mRequestDetailsObj.getPhone_number();
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

        if(mRequestDetailsObj.getPhone_number().length() < 10){
            mWhatsAppBtn.setVisibility(View.GONE);
        }
        mTitleTxt.setText(mRequestDetailsObj.getTitle());
        mBodyTxt.setText(mRequestDetailsObj.getBody());
    }
}
