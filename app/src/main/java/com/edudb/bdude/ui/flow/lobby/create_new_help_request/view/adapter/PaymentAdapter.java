package com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edudb.bdude.R;
import com.edudb.bdude.enums.EnumPayBack;
import com.edudb.bdude.interfaces.IExecutable;
import java.util.Arrays;
import java.util.List;
import static com.edudb.bdude.enums.EnumPayBack.BRING_BACK;
import static com.edudb.bdude.enums.EnumPayBack.CONTRIBUTION;
import static com.edudb.bdude.enums.EnumPayBack.MONEY_TRANSFER;

public class PaymentAdapter extends RecyclerView.Adapter<CategoryPaymentHolder> {

    private List<Pair<EnumPayBack, Integer>> paybackCategories = Arrays.asList(
            new Pair<>(CONTRIBUTION, R.drawable.ic_payback_contribution),
            new Pair<>(MONEY_TRANSFER, R.drawable.ic_payback_pay),
            new Pair<>(BRING_BACK, R.drawable.ic_payback_loan)
    );

    public PaymentAdapter() {
        workingPaybackData = paybackCategories;
    }

    private List<Pair<EnumPayBack, Integer>> workingPaybackData;
    private IExecutable<Void> mListener;
    private int selectedPosition = -1;

    public void setListener(IExecutable<Void> listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryPaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false);
        return new CategoryPaymentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPaymentHolder holder, int position) {

        holder.bind(workingPaybackData.get(position), position == selectedPosition);
        holder.root.setOnClickListener(v -> {

            if (selectedPosition == position) {
                selectedPosition = -1;
            } else {
                selectedPosition = position;
            }
            if (mListener != null) {
                //click
                mListener.execute(null);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return workingPaybackData.size();
    }

    public Pair<EnumPayBack, Integer> getSelectedItem() {
        if (selectedPosition < 0) return null;
            return workingPaybackData.get(selectedPosition);
    }
}
