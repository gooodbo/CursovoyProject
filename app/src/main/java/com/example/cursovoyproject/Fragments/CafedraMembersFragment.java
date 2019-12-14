package com.example.cursovoyproject.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.cursovoyproject.CafedraMembers;
import com.example.cursovoyproject.DBHelper;
import com.example.cursovoyproject.R;
import com.example.cursovoyproject.adapters.CustomAdapterCafMembers;

import java.util.LinkedList;
import java.util.List;

public class CafedraMembersFragment extends Fragment {
    private View view;
    private CafedraMembersAddFragment cafedraMembersAddFragment;
    private List<CafedraMembers> list;
    private CustomAdapterCafMembers customAdapterCafMembers;
    private ListView listView;
    private DBHelper dbHelper;

    public CafedraMembersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cafedra_members_fragment, container, false);

        list = new LinkedList<>();

        dbHelper = new DBHelper(view.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        final Cursor cursor = database.query(DBHelper.TABLE_MEMBERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int positionIndex = cursor.getColumnIndex(DBHelper.KEY_POSITION);
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex) +
                        ", position = " + cursor.getString(positionIndex));
                CafedraMembers cafedraMembers = new CafedraMembers();
                cafedraMembers.setName(cursor.getString(nameIndex));

                cafedraMembers.setPosition(cursor.getString(positionIndex));
                list.add(cafedraMembers);

            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        database.close();

        listView = view.findViewById(R.id.listViewMember);
        customAdapterCafMembers = new CustomAdapterCafMembers(list, getContext());
        listView.setAdapter(customAdapterCafMembers);
        cafedraMembersAddFragment = new CafedraMembersAddFragment(list, customAdapterCafMembers);

        Button addBtn = view.findViewById(R.id.addMember);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frameMain, cafedraMembersAddFragment).addToBackStack(null).commit();
            }
        });
        return view;
    }


}
