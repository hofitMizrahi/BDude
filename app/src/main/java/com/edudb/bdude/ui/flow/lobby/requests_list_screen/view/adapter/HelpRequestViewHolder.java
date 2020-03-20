package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.interfaces.IExecutable;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<HelpRequest> mListener;
    private HelpRequest mHelpRequest;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.body)
    TextView mBody;

    @BindView(R.id.destination)
    TextView mLocation;
    @OnClick(R.id.root_item)
    void onItemClicked(){
        mListener.execute(mHelpRequest);
    }

    @BindView(R.id.avatar)
    ImageView mAvatar;

    public HelpRequestViewHolder(@NonNull View itemView, IExecutable<HelpRequest> requestIExecutable) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mListener = requestIExecutable;
    }

    public void onBind(HelpRequest helpRequest) {

        mHelpRequest = helpRequest;

        mTitle.setText(helpRequest.getTitle());
        mBody.setText(helpRequest.getBody());
        mLocation.setText(helpRequest.getAddress_coords().toString());

        Picasso.get().load(helpRequest.getUser_avatar()).into(mAvatar);
    }
}
