package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpRequestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.body)
    TextView mBody;

    @BindView(R.id.destination)
    TextView mLocation;

    @BindView(R.id.avatar)
    ImageView mAvatar;

    public HelpRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void onBind(HelpRequest helpRequest) {
        mTitle.setText(helpRequest.getTitle());
        mBody.setText(helpRequest.getBody());
        mLocation.setText(helpRequest.getAddress_coords().toString());

        Picasso.get().load(helpRequest.getUser_avatar()).into(mAvatar);
    }
}
