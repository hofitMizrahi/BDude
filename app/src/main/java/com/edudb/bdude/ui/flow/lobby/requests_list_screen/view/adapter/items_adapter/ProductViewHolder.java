package com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.db.modules.Product;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView mName;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Product product){
        mName.setText(product.getProduct() + " x " + product.getAmount());
    }
}
