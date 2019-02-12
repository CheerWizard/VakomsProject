package com.example.jeremy.artgenerator.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.Preferences;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements IContract.IView , View.OnClickListener {

    private EditText priorityEditText , rateEditText , loopEditText , leftVolumeEditText , rightVolumeEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
        final TextView priorityTextView = findViewById(R.id.priority_settings_text_view);
        final TextView leftVolumeTextView = findViewById(R.id.left_volume_settings_text_view);
        final TextView rightVolumeTextView = findViewById(R.id.right_volume_settings_text_view);
        final TextView rateTextView = findViewById(R.id.rate_settings_text_view);
        final TextView loopTextView = findViewById(R.id.loop_settings_text_view);

        leftVolumeEditText = findViewById(R.id.left_volume_settings_edit_text);
        rightVolumeEditText = findViewById(R.id.right_volume_settings_edit_text);
        loopEditText = findViewById(R.id.loop_settings_edit_text);
        rateEditText = findViewById(R.id.rate_settings_edit_text);
        priorityEditText = findViewById(R.id.priority_settings_edit_text);

        saveButton = findViewById(R.id.save_settings_btn);
    }

    private void initListeners() {
        saveButton.setOnClickListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_settings_btn:
                if (!emptyFields()) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Preferences.SOUND_POOL_SETTINGS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Preferences.SOUND_POOL_PRIORITY, Integer.valueOf(priorityEditText.getText().toString()));
                    editor.putInt(Preferences.SOUND_POOL_LEFT_VOLUME, Integer.valueOf(leftVolumeEditText.getText().toString()));
                    editor.putInt(Preferences.SOUND_POOL_RIGHT_VOLUME, Integer.valueOf(rightVolumeEditText.getText().toString()));
                    editor.putInt(Preferences.SOUND_POOL_RATE, Integer.valueOf(rateEditText.getText().toString()));
                    editor.putInt(Preferences.SOUND_POOL_LOOP, Integer.valueOf(loopEditText.getText().toString()));
                    editor.apply();
                }
                else Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean emptyFields() {
        return ((priorityEditText.getText().toString() == "") ||
                (leftVolumeEditText.getText().toString() == "") ||
                (rightVolumeEditText.getText().toString() == "") ||
                (rateEditText.getText().toString() == "") ||
                (loopEditText.getText().toString() == ""));
    }
}
