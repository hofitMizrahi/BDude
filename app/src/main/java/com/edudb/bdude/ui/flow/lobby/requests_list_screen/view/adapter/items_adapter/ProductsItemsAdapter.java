package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;

public class ProductsItemsAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help_request, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
       // holder.onBind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
