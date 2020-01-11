package com.example.cursovoyproject.Fragments.Practice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cursovoyproject.R;

public class PracticeMainFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button productionBtn;
    private Button undergraduateBtn;
    private PracticeProductionFragment practiceProductionFragment;
    private PracticeUndergraduateFragment practiceUndergraduateFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.practice_fragment, container, false);
        practiceProductionFragment = new PracticeProductionFragment();
        practiceUndergraduateFragment = new PracticeUndergraduateFragment();
        productionBtn = view.findViewById(R.id.productuonBtn);
        undergraduateBtn = view.findViewById(R.id.undergraduateBtn);
        productionBtn.setOnClickListener(this);
        undergraduateBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.productuonBtn:
                getFragmentManager().beginTransaction().replace(R.id.frameMain, practiceProductionFragment).addToBackStack(null).commit();
                break;
            case R.id.undergraduateBtn:
                getFragmentManager().beginTransaction().replace(R.id.frameMain, practiceUndergraduateFragment).addToBackStack(null).commit();
                break;

        }
    }
}
