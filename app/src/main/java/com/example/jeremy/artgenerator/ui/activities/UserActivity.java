package com.example.jeremy.artgenerator.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class UserActivity extends AppCompatActivity implements IContract.IView, View.OnClickListener , Handler.Callback {

    private UserViewModel userViewModel;

    private EditText nameEditText , emailEditText , passwordEditText;
    private Button saveButton;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_profile_data_btn:
                final User user = new User(nameEditText.getText().toString() , passwordEditText.getText().toString() , emailEditText.getText().toString());
                userViewModel.onTouchUpdateProfileBtn(user);
                break;
        }
    }

    private void initVars() {
        userViewModel.getUserLiveData(emailEditText.getText().toString() , passwordEditText.getText().toString()).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                nameEditText.setHint(user.getName());
                passwordEditText.setHint(user.getPassword());
                emailEditText.setHint(user.getEmail());
            }
        });
    }

    private void initViews() {
        final TextView nameTextView = (TextView) findViewById(R.id.user_name_text_view);
        final TextView passwordTextView = (TextView) findViewById(R.id.user_password_text_view);
        final TextView emailTextView = (TextView) findViewById(R.id.user_email_text_view);

        nameEditText = findViewById(R.id.user_name_edit_text);
        passwordEditText = findViewById(R.id.user_password_edit_text);
        emailEditText = findViewById(R.id.user_email_edit_text);

        saveButton = (Button) findViewById(R.id.save_profile_data_btn);
        warningTextView = (TextView) findViewById(R.id.warning_text_view);
    }

    private void initListeners() {
        saveButton.setOnClickListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        userViewModel.onClose();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this , SettingsActivity.class));
                break;
            case R.id.exit:
                onPause();
                finish();
                break;
            case R.id.profile:
                startActivity(new Intent(this , UserActivity.class));
                break;
            case R.id.editor:
                startActivity(new Intent(this , MusicEditorActivity.class));
                break;
            case R.id.sample_album:
                startActivity(new Intent(this , SampleListActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ProcessStates.Failed.STATUS_PROFILE_FIELDS_EMPTY:
                warningTextView.setText(getResources().getString(R.string.empty_fields));
                break;
            case ProcessStates.Successful.STATUS_PROFILE_FIELDS_CHANGED:
                Toast.makeText(this, R.string.data_changed_successfully, Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
