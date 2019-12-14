package com.example.cursovoyproject.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
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
public class CafedraMembersEdit extends Fragment {
    private List<CafedraMembers> list;
    private int pos;
    private CustomAdapterCafMembers customAdapterCafMembers;
    private View view;
    private DBHelper dbHelper;

    @SuppressLint("ValidFragment")
    public CafedraMembersEdit(List<CafedraMembers> list, int pos, CustomAdapterCafMembers customAdapterCafMembers) {
        this.pos = pos;
        this.customAdapterCafMembers = customAdapterCafMembers;
        this.list = list;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cafedra_members_edit, container, false);
        final EditText nameET = view.findViewById(R.id.nameET);
        final EditText positionET = view.findViewById(R.id.positionET);

        nameET.setText(list.get(pos).getName());
        positionET.setText(list.get(pos).getPosition());

        Button cansel = view.findViewById(R.id.cansel);
        cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        dbHelper = new DBHelper(view.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        final Cursor cursor = database.query(DBHelper.TABLE_MEMBERS, null, null, null, null, null, null);
        final int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        final int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);

        Button assept = view.findViewById(R.id.assept);
        assept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String position = positionET.getText().toString();
                cursor.moveToFirst();
                int id = 0;
                boolean flag = true;
                while (flag) {
                    if (list.get(pos).getName().equals(cursor.getString(nameIndex))) {
                        id = cursor.getInt(idIndex);
                        flag = false;
                    }
                    cursor.moveToNext();
                }

                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_POSITION, position);
                database.update(DBHelper.TABLE_MEMBERS, contentValues, DBHelper.KEY_ID + "= ?", new String[]{String.valueOf(id)});
                CafedraMembers cafedraMembers = new CafedraMembers();
                cafedraMembers.setPosition(position);
                cafedraMembers.setName(name);
                list.set(pos, cafedraMembers);
                customAdapterCafMembers.notifyDataSetChanged();
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
