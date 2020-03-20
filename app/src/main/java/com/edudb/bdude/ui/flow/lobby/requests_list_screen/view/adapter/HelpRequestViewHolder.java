package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpRequestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView mName;

    public HelpRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void onBind(HelpRequest helpRequest) {
        mName.setText(helpRequest.getUser_name());
    }
}
