package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.adapter;

import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class CategoryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.categoryName)
    TextView categoryName;

    @BindView(R.id.categoryImage)
    ImageView categoryImage;

    @BindView(R.id.category_item_root)
    View root;

    private View itemView;

    CategoryHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    void bind(Pair<String, Integer> dataItem, boolean b) {
        float alpha = b ? 1f : 0.3f;
        itemView.setAlpha(alpha);
        categoryName.setText(dataItem.first);
        categoryImage.setImageResource(dataItem.second);
    }
}
