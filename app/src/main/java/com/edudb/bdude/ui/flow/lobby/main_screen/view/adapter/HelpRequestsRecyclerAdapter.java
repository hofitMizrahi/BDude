package com.edudb.bdude.ui.flow.lobby.main_screen.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.interfaces.IExecutable;

import java.util.List;

public class HelpRequestsRecyclerAdapter extends RecyclerView.Adapter<HelpRequestViewHolder> implements IExecutable<Post> {

    private List<Post> mList;
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

    public void setData(List<Post> posts, InteractionListener listener) {
        mList = posts;
        mListener = listener;
    }

    @Override
    public void execute(Post request) {
        mListener.onItemClicked(request);
    }

    public void setList(List<Post> searchResultItems) {
        mList = searchResultItems;
    }

    public interface InteractionListener{
        void onItemClicked(Post request);
    }
}
