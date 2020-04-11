package com.edudb.bdude.ui.flow.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.edudb.bdude.R;
import com.edudb.bdude.general.Constants;
import com.edudb.bdude.interfaces.IExecutable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UpdatePhoneDialogFragment extends DialogFragment {

    @BindView(R.id.nameET)
    EditText mPhone;

    @BindView(R.id.save_data)
    TextView mBtn;

    @OnClick(R.id.save_data)
    void onSaveClicked(){
        if(mListener != null){
            getDialog().dismiss();
            mListener.execute(mPhone.getText().toString());
        }

    }

    @OnTextChanged(R.id.nameET)
    void onTextChange(){
        validateBtn();
    }

    private void validateBtn() {
        mBtn.setEnabled(mPhone.getText().toString().matches(Constants.PHONE_FULL_REGEX));
    }

    private IExecutable<String> mListener;

    public static UpdatePhoneDialogFragment newInstance(IExecutable<String> listener) {
        UpdatePhoneDialogFragment fragment = new UpdatePhoneDialogFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_update_phone_number, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void showDialog(FragmentManager fm, String tag, IExecutable<String> listener) {
        if (fm != null) {
            UpdatePhoneDialogFragment.newInstance(listener).show(fm, tag);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
