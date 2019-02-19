package com.example.jeremy.artgenerator.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.ui.fragments.ForgotPasswordFragment;
import com.example.jeremy.artgenerator.ui.fragments.SignInFragment;
import com.example.jeremy.artgenerator.ui.fragments.SignUpFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AuthorizationActivity extends AppCompatActivity implements IContract.IView,
        CompoundButton.OnCheckedChangeListener , Handler.Callback {
    //views
    private Switch changeModeSwitch;
    //fragments
    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;
    private ForgotPasswordFragment forgotPasswordFragment;
    //fragment manager
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
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
        fragmentManager = getSupportFragmentManager();
        signInFragment.setHandler(handler);
        signUpFragment.setHandler(handler);
        forgotPasswordFragment.setHandler(handler);
        //first fragment init
        fragmentManager.beginTransaction().add(R.id.fragment_container , signInFragment).commit();
    }

    private void initViews() {
        changeModeSwitch = findViewById(R.id.change_mode_switch_btn);
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        forgotPasswordFragment = new ForgotPasswordFragment();
    }

    private void initListeners() {
        changeModeSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!changeModeSwitch.isChecked()) startActivity(new Intent(this , LauncherActivity.class));
    }

    @Override
    public boolean handleMessage(Message msg) {
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (msg.what) {
            case ProcessStates.Failed.STATUS_LOGIN_GET_RESPONSE_FAILED:
                signInFragment.animate(AnimationType.ANIMATE_SERVER_NOT_RESPONDING);
                break;
            case ProcessStates.Failed.STATUS_REGISTER_GET_RESPONSE_FAILED:
                signUpFragment.animate(AnimationType.ANIMATE_SERVER_NOT_RESPONDING);
                break;
            case ProcessStates.Failed.STATUS_REGISTER_POST_RESPONSE_FAILED:
                signUpFragment.animate(AnimationType.ANIMATE_SERVER_NOT_RESPONDING);
                break;
            case ProcessStates.STATUS_CONNECTION_FAILED:
                Toast.makeText(this, R.string.internet_connection_failed, Toast.LENGTH_LONG).show();
                break;
            case ProcessStates.Failed.STATUS_REGISTER_FIELDS_EMPTY:
                signUpFragment.animate(AnimationType.ANIMATE_EMPTY_FIELDS);
                break;
            case ProcessStates.Failed.STATUS_LOGIN_FIELDS_EMPTY:
                signInFragment.animate(AnimationType.ANIMATE_EMPTY_FIELDS);
                break;
            case ProcessStates.Failed.STATUS_RESTORE_PASSWORD_EMPTY_FIELD:
                forgotPasswordFragment.animate(AnimationType.ANIMATE_EMPTY_FIELDS);
                break;
            case ProcessStates.Failed.STATUS_RESTORE_PASSWORD_FAILED:
                forgotPasswordFragment.animate(AnimationType.ANIMATE_INVALID_RESTORE_DATA);
                break;
            case ProcessStates.Successful.STATUS_LOGIN_SUCCESSFULLY:
                startActivity(new Intent(this , MainActivity.class));
                break;
            case ProcessStates.Successful.STATUS_REGISTER_SUCCESSFULLY:
                startActivity(new Intent(this , MainActivity.class));
                break;
            case ProcessStates.Successful.STATUS_RESTORE_PASSWORD_SUCCESS:
                if (!signInFragment.isAdded()) fragmentTransaction.replace(R.id.fragment_container , signInFragment).commit();
                signInFragment.animate(AnimationType.ANIMATE_RESTORED_DATA);
                break;
            case ProcessStates.Successful.STATUS_MOVE_TO_SIGN_IN_FRAGMENT:
                if (!signInFragment.isAdded()) fragmentTransaction.replace(R.id.fragment_container , signInFragment).commit();
                break;
            case ProcessStates.Successful.STATUS_MOVE_TO_SIGN_UP_FRAGMENT:
                if (!signUpFragment.isAdded()) fragmentTransaction.replace(R.id.fragment_container , signUpFragment).commit();
                break;
            case ProcessStates.Successful.STATUS_MOVE_TO_FORGOT_PASSWORD_FRAGMENT:
                if (!forgotPasswordFragment.isAdded()) fragmentTransaction.replace(R.id.fragment_container , forgotPasswordFragment).commit();
                break;
        }
        return false;
    }
}
