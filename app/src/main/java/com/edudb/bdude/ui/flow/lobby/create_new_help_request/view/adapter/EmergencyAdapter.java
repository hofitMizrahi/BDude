package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.enums.EnumEmergency;
import com.edudb.bdude.interfaces.IExecutable;
import java.util.Arrays;
import java.util.List;
import static com.edudb.bdude.enums.EnumEmergency.AGE_AT_RISK;
import static com.edudb.bdude.enums.EnumEmergency.ISOLATE;
import static com.edudb.bdude.enums.EnumEmergency.SICK;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyViewHolder> {

    private List<Pair<EnumEmergency, Integer>> emergencyCategories = Arrays.asList(
            new Pair<>(AGE_AT_RISK, R.drawable.ic_emergency_sikun),
            new Pair<>(ISOLATE, R.drawable.ic_emergency_bdude),
            new Pair<>(SICK, R.drawable.ic_emergency_sick)
    );

    public EmergencyAdapter(){
        workingEmergencyData = emergencyCategories;
    }

    private List<Pair<EnumEmergency, Integer>> workingEmergencyData;
    private IExecutable<Void> mListener;
    private int selectedPosition = -1;

    public void setListener(IExecutable<Void> listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public EmergencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false);
        return new EmergencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyViewHolder holder, int position) {

        holder.bind(workingEmergencyData.get(position), position == selectedPosition);
        holder.root.setOnClickListener(v -> {

            if(selectedPosition == position){
                selectedPosition = -1;
            }else {
                selectedPosition = position;
            }
            if(mListener != null) {
                //click
                mListener.execute(null);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return workingEmergencyData.size();
    }

    public Pair<EnumEmergency, Integer> getSelectedItem(){
        if (selectedPosition < 0) return null;
        return workingEmergencyData.get(selectedPosition);
    }
}
