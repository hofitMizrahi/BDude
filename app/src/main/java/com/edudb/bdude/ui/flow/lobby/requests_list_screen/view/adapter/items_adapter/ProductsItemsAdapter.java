package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsItemsAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> mList;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    public void setData(List<Product> list){
        if(mList == null) {
            mList = new ArrayList<>();
        }else {
            mList = list;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.onBind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
