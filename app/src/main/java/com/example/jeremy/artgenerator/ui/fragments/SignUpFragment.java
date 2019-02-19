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
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.view_models.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SignUpFragment extends Fragment implements IContract.IView, View.OnClickListener {

    //view model
    private UserViewModel userViewModel;
    //views
    private EditText loginEditText , passwordEditText , emailEditText;
    private Button registerButton , alreadyRegisteredButton;
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
        final View view = inflater.inflate(R.layout.fragment_register , container , false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initListeners();
    }

    @Override
    public void onStop() {
        userViewModel.onClose();
        super.onStop();
    }

    private void initViews(View view) {
        registerButton = view.findViewById(R.id.register_btn);
        alreadyRegisteredButton = view.findViewById(R.id.already_registered);

        final TextView registerTextView = view.findViewById(R.id.register_text_view);
        warningTextView = view.findViewById(R.id.warning_text_view);

        emailEditText = view.findViewById(R.id.user_email_edit_text);
        passwordEditText = view.findViewById(R.id.user_password_edit_text);
        loginEditText = view.findViewById(R.id.user_login_edit_text);
    }

    private void initListeners() {
        registerButton.setOnClickListener(this);
        alreadyRegisteredButton.setOnClickListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {
        switch (animationType) {
            case ANIMATE_INVALID_SIGN_UP_DATA:
                warningTextView.setText(getResources().getString(R.string.emailAndPasswordExist));
                break;
            case ANIMATE_EMPTY_FIELDS:
                warningTextView.setText(getResources().getString(R.string.empty_fields));
                break;
            case ANIMATE_SERVER_NOT_RESPONDING:
                warningTextView.setText("");
                Toast.makeText(getActivity(), R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                userViewModel.onTouchRegisterBtn(new User(loginEditText.getText().toString() , emailEditText.getText().toString() , passwordEditText.getText().toString()));
                break;
            case R.id.already_registered:
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_MOVE_TO_SIGN_IN_FRAGMENT));
                break;
        }
    }
}
