package com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.general.utils.DialogUtil;
import com.edudb.bdude.general.utils.Utils;
import com.edudb.bdude.interfaces.IExecutable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class MyRequestViewHolder extends RecyclerView.ViewHolder {

    private IExecutable<Integer> mListener;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.body)
    TextView mBody;

    @BindView(R.id.date)
    TextView mDate;

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
    }

    void onBind(HelpRequest post, IExecutable<Integer> listener){
        mListener = listener;
        mTitle.setText(post.getTitle());
        mBody.setText(post.getBody());
        mDate.setText(Utils.getTimeFormat(post.getTimestamp()));
    }
}
