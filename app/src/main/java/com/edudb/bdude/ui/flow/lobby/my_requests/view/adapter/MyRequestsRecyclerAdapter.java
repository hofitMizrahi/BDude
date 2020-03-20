package com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.interfaces.IExecutable;

import java.util.List;

public class MyRequestsRecyclerAdapter extends RecyclerView.Adapter<MyRequestViewHolder> implements IExecutable<Integer> {

    private List<HelpRequest> mList;
    private IEventListener mListener;

    public void setDate(List<HelpRequest> list, IEventListener listener){
        mList = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help_my_request, parent, false);
        return new MyRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestViewHolder holder, int position) {
        holder.onBind(mList.get(position), this);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void execute(Integer pos) {
        mListener.onItemDeleteClicked(mList.get(pos));
    }

    public void refreshData(List<HelpRequest> newList) {
        mList = newList;
    }

    public interface IEventListener{
        void onItemDeleteClicked(HelpRequest request);
    }
}
