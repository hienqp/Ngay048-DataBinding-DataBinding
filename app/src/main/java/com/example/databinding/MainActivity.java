package com.example.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
//        mActivityMainBinding.setMainViewModel(mainViewModel);

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );
        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");

        mActivityMainBinding.setMainViewModel(mainViewModel);

        setContentView(mActivityMainBinding.getRoot());
    }
}