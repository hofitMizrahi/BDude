package com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.enums.EnumEmergency;
import com.edudb.bdude.enums.EnumPayBack;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.ui.flow.lobby.main_screen.view.adapter.items_adapter.ProductsItemsAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class MyRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<Integer> mListener;
    private ProductsItemsAdapter mAdapter;
    private LinearLayoutManager horizontalLayout;
    private HelpRequest mPost;

    @BindView(R.id.recyclerViewItems)
    RecyclerView mRecyclerView;

    @BindView(R.id.hours)
    TextView mHours;

    @BindView(R.id.distance)
    TextView mDistance;

    @BindView(R.id.dots)
    TextView mDots;

    @BindView(R.id.body_text)
    TextView mBody;

    @BindView(R.id.refund)
    TextView mRefund;

    @BindView(R.id.position)
    TextView mStatus;

    @BindView(R.id.divider2)
    View mBodyDivider;

    @BindView(R.id.name)
    TextView mName;
    @OnClick(R.id.delete)
    void onItemDeleteClicked(){

        DialogUtil.getSingleButtonInstance(itemView.getContext(), (dialog, whith) -> {
            mListener.execute(getAdapterPosition());
        }, itemView.getContext().getString(R.string.pay_attention_please),
                itemView.getContext().getString(R.string.delete_item_text),
                itemView.getContext().getString(R.string.delete), true );
    }

    MyRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mAdapter = new ProductsItemsAdapter();
    }

    void onBind(HelpRequest post, IExecutable<Integer> listener){
        mPost = post;
        mListener = listener;

        horizontalLayout
                = new LinearLayoutManager(
                itemView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(horizontalLayout);

        mAdapter.setData(post.getProducts());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(0);
        checkIfNeedToShowArrow();

        if(Utils.isNullOrWhiteSpace(post.getBody())){
            mBody.setVisibility(View.GONE);
            mBodyDivider.setVisibility(View.GONE);
        }else {
            mBody.setText(post.getBody());
        }

        if(post.getCategory() != 0 && EnumPayBack.getEnumValueByName(post.getCategory()) != null) {
            mRefund.setText(Utils.getStringRefundTitle(Objects.requireNonNull(EnumPayBack.getEnumValueByName(post.getCategory()))));
            mRefund.setCompoundDrawablesWithIntrinsicBounds(Utils.getIconRefund(Objects.requireNonNull(EnumPayBack.getEnumValueByName(post.getCategory()))), 0, 0, 0);
        }else {
            mRefund.setVisibility(View.GONE);
        }

        if(post.getStatus() != 0){
            mStatus.setVisibility(View.VISIBLE);
            mStatus.setText(Utils.getStringEmergencyTitle(Objects.requireNonNull(EnumEmergency.getEnumValueByName(post.getStatus()))));
            mStatus.setCompoundDrawablesWithIntrinsicBounds(Utils.getIconEmergency(Objects.requireNonNull(EnumEmergency.getEnumValueByName(post.getStatus()))), 0, 0, 0);
        }else {
            mStatus.setVisibility(View.GONE);
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                checkIfNeedToShowArrow();
            }
        });

        mName.setText(post.getUserName());
        LatLng latLng = new LatLng(post.getAddress_coords().getLatitude(), post.getAddress_coords().getLongitude());
        String kmStr = Utils.round(LocationHelper.getDistance(latLng), 1) + " " + itemView.getContext().getString(R.string.km);
        mDistance.setText(kmStr);

        String fullTimeStr = "";

        long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - post.getTimestamp());
        long hours = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - post.getTimestamp());

        if (hours == 0) {
            fullTimeStr += itemView.getContext().getString(R.string.recently_published);
        } else if (days == 0){
            fullTimeStr += itemView.getContext().getString(R.string.publish_time) + " " + (hours % 24) + " " + itemView.getContext().getString(R.string.hours_ago);
        }else {
            fullTimeStr += itemView.getContext().getString(R.string.publish_time) + " " + days + " " + itemView.getContext().getString(R.string.days_ago);
        }
        mHours.setText(fullTimeStr);
    }

    private void checkIfNeedToShowArrow(){

        new Handler().postDelayed(() -> {
            int lastVisibleFilter = horizontalLayout.findLastCompletelyVisibleItemPosition();
            boolean shouldShowArrows = !mPost.getProducts().isEmpty() && lastVisibleFilter > -1 && lastVisibleFilter < mPost.getProducts().size() - 1;
            Utils.setViewVisibility(mDots, shouldShowArrows, View.GONE);
            mRecyclerView.postInvalidate();
        }, 100);
    }
}
