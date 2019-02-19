package com.example.jeremy.artgenerator.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.utils.containers.PasswordContainer;
import com.example.jeremy.artgenerator.view_models.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SignInFragment extends Fragment implements IContract.IView, View.OnClickListener {

    //view model
    private UserViewModel userViewModel;
    //views
    private Button loginButton , notRegisteredButton , forgotPasswordButton;
    private EditText userEmailEditText , userPasswordEditText;
    private TextView warningTextView;
    //handler
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.setHandler(handler);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login , container , false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initVars();
        initListeners();
    }

    @Override
    public void onStop() {
        super.onStop();
        userViewModel.onClose();
    }

    private void initVars() {
    }

    private void initViews(View view) {
        final TextView loginTextView = view.findViewById(R.id.login_text_view);

        loginButton = view.findViewById(R.id.login_btn);
        notRegisteredButton = view.findViewById(R.id.not_already_registered);
        userEmailEditText = view.findViewById(R.id.user_email_edit_text);
        userPasswordEditText = view.findViewById(R.id.user_password_edit_text);
        warningTextView = view.findViewById(R.id.warning_text_view);
        forgotPasswordButton = view.findViewById(R.id.forgot_password_btn);
    }

    private void initListeners() {
        loginButton.setOnClickListener(this);
        notRegisteredButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {
        switch (animationType) {
            case ANIMATE_INVALID_SIGN_IN_DATA:
                warningTextView.setText(getResources().getString(R.string.emailAndPasswordNotExist));
                break;
            case ANIMATE_EMPTY_FIELDS:
                warningTextView.setText(getResources().getString(R.string.empty_fields));
                break;
            case ANIMATE_SERVER_NOT_RESPONDING:
                warningTextView.setText("");
                Toast.makeText(getActivity(), R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                break;
            case ANIMATE_RESTORED_DATA:
                userPasswordEditText.setText(PasswordContainer.getPassword());
                userPasswordEditText.setHint(PasswordContainer.getPassword());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                userViewModel.onTouchLoginBtn(userEmailEditText.getText().toString());
                break;
            case R.id.not_already_registered:
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_MOVE_TO_SIGN_UP_FRAGMENT));
                break;
            case R.id.forgot_password_btn:
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_MOVE_TO_FORGOT_PASSWORD_FRAGMENT));
                break;
        }
    }
}

