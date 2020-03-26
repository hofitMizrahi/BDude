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

public class HelpRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<Post> mListener;
    private Post mPost;

    @BindView(R.id.body)
    TextView mBody;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.hours)
    TextView mHours;

    @BindView(R.id.destination)
    TextView mLocation;

    public HelpRequestViewHolder(@NonNull View itemView, IExecutable<Post> requestIExecutable) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = requestIExecutable;
    }

    @BindView(R.id.avatar)
    ImageView mAvatar;

    @OnClick(R.id.root_item)
    void onItemClicked() {
        mListener.execute(mPost);
    }

    void onBind(Post post) {

        mPost = post;
        mTitle.setText(String.format("%s %s", post.getUserName(), mTitle.getText()));
        mBody.setText(post.getTitle());
        LatLng latLng = new LatLng(post.getGeoloc().getLat(), post.getGeoloc().getLng());
        String kmStr = Utils.round(LocationHelper.getDistance(latLng), 1) + " km";
        mLocation.setText(kmStr);

        int hours = (int) ((mPost.getTimestamp() / (1000 * 60 * 60)) % 24);
        String fullTimeStr = "";

        if (hours == 0) {
            fullTimeStr += "פורסם לאחרונה";
        } else {
            fullTimeStr += "פורסם לפני " + hours + " שעות";
        }
        mHours.setText(fullTimeStr);
        Glide.with(mAvatar.getContext()).load("https://api.adorable.io/avatar/" + post.getId()).circleCrop().into(mAvatar);
    }
}
