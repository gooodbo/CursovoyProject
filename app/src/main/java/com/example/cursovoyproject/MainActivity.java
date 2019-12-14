package com.example.cursovoyproject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.cursovoyproject.Fragments.MainMenuFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        MainMenuFragment homeFragmet = new MainMenuFragment();
        transaction.add(R.id.frameMain, homeFragmet);
        transaction.commit();

    }
}
