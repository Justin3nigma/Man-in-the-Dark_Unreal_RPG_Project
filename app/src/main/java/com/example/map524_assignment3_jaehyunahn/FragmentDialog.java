package com.example.map524_assignment3_jaehyunahn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


public class FragmentDialog extends DialogFragment {

    interface DialogClickListener{
        void dialogListnerWithOk(int number);
        void dialogListnerWithCancel();
    }

    public DialogClickListener listener;

    public static final String Tag = "tag";
    private static final String ARG_PARAM1 = "param1";
    private int mParam1;

    public static FragmentDialog newInstance(int param1) {
        FragmentDialog fragment = new FragmentDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);

        String inputNum = getResources().getString(R.string.inputHint);

        TextView textView = v.findViewById(R.id.inputHintText);
        textView.setText(inputNum + mParam1);

        EditText editText = v.findViewById(R.id.inputText_dialog);

        Button okButton = v.findViewById(R.id.okButton_dialog);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()) {
                    int input = Integer.parseInt(editText.getText().toString());
                    listener.dialogListnerWithOk(input);
                    dismiss();
                }
            }
        });

        Button canButton = v.findViewById(R.id.cancelButton_dialog);
        canButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.dialogListnerWithCancel();
                dismiss();
            }
        });
        return v;
    }
}