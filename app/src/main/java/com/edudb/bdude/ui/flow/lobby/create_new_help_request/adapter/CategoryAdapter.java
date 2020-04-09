package com.edudb.bdude.ui.flow.lobby.create_new_help_request.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexkorolov on 09/04/2020.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    /* TODO ALEX maybe better to make Enum or move to separate file of categories ? */
    private List<Pair<String, Integer>> emergencyCategories = Arrays.asList(
            new Pair<>("גיל בסיכון", R.drawable.ic_emergency_sikun),
            new Pair<>("בבידוד", R.drawable.ic_emergency_bdude),
            new Pair<>("חולה", R.drawable.ic_emergency_sick)
    );

    private List<Pair<String, Integer>> paybackCategories = Arrays.asList(
            new Pair<>("תרומה", R.drawable.ic_payback_contribution),
            new Pair<>("אני אשלם דרך...", R.drawable.ic_payback_pay),
            new Pair<>("בהשאלה", R.drawable.ic_payback_loan)
    );

    private List<Pair<String, Integer>> workingData = new ArrayList<>();
    private int selectedPosition = -1;

    public void setWorkingData(boolean isEmergencyCategory){
        workingData = isEmergencyCategory ? emergencyCategories : paybackCategories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bind(workingData.get(position), position == selectedPosition);
        holder.root.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return workingData.size();
    }

    public Pair<String, Integer> getSelectdItem(){
        if (selectedPosition < 0) return null;
        return workingData.get(selectedPosition);
    }
}
