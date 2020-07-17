package com.edudb.bdude.ui.flow.lobby.send_request.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.Product;
import com.edudb.bdude.di.components.DaggerSendRequestComponent;
import com.edudb.bdude.di.modules.SendRequestModule;
import com.edudb.bdude.enums.EnumEmergency;
import com.edudb.bdude.enums.EnumPayBack;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter.ProductsItemsAdapter;
import com.edudb.bdude.ui.flow.lobby.send_request.contract.SendRequestContract;
import com.edudb.bdude.ui.flow.lobby.send_request.presenter.SendRequestPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.Objects;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class SendRequestActivity extends BaseActivity implements SendRequestContract.View {

    @Inject
    SendRequestPresenter mPresenter;

    @Inject
    HelpRequest mHelpRequest;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.body)
    TextView mBodyTxt;

    @BindView(R.id.distance)
    TextView mDistance;

    @BindView(R.id.refund_details)
    TextView mRefundText;

    @BindView(R.id.phoneNumber)
    TextView mNumber;

    @BindView(R.id.more_details_title)
    TextView mMoreDetailsTitle;

    @BindView(R.id.status_image)
    ImageView mEmergencyStatusImage;

    @BindView(R.id.status_text)
    TextView mEmergencyStatusText;

    @BindView(R.id.status_emergency_container)
    View mEmergencyContainer;

    @BindView(R.id.chips_group)
    ChipGroup requestedItemsGroup;

    @OnClick(R.id.btn_private_area)
    void onBackClicked(){
        finish();
    }

    @OnClick(R.id.sendRequest)
    void onSendRequestClicked(){
        mPresenter.sendRequest();
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

        RecyclerView.LayoutManager HorizontalLayout
                = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);

        for(Product item : mHelpRequest.getProducts()){

            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip_item, null, false);
            chip.setText(item.getProduct());
            chip.setCloseIcon(null);
            requestedItemsGroup.addView(chip);
        }

        mName.setText(mHelpRequest.getUserName());

        if(EnumPayBack.getEnumValueByName(mHelpRequest.getCategory()) != null) {
            mRefundText.setText(Utils.getStringRefundTitle(Objects.requireNonNull(EnumPayBack.getEnumValueByName(mHelpRequest.getCategory()))));
            mRefundText.setCompoundDrawablesWithIntrinsicBounds(Utils.getIconRefund(Objects.requireNonNull(EnumPayBack.getEnumValueByName(mHelpRequest.getCategory()))), 0, 0, 0);
        }

        if(mHelpRequest.getStatus() != 0){
            mEmergencyContainer.setVisibility(View.VISIBLE);
            mEmergencyStatusText.setText(Utils.getStringEmergencyTitle(Objects.requireNonNull(EnumEmergency.getEnumValueByName(mHelpRequest.getStatus()))));
            mEmergencyStatusImage.setImageResource(Utils.getIconEmergency(Objects.requireNonNull(EnumEmergency.getEnumValueByName(mHelpRequest.getStatus()))));
        }else {
            mEmergencyContainer.setVisibility(View.GONE);
        }

        Utils.setViewVisibility(mMoreDetailsTitle, !Utils.isNullOrWhiteSpace(mHelpRequest.getBody()), View.GONE);
        Utils.setViewVisibility(mBodyTxt, !Utils.isNullOrWhiteSpace(mHelpRequest.getBody()), View.GONE);

        mName.setText(mHelpRequest.getUserName());
        mBodyTxt.setText(mHelpRequest.getBody());
        mDistance.setText(mHelpRequest.getAddress_text());
    }
}
