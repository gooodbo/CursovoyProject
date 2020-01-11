package com.example.cursovoyproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cursovoyproject.Fragments.Cafedra.CafedraMembersFragment;
import com.example.cursovoyproject.Fragments.Practice.PracticeMainFragment;
import com.example.cursovoyproject.R;

public class MainMenuFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView cafListTextView;
    private TextView practice;
    private CafedraMembersFragment cafedraMembersFragment;
    private PracticeMainFragment practiceMainFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        cafListTextView = view.findViewById(R.id.cafedraList);
        practice = view.findViewById(R.id.practise);

        cafListTextView.setOnClickListener(this);
        practice.setOnClickListener(this);
        cafedraMembersFragment = new CafedraMembersFragment();
        practiceMainFragment = new PracticeMainFragment();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cafedraList:

                break;
            case R.id.practise:
                getFragmentManager().beginTransaction().replace(R.id.frameMain, practiceMainFragment).addToBackStack(null).commit();

                break;
        }

    }
}
