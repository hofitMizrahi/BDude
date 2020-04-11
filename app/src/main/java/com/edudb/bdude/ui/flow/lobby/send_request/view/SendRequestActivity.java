package com.edudb.bdude.ui.flow.lobby.send_request.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.di.components.DaggerSendRequestComponent;
import com.edudb.bdude.di.modules.SendRequestModule;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter.ProductsItemsAdapter;
import com.edudb.bdude.ui.flow.lobby.send_request.contract.SendRequestContract;
import com.edudb.bdude.ui.flow.lobby.send_request.presenter.SendRequestPresenter;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class SendRequestActivity extends BaseActivity implements SendRequestContract.View {

    @Inject
    SendRequestPresenter mPresenter;

    @Inject
    HelpRequest mHelpRequest;

    @Inject
    ProductsItemsAdapter mAdapter;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.body)
    TextView mBodyTxt;

    @BindView(R.id.distance)
    TextView mDistance;

    @BindView(R.id.phoneNumber)
    TextView mNumber;
    @BindView(R.id.more_details_title)
    TextView mMoreDetailsTitle;

    @BindView(R.id.age_at_risk_container)
    View mAgeAtRisk;

    @BindView(R.id.productsRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.isolation_container)
    View mIsolation;

    @OnClick(R.id.btn_private_area)
    void onBackClicked(){
        finish();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_send_request;
    }

    @Override
    public void initDependencies() {
        DaggerSendRequestComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .sendRequestModule(new SendRequestModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {

        mNumber.setText(mHelpRequest.getPhone_number());

        Utils.setViewVisibility(mAgeAtRisk, mHelpRequest.isAgeAtRisk(), View.GONE);
        Utils.setViewVisibility(mIsolation, mHelpRequest.isInIsolation(), View.GONE);

        RecyclerView.LayoutManager HorizontalLayout
                = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);

        //TODO add refund details from pbj

        mRecyclerView.setLayoutManager(HorizontalLayout);
        mAdapter.setData(mHelpRequest.getProducts());
        mRecyclerView.setAdapter(mAdapter);
        mName.setText(mHelpRequest.getUserName());

        Utils.setViewVisibility(mMoreDetailsTitle, !Utils.isNullOrWhiteSpace(mHelpRequest.getBody()), View.GONE);
        Utils.setViewVisibility(mBodyTxt, !Utils.isNullOrWhiteSpace(mHelpRequest.getBody()), View.GONE);

        mName.setText(mHelpRequest.getUserName());
        mBodyTxt.setText(mHelpRequest.getBody());
        mDistance.setText(mHelpRequest.getAddress_text());
    }
}
