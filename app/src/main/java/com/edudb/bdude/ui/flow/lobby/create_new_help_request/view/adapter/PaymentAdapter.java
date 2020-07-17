package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edudb.bdude.R;
import com.edudb.bdude.enums.EnumEmergency;
import com.edudb.bdude.enums.EnumPayBack;
import com.edudb.bdude.interfaces.IExecutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.edudb.bdude.enums.EnumEmergency.AGE_AT_RISK;
import static com.edudb.bdude.enums.EnumEmergency.ISOLATE;
import static com.edudb.bdude.enums.EnumEmergency.SICK;
import static com.edudb.bdude.enums.EnumPayBack.BRING_BACK;
import static com.edudb.bdude.enums.EnumPayBack.CONTRIBUTION;
import static com.edudb.bdude.enums.EnumPayBack.MONEY_TRANSFER;
import static com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.adapter.CategoryAdapter.EnumEmergency.AGE_AT_RISK;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private List<Pair<EnumEmergency, Integer>> emergencyCategories = Arrays.asList(
            new Pair<>(AGE_AT_RISK, R.drawable.ic_emergency_sikun),
            new Pair<>(ISOLATE, R.drawable.ic_emergency_bdude),
            new Pair<>(SICK, R.drawable.ic_emergency_sick)
    );

    private List<Pair<EnumPayBack, Integer>> paybackCategories = Arrays.asList(
            new Pair<>(CONTRIBUTION, R.drawable.ic_payback_contribution),
            new Pair<>(MONEY_TRANSFER, R.drawable.ic_payback_pay),
            new Pair<>(BRING_BACK, R.drawable.ic_payback_loan)
    );

    private List<Pair<EnumPayBack, Integer>> workingPaybackData = new ArrayList<>();
    private List<Pair<EnumEmergency, Integer>> workingEmergencyData = new ArrayList<>();
    private IExecutable<Void> mListener;
    private int selectedPosition = -1;

    public void setWorkingData(boolean isEmergencyCategory){

        if(isEmergencyCategory){
            workingEmergencyData = emergencyCategories;
        }else {
            workingPaybackData = paybackCategories;
        }
    }

    public void setListener(IExecutable<Void> listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        Object item;
        if(workingEmergencyData != null){
            item = workingEmergencyData.get(position);
        }else {
            return workingPaybackData.size();
        }

        holder.bind(workingData.get(position), position == selectedPosition);
        holder.root.setOnClickListener(v -> {

            if(selectedPosition == position){
                selectedPosition = -1;
            }else {
                selectedPosition = position;
                if(mListener != null) {
                    //click
                    mListener.execute(null);
                }
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if(workingEmergencyData != null){
            return workingEmergencyData.size();
        }else {
            return workingPaybackData.size();
        }
    }

    public Pair<EnumEmergency, Integer> getSelectedEmergency(){
        if (selectedPosition < 0) return null;
        return workingEmergencyData.get(selectedPosition);
    }

    public Pair<EnumPayBack, Integer> getSelectedPaymentItem(){
        if (selectedPosition < 0) return null;
        return workingPaybackData.get(selectedPosition);
    }
}
