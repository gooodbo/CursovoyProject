package com.example.cursovoyproject.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cursovoyproject.CafedraMembers;
import com.example.cursovoyproject.DBHelper;
import com.example.cursovoyproject.R;
import com.example.cursovoyproject.adapters.CustomAdapterCafMembers;

import java.util.List;

@SuppressLint("ValidFragment")
public class CafedraMembersAddFragment extends Fragment {
    private List<CafedraMembers> list;
    private CustomAdapterCafMembers customAdapterCafMembers;
    private View view;
    private DBHelper dbHelper;


    @SuppressLint("ValidFragment")
    public CafedraMembersAddFragment(List<CafedraMembers> list, CustomAdapterCafMembers customAdapterCafMembers) {
        this.customAdapterCafMembers = customAdapterCafMembers;
        this.list = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cafedra_members_add, container, false);
        final EditText nameET = view.findViewById(R.id.nameET);
        final EditText positionET = view.findViewById(R.id.positionET);

        dbHelper = new DBHelper(view.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        Button cansel = view.findViewById(R.id.cansel);
        cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        Button assept = view.findViewById(R.id.assept);
        assept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CafedraMembers cafedraMembers = new CafedraMembers();
                contentValues.put(DBHelper.KEY_NAME, (nameET.getText().toString()));
                contentValues.put(DBHelper.KEY_POSITION, positionET.getText().toString());
                database.insert(DBHelper.TABLE_MEMBERS, null, contentValues);

                cafedraMembers.setName(nameET.getText().toString());
                cafedraMembers.setPosition(positionET.getText().toString());
                list.add(cafedraMembers);

                customAdapterCafMembers.notifyDataSetChanged();
                getFragmentManager().popBackStack();
                database.close();

            }
        });
        return view;
    }
}
