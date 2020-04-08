package com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NoDataViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.messageText)
    TextView mTextNoData;

    public NoDataViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(String str){

        mTextNoData.setText(str);
    }
}
