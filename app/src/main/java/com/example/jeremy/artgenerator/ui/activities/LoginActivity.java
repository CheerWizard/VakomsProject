package com.example.jeremy.artgenerator.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.constants.SystemModes;
import com.example.jeremy.artgenerator.utils.cache.SystemModeCache;
import com.example.jeremy.artgenerator.view_models.UserViewModel;

import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity implements IContract.IView,
        CompoundButton.OnCheckedChangeListener , View.OnClickListener , Handler.Callback {

    private UserViewModel userViewModel;

    private Switch changeModeSwitch;
    private Button loginButton , toRegisterActivityButton;
    private EditText userEmailEditText , userPasswordEditText;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    }

    private void initViews() {
        final TextView userEmailTextView = (TextView) findViewById(R.id.user_email_text_view);
        final TextView userPasswordTextView = (TextView) findViewById(R.id.user_password_text_view);

        changeModeSwitch = (Switch) findViewById(R.id.change_mode_switch_btn);
        loginButton = (Button) findViewById(R.id.login_btn);
        userEmailEditText = (EditText) findViewById(R.id.user_email_edit_text);
        userPasswordEditText = (EditText) findViewById(R.id.user_password_edit_text);
        toRegisterActivityButton = (Button) findViewById(R.id.not_already_registered);
        warningTextView = (TextView) findViewById(R.id.warning_text_view);
    }

    private void initListeners() {
        loginButton.setOnClickListener(this);
        toRegisterActivityButton.setOnClickListener(this);
        changeModeSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                userViewModel.getUserLiveData(userEmailEditText.getText().toString() , userPasswordEditText.getText().toString());
                break;
            case R.id.not_already_registered:
                startActivity(new Intent(this , RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!changeModeSwitch.isChecked()) SystemModeCache.setSystem_mode(SystemModes.OFFLINE_MODE);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ProcessStates.STATUS_CONNECTION_FAILED:
                warningTextView.setTextColor(getResources().getColor(R.color.colorRed));
                warningTextView.setText(getResources().getString(R.string.internet_connection_failed));
                break;
            case ProcessStates.Failed.STATUS_LOGIN_FIELDS_EMPTY:
                warningTextView.setText(getResources().getString(R.string.empty_fields));
                break;
            case ProcessStates.Failed.STATUS_LOGIN_FIELDS_NOT_VALID:
                warningTextView.setText(getResources().getString(R.string.emailAndPasswordNotExist));
                break;
            case ProcessStates.Failed.STATUS_LOGIN_GET_RESPONSE_FAILED:
                warningTextView.setText("");
                Toast.makeText(this, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successful.STATUS_LOGIN_SUCCESSFULLY:
                startActivity(new Intent(this , MusicEditorActivity.class));
                break;
            case SystemModes.OFFLINE_MODE:
                startActivity(new Intent(this , LauncherActivity.class));
                break;
        }
        return false;
    }
}
