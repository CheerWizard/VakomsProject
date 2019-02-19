package com.example.jeremy.artgenerator.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.view_models.UserViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LauncherActivity extends AppCompatActivity implements IContract.IView,
        View.OnClickListener , CompoundButton.OnCheckedChangeListener , Handler.Callback{
    //view model
    private UserViewModel userViewModel;
    //views
    private Switch changeModeSwitch;
    private Button startButton;
    private TextView countTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVars();
        initListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initVars() {
        final Handler handler = new Handler(this);
        userViewModel.setHandler(handler);
        userViewModel.getUserListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                countTextView.setText(users.size());
            }
        });
    }

    private void initViews() {
        final TextView peopleTextView = findViewById(R.id.people_text_view);
        countTextView = findViewById(R.id.count_text_view);

        changeModeSwitch = findViewById(R.id.change_mode_switch_btn);
        startButton = findViewById(R.id.startBtn);
    }

    private void initListeners() {
        startButton.setOnClickListener(this);
        changeModeSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                startActivity(new Intent(this , MainActivity.class));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (changeModeSwitch.isChecked()) startActivity(new Intent(this , AuthorizationActivity.class));
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ProcessStates.STATUS_CONNECTION_FAILED:
                Toast.makeText(this, R.string.internet_connection_failed, Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }
}
