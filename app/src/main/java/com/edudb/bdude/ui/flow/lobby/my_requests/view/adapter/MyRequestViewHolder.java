package com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.Product;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter.ProductsItemsAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class MyRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<Integer> mListener;
    private ProductsItemsAdapter mAdapter;

    @BindView(R.id.recyclerViewItems)
    RecyclerView mRecyclerView;

    @BindView(R.id.hours)
    TextView mHours;

    @BindView(R.id.distance)
    TextView mDistance;

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

        mListener = listener;

        RecyclerView.LayoutManager HorizontalLayout
                = new LinearLayoutManager(
                itemView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(HorizontalLayout);

        mAdapter.setData(post.getProducts());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(0);

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
}
