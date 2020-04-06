package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.location.LocationHelper;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class HelpRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<Post> mListener;
    private Post mPost;

    @BindView(R.id.recyclerViewItems)
    RecyclerView mRecyclerView;

    @BindView(R.id.hours)
    TextView mHours;

    @BindView(R.id.name)
    TextView mName;

    HelpRequestViewHolder(@NonNull View itemView, IExecutable<Post> requestIExecutable) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = requestIExecutable;
    }

    @OnClick(R.id.root_item)
    void onItemClicked() {
        mListener.execute(mPost);
    }

    void onBind(Post post) {

        mPost = post;
        mName.setText(mPost.getUserName());
//        LatLng latLng = new LatLng(post.getGeoloc().getLat(), post.getGeoloc().getLng());
//        String kmStr = Utils.round(LocationHelper.getDistance(latLng), 1) + " " + itemView.getContext().getString(R.string.km);
//        mLocation.setText(kmStr);

        int hours = (int) ((mPost.getTimestamp() / (1000 * 60 * 60)) % 24);
//        if (hours == 0) {
//            fullTimeStr += itemView.getContext().getString(R.string.recently_published);
//        } else {
//            fullTimeStr += itemView.getContext().getString(R.string.publish_time) + " " + hours + " " + itemView.getContext().getString(R.string.hours_ago);
//        }
        String fullTimeStr = hours + " " + itemView.getContext().getString(R.string.hours_ago);
        mHours.setText(fullTimeStr);
    }
}
