package com.edudb.bdude.ui.flow.lobby.my_requests.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.components.DaggerMyRequestsComponent;
import com.edudb.bdude.di.modules.MyRequestsModule;
import com.edudb.bdude.general.BaseActionBar;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract;
import com.edudb.bdude.ui.flow.lobby.my_requests.presenter.MyRequestsPresenter;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter.MyRequestsRecyclerAdapter;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class MyRequestsActivity extends BaseActivity implements MyRequestsContract.View {

    @Inject
    MyRequestsPresenter mPresenter;

    @Inject
    User mCurrentUser;

    @Inject
    MyRequestsRecyclerAdapter mAdapter;

    @BindView(R.id.my_requests_recycler_view)
    RecyclerView mRecycler;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.phoneNumber)
    TextView mPhoneNumber;

    @BindView(R.id.emailEditText)
    EditText mEmailContentET;

    @OnClick(R.id.btnShare)
    void onShareBtnClicked() {
        EventBus.getDefault().post(new BaseActionBar.ShareMessageEvent());
    }

    @OnClick(R.id.backBtn)
    void onBackBtnClicked() {
        onBackPressed();
    }

    @OnClick(R.id.editAddress)
    void onUpdateLocationClicked() {
        mPresenter.selectLocationClicked();
    }

    @OnClick(R.id.editPhone)
    void onUpdatePhoneClicked() {
        mPresenter.setUserPhoneClicked("052333333");
    }

    @OnClick(R.id.sendToEmail)
    void onSendEmailClicked() {
        if(!Utils.isNullOrWhiteSpace(mEmailContentET.getText().toString())){

            String mailto = "mailto:covid19communityhelp@gmail.com" +
                    "?cc=" + "covid19communityhelp@gmail.com" +
                    "&subject=" + Uri.encode("חוות דעת") +
                    "&body=" + Uri.encode(mEmailContentET.getText().toString());

            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse(mailto));

            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_my_requests;
    }

    @Override
    public void initDependencies() {
        DaggerMyRequestsComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .myRequestsModule(new MyRequestsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void displayList(List<HelpRequest> posts) {
        mRecycler.setVisibility(View.VISIBLE);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter.setDate(posts, mPresenter);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onStartReloadData();
    }

    @Override
    public void initView() {

        if (Utils.isNullOrWhiteSpace(mCurrentUser.getName())) {
            mName.setText("הכנס שם");
            mName.setTextColor(ContextCompat.getColor(this, R.color.gray_light));
        } else {
            mName.setText(mCurrentUser.getName());
        }

        if (Utils.isNullOrWhiteSpace(mCurrentUser.getAddress_text())) {
            mAddress.setText("הכנס כתובת");
            mAddress.setTextColor(ContextCompat.getColor(this, R.color.gray_light));
        } else {
            mAddress.setText(mCurrentUser.getAddress_text());
        }

        if (Utils.isNullOrWhiteSpace(mCurrentUser.getPhone_number())) {
            mPhoneNumber.setText("הכנס מספר טלפון");
            mPhoneNumber.setTextColor(ContextCompat.getColor(this, R.color.gray_light));
        } else {
            mPhoneNumber.setText(mCurrentUser.getPhone_number());
        }
    }

    @Override
    public void refreshList(List<HelpRequest> list) {
        mAdapter.refreshData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCurrentUser(){
        mCurrentUser = SessionManager.getInstance().getUser();
    }
}
