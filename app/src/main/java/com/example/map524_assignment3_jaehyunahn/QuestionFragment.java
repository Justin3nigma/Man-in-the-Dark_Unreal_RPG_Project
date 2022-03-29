package com.example.map524_assignment3_jaehyunahn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class QuestionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    int qText;
    int qColuor;
    boolean qAnswer;

    public QuestionFragment() { }

    public static QuestionFragment newInstance(int q_txt, int q_colour, boolean q_answer) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, q_txt);
        args.putInt(ARG_PARAM2, q_colour);
        args.putBoolean(ARG_PARAM3, q_answer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qText = getArguments().getInt(ARG_PARAM1);
            qColuor = getArguments().getInt(ARG_PARAM2);
            qAnswer = getArguments().getBoolean(ARG_PARAM3);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.question_fragment, container, false);

        TextView textView = v.findViewById(R.id.textView_fragment);
        textView.setText(qText);
        v.setBackgroundColor(getResources().getColor(qColuor));

        return v;

    }

    public int getqText(){return this.qText;}
    public int getqColuor(){return this.qColuor;}
    public boolean getAnswer() {
        return this.qAnswer;
    }
}