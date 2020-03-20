package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter.HelpRequestsListPresenter;

import java.util.List;

public class HelpRequestsRecyclerAdapter extends RecyclerView.Adapter<HelpRequestViewHolder> implements IExecutable<HelpRequest> {

    private List<HelpRequest> mList;
    private InteractionListener mListener;

    @NonNull
    @Override
    public HelpRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help_request, parent, false);
        return new HelpRequestViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpRequestViewHolder holder, int position) {
        holder.onBind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<HelpRequest> helpRequests, InteractionListener listener) {
        mList = helpRequests;
        mListener = listener;
    }

    @Override
    public void execute(HelpRequest request) {
        mListener.onItemClicked(request);
    }

    public interface InteractionListener{
        void onItemClicked(HelpRequest request);
    }
}
