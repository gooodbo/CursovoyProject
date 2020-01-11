package com.example.cursovoyproject.adapters;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cursovoyproject.CafedraMembers;
import com.example.cursovoyproject.DBHelper;
import com.example.cursovoyproject.Fragments.Cafedra.CafedraMembersEdit;
import com.example.cursovoyproject.R;

import java.util.List;

public class CustomAdapterCafMembers extends BaseAdapter implements ListAdapter {
    private Context context;
    private List<CafedraMembers> list;
    private CafedraMembersEdit cafedraMembersEdit;
    private DBHelper dbHelper;
    private String m_Text = "";
    private final String pass = "1111";

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
        TextView emailTV = view.findViewById(R.id.membEmail);
        TextView telephoneTV = view.findViewById(R.id.membTelephon);
        TextView audienceTV = view.findViewById(R.id.membAudit);

        nameTV.setText(list.get(position).getName());
        positionTV.setText(list.get(position).getPosition());
        emailTV.setText("Email: "+list.get(position).getEmail());
        telephoneTV.setText("Телефон: "+list.get(position).getTelephone());
        audienceTV.setText("Ауд. "+list.get(position).getAudience());

        dbHelper = new DBHelper(view.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final Cursor cursor = database.query(DBHelper.TABLE_MEMBERS, null, null, null, null, null, null);
        final int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        final int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);

        Button removeBtn = view.findViewById(R.id.delete);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
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

        Button editBtn = view.findViewById(R.id.edit);
        final View finalView = view;
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(finalView.getContext());
                dialog.setContentView(R.layout.custom_dialog_cafedra_members);
                //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                Button bAccept = dialog.findViewById(R.id.button11);
                Button bCansel = dialog.findViewById(R.id.button10);
                final EditText bText = dialog.findViewById(R.id.editText11);
                bAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m_Text = bText.getText().toString();
                        if (m_Text.equals(pass)) {
                            FragmentTransaction transaction;
                            FragmentManager manager;
                            context = v.getContext();
                            cafedraMembersEdit = new CafedraMembersEdit(list, position, CustomAdapterCafMembers.this);

                            AppCompatActivity activity = (AppCompatActivity) finalView.getContext();
                            manager = activity.getSupportFragmentManager();
                            transaction = manager.beginTransaction();
                            transaction.replace(R.id.frameMain, cafedraMembersEdit).addToBackStack(null).commit();

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
