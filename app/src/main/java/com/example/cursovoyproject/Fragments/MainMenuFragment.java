package com.example.cursovoyproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cursovoyproject.R;

public class MainMenuFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView cafListTextView;
    private CafedraMembersFragment cafedraMembersFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        cafListTextView = view.findViewById(R.id.cafedraList);
        cafListTextView.setOnClickListener(this);
        cafedraMembersFragment = new CafedraMembersFragment();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cafedraList:

                getFragmentManager().beginTransaction().replace(R.id.frameMain, cafedraMembersFragment).addToBackStack(null).commit();
                break;

        }

    }
}
