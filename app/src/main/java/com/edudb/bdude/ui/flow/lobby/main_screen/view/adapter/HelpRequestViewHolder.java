package com.edudb.bdude.ui.flow.lobby.main_screen.view.adapter;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.ui.flow.lobby.main_screen.view.adapter.items_adapter.ProductsItemsAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class HelpRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<Post> mListener;
    private Post mPost;
    private ProductsItemsAdapter mAdapter;
    private LinearLayoutManager horizontalLayout;

    @BindView(R.id.recyclerViewItems)
    RecyclerView mRecyclerView;

    @BindView(R.id.hours)
    TextView mHours;

    @BindView(R.id.dots)
    TextView mDots;

    @BindView(R.id.distance)
    TextView mDistance;

    @BindView(R.id.name)
    TextView mName;

    HelpRequestViewHolder(@NonNull View itemView, IExecutable<Post> requestIExecutable) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = requestIExecutable;
        mAdapter = new ProductsItemsAdapter();
    }

    @OnClick(R.id.root_item)
    void onItemClicked() {
        mListener.execute(mPost);
    }

    void onBind(Post post) {

        mPost = post;

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

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                checkIfNeedToShowArrow();
            }
        });

        mName.setText(mPost.getUserName());
        LatLng latLng = new LatLng(post.getGeoloc().getLat(), post.getGeoloc().getLng());
        String kmStr = Utils.round(LocationHelper.getDistance(latLng), 1) + " " + itemView.getContext().getString(R.string.km);
        mDistance.setText(kmStr);

        String fullTimeStr = "";

        long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - mPost.getTimestamp());
        long hours = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - mPost.getTimestamp());

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
