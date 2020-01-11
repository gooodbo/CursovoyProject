package com.example.cursovoyproject.Fragments.Cafedra;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private String m_Text = "";
    final String pass = "1111";

    public CafedraMembersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cafedra_members_fragment, container, false);

        list = new LinkedList<>();

        dbHelper = new DBHelper(view.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final Cursor cursor = database.query(DBHelper.TABLE_MEMBERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int positionIndex = cursor.getColumnIndex(DBHelper.KEY_POSITION);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
            int telephoneIndex = cursor.getColumnIndex(DBHelper.KEY_TELEPHONE);
            int audienceIndex = cursor.getColumnIndex(DBHelper.KEY_AUDIENCE);

            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex) +
                        ", position = " + cursor.getString(positionIndex) +
                        ", email = " + cursor.getString(emailIndex) +
                        ", telephone = " + cursor.getString(telephoneIndex) +
                        ", audience = " + cursor.getString(audienceIndex));
                CafedraMembers cafedraMembers = new CafedraMembers();
                cafedraMembers.setName(cursor.getString(nameIndex));
                cafedraMembers.setPosition(cursor.getString(positionIndex));
                cafedraMembers.setEmail(cursor.getString(emailIndex));
                cafedraMembers.setTelephone(cursor.getString(telephoneIndex));
                cafedraMembers.setAudience(cursor.getString(audienceIndex));
                list.add(cafedraMembers);

            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        database.close();
        cursor.close();

        listView = view.findViewById(R.id.listViewMember);
        customAdapterCafMembers = new CustomAdapterCafMembers(list, getContext());
        listView.setAdapter(customAdapterCafMembers);
        cafedraMembersAddFragment = new CafedraMembersAddFragment(list, customAdapterCafMembers);

        Button addBtn = view.findViewById(R.id.addMember);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
        return view;
    }

    private void change() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog_cafedra_members);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button bAccept = dialog.findViewById(R.id.button11);
        Button bCansel = dialog.findViewById(R.id.button10);
        final EditText bText = dialog.findViewById(R.id.editText11);
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Text = bText.getText().toString();
                if (m_Text.equals(pass)) {
                    getFragmentManager().beginTransaction().replace(R.id.frameMain, cafedraMembersAddFragment).addToBackStack(null).commit();
                    dialog.dismiss();
                    return;
                }
                dialog.cancel();
            }
        });

        bCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ((Dialog) dialog).show();
            }
        });

        dialog.show();
    }


}
