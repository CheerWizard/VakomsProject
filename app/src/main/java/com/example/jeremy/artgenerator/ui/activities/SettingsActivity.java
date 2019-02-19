package com.example.jeremy.artgenerator.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements IContract.IView , View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initViews() {

    }

    private void initVars() {

    }

    private void initListeners() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void animate(AnimationType animationType) {

    }
}
