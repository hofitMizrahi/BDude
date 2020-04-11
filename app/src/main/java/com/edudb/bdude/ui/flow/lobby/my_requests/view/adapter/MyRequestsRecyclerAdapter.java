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

public class MyRequestsRecyclerAdapter extends RecyclerView.Adapter implements IExecutable<Integer> {

    private List<HelpRequest> mList;
    private IEventListener mListener;

    public void setDate(List<HelpRequest> list, IEventListener listener){
        mList = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(mList.size() > 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help_my_request, parent, false);
            return new MyRequestViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_data, parent, false);
            return new NoDataViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(mList.size() > 0) {
            ((MyRequestViewHolder)holder).onBind(mList.get(position), this);
        }else {
            ((NoDataViewHolder)holder).onBind("אין בקשות");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() : 1;
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
