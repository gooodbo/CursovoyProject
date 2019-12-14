package com.example.cursovoyproject.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cursovoyproject.CafedraMembers;
import com.example.cursovoyproject.DBHelper;
import com.example.cursovoyproject.Fragments.CafedraMembersEdit;
import com.example.cursovoyproject.R;

import java.util.List;

public class CustomAdapterCafMembers extends BaseAdapter implements ListAdapter {
    private Context context;
    private List<CafedraMembers> list;
    private CafedraMembersEdit cafedraMembersEdit;
    private DBHelper dbHelper;

    public CustomAdapterCafMembers(List<CafedraMembers> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cafedra_members_adapter, null);
        }

        TextView nameTV = view.findViewById(R.id.membName);
        TextView positionTV = view.findViewById(R.id.membPosition);

//        dbHelper = new DBHelper(view.getContext());
//        final SQLiteDatabase database = dbHelper.getWritableDatabase();
//        final ContentValues contentValues = new ContentValues();
//        final Cursor cursor = database.query(DBHelper.TABLE_MEMBERS, null, null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
//            int positionIndex = cursor.getColumnIndex(DBHelper.KEY_POSITION);
//            do {
//                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
//                        ", name = " + cursor.getString(nameIndex) +
//                        ", email = " + cursor.getString(positionIndex));
//                nameTV.setText(cursor.getString(nameIndex));
//                //  positionTV.setText(cursor.getString(positionIndex));
//
//            } while (cursor.moveToNext());
//        } else
//            Log.d("mLog", "0 rows");
        nameTV.setText(list.get(position).getName());
        positionTV.setText(list.get(position).getPosition());

        dbHelper = new DBHelper(view.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        final Cursor cursor = database.query(DBHelper.TABLE_MEMBERS, null, null, null, null, null, null);
        final int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        final int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);

        Button removeBtn = view.findViewById(R.id.delete);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToFirst();
                int id = 0;
                boolean flag = true;
                while (flag) {
                    if (list.get(position).getName().equals(cursor.getString(nameIndex))) {
                        id = cursor.getInt(idIndex);
                        flag = false;
                    }
                    cursor.moveToNext();
                }
                list.remove(position);
                database.delete(DBHelper.TABLE_MEMBERS, DBHelper.KEY_ID + "= " + id, null);
                dbHelper.close();
                notifyDataSetChanged();
            }
        });

        Button editBtn = view.findViewById(R.id.edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction;
                FragmentManager manager;
                context = v.getContext();
                cafedraMembersEdit = new CafedraMembersEdit(list, position, CustomAdapterCafMembers.this);
                Activity activity = (AppCompatActivity) context;
                manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                assert manager != null;
                transaction = manager.beginTransaction();
                transaction.replace(R.id.frameMain, cafedraMembersEdit).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
