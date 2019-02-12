package com.example.jeremy.artgenerator.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.constants.SystemModes;
import com.example.jeremy.artgenerator.utils.cache.SystemModeCache;
import com.example.jeremy.artgenerator.view_models.UserViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class RegisterActivity extends AppCompatActivity implements IContract.IView,
        View.OnClickListener , CompoundButton.OnCheckedChangeListener , Handler.Callback {

    private EditText nameEditText , passwordEditText , emailEditText;
    private Button registerButton , toLoginActivityButton;
    private Switch changeModeSwitch;
    private TextView warningTextView;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVars();
        initListeners();
    }

    private void initVars() {
    }

    private void initViews() {
        final TextView userNameTextView = (TextView) findViewById(R.id.user_name_text_view);
        final TextView userEmailTextView = (TextView) findViewById(R.id.user_email_text_view);
        final TextView userPasswordTextView = (TextView) findViewById(R.id.user_password_text_view);

        changeModeSwitch = (Switch) findViewById(R.id.change_mode_switch_btn);
        registerButton = (Button) findViewById(R.id.register_btn);
        toLoginActivityButton = (Button) findViewById(R.id.already_registered);
        emailEditText = (EditText) findViewById(R.id.user_email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.user_password_edit_text);
        nameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        warningTextView = (TextView) findViewById(R.id.warning_text_view);
    }

    private void initListeners() {
        changeModeSwitch.setOnCheckedChangeListener(this);
        toLoginActivityButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.already_registered:
                startActivity(new Intent(this , LoginActivity.class));
                break;
            case R.id.register_btn:
                userViewModel.onTouchRegisterBtn(new User(nameEditText.getText().toString() , passwordEditText.getText().toString() , emailEditText.getText().toString()));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!changeModeSwitch.isChecked()) SystemModeCache.setSystem_mode(SystemModes.OFFLINE_MODE);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == ProcessStates.Failed.STATUS_REGISTER_POST_RESPONSE_FAILED || msg.what == ProcessStates.Failed.STATUS_REGISTER_GET_RESPONSE_FAILED) {
            warningTextView.setText("");
            Toast.makeText(this, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
        }
        switch (msg.what) {
            case ProcessStates.STATUS_CONNECTION_FAILED:
                warningTextView.setTextColor(getResources().getColor(R.color.colorRed));
                warningTextView.setText(getResources().getString(R.string.internet_connection_failed));
                break;
            case ProcessStates.Failed.STATUS_REGISTER_FIELDS_EMPTY:
                warningTextView.setText(getResources().getString(R.string.empty_fields));
                break;
            case ProcessStates.Failed.STATUS_REGISTER_FIELDS_NOT_VALID:
                warningTextView.setText(getResources().getString(R.string.emailAndPasswordExist));
                break;
            case ProcessStates.Successful.STATUS_REGISTER_SUCCESSFULLY:
                startActivity(new Intent(this , MusicEditorActivity.class));
                break;
            case SystemModes.OFFLINE_MODE:
                startActivity(new Intent(this , LauncherActivity.class));
                break;
        }
        return false;
    }
}
