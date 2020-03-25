package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.location.LocationHelper;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.Math.round;

public class HelpRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<Post> mListener;
    private Post mPost;

    @BindView(R.id.body)
    TextView mBody;

    @BindView(R.id.destination)
    TextView mLocation;
    @OnClick(R.id.root_item)
    void onItemClicked(){
        mListener.execute(mPost);
    }

    @BindView(R.id.avatar)
    ImageView mAvatar;

    public HelpRequestViewHolder(@NonNull View itemView, IExecutable<Post> requestIExecutable) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mListener = requestIExecutable;
    }

    public void onBind(Post post) {

        mPost = post;
        mBody.setText(post.getTitle());
        LatLng latLng = new LatLng(post.getGeoloc().getLat(), post.getGeoloc().getLng());
        String kmStr = Utils.round(LocationHelper.getDistance(latLng), 1) + " km";
        mLocation.setText(kmStr);
        Picasso.get().load(post.getUserAvatar()).into(mAvatar);
    }
}
