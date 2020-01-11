package com.example.cursovoyproject.Fragments.Cafedra;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private String m_Text = "";
    final String pass = "1111";

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
        final EditText emailET = view.findViewById(R.id.emailET);
        final EditText telephoneET = view.findViewById(R.id.telephoneET);
        final EditText audienceET = view.findViewById(R.id.auditET);

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
                            CafedraMembers cafedraMembers = new CafedraMembers();

                            contentValues.put(DBHelper.KEY_NAME, nameET.getText().toString());
                            contentValues.put(DBHelper.KEY_POSITION, positionET.getText().toString());
                            contentValues.put(DBHelper.KEY_EMAIL, emailET.getText().toString());
                            contentValues.put(DBHelper.KEY_TELEPHONE, telephoneET.getText().toString());
                            contentValues.put(DBHelper.KEY_AUDIENCE, audienceET.getText().toString());

                            database.insert(DBHelper.TABLE_MEMBERS, null, contentValues);

                            cafedraMembers.setName(nameET.getText().toString());
                            cafedraMembers.setPosition(positionET.getText().toString());
                            cafedraMembers.setPosition(emailET.getText().toString());
                            cafedraMembers.setPosition(telephoneET.getText().toString());
                            cafedraMembers.setPosition(audienceET.getText().toString());
                            list.add(cafedraMembers);

                            customAdapterCafMembers.notifyDataSetChanged();
                            getFragmentManager().popBackStack();
                            database.close();
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
        });
        return view;
    }
}
