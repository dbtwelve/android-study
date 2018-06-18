package com.example.jupiter.myfragment2;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ListFragment fragment1;
    ViewerFragment fragment2;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        fragment1 = (ListFragment) manager.findFragmentById(R.id.listFragment);//new ListFragment();
        fragment2 = (ViewerFragment) manager.findFragmentById(R.id.viewerFragment);//new ViewerFragment();

    }

    public void onImageChange(int index) {
        fragment2.setImage(index);

    }
}
