package com.example.jeremy.artgenerator.ui.dialogs;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.DialogType;
import com.example.jeremy.artgenerator.view_models.MusicEditorViewModel;
import com.example.jeremy.artgenerator.ui.activities.MusicEditorActivity;
import com.example.jeremy.artgenerator.view_models.SampleListViewModel;

import androidx.appcompat.app.AlertDialog;

public class CustomDialogView implements IContract.IView, View.OnClickListener {

    private MusicEditorActivity musicEditorActivity;
    private Activity activity;

    private AlertDialog alertDialog;

    private Button acceptForPlayerPathButton , acceptForRecorderPathButton , sureRemoveAllButton , cancelRemoveAllButton;
    private EditText recordNameForPlayerPathEditText , recordNameForRecorderPathEditText;
    private View recorderPathView , playerPathView , alertRemoveAllView;

    private MusicEditorViewModel musicEditorViewModel;
    private SampleListViewModel sampleListViewModel;

    private DialogType dialogType;

    public CustomDialogView(MusicEditorActivity musicEditorActivity, MusicEditorViewModel musicEditorViewModel) {
        this.musicEditorActivity = musicEditorActivity;
        this.musicEditorViewModel = musicEditorViewModel;
    }

    public CustomDialogView(Activity activity, SampleListViewModel sampleListViewModel) {
        this.activity = activity;
        this.sampleListViewModel = sampleListViewModel;
    }

    public void create(DialogType dialogType) {
        this.dialogType = dialogType;
        AlertDialog.Builder builder = null;
        initAll();
        switch (dialogType) {
            case PLAYER_DIALOG:
                builder = new AlertDialog.Builder(musicEditorActivity);
                builder.setView(playerPathView);
                break;
            case RECORDER_DIALOG:
                builder = new AlertDialog.Builder(musicEditorActivity);
                builder.setView(recorderPathView);
                break;
            case ALERT_REMOVE_ALL_DIALOG:
                builder = new AlertDialog.Builder(activity);
                builder.setView(alertRemoveAllView);
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    private static boolean checkFileName(String fileName) {
        return (fileName != null && !fileName.equals(""));
    }

    private void initAll() {
        initViews();
        initVars();
        initListeners();
    }

    private void initVars() {
        //empty
    }

    private void initViews() {
        switch (this.dialogType) {
            case ALERT_REMOVE_ALL_DIALOG:
                //for remove all alert dialog view
                alertRemoveAllView = activity.getLayoutInflater().inflate(R.layout.dialog_alert_remove_all , null);
                final TextView alertRemoveAllTextView = (TextView) alertRemoveAllView.findViewById(R.id.alert_text_view);
                sureRemoveAllButton = (Button) alertRemoveAllView.findViewById(R.id.sure_alert_btn);
                cancelRemoveAllButton = (Button) alertRemoveAllView.findViewById(R.id.cancel_alert_btn);
                break;
            case RECORDER_DIALOG:
                //for recorder dialog view
                recorderPathView = musicEditorActivity.getLayoutInflater().inflate(R.layout.dialog_record_path , null);
                acceptForRecorderPathButton = (Button) recorderPathView.findViewById(R.id.save_path_btn);
                recordNameForRecorderPathEditText = (EditText) recorderPathView.findViewById(R.id.record_name);
                break;
            case PLAYER_DIALOG:
                //for player dialog view
                playerPathView = musicEditorActivity.getLayoutInflater().inflate(R.layout.dialog_player_path , null);
                acceptForPlayerPathButton = (Button) playerPathView.findViewById(R.id.accept_player_btn);
                recordNameForPlayerPathEditText = (EditText) playerPathView.findViewById(R.id.player_name);
                break;
        }
    }

    private void initListeners() {
        switch (this.dialogType) {
            case PLAYER_DIALOG:
                acceptForPlayerPathButton.setOnClickListener(this);
                break;
            case RECORDER_DIALOG:
                acceptForRecorderPathButton.setOnClickListener(this);
                break;
            case ALERT_REMOVE_ALL_DIALOG:
                cancelRemoveAllButton.setOnClickListener(this);
                sureRemoveAllButton.setOnClickListener(this);
                break;
        }
    }

    @Override
    public void animate(AnimationType animationType) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept_player_btn:
                if (checkFileName(recordNameForPlayerPathEditText.getText().toString())) {
                    try {
                        musicEditorViewModel.onTouchStartPlayerBtn(recordNameForPlayerPathEditText.getText().toString());
                        alertDialog.hide();
                        musicEditorActivity.animate(AnimationType.ANIMATE_PLAYING);
                    } catch (Exception e) {
                        Toast.makeText(musicEditorActivity.getApplicationContext() , R.string.fileNameNotFound , Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(musicEditorActivity.getApplicationContext() , R.string.errorFileNameIsEmpty , Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_path_btn:
                if (checkFileName(recordNameForRecorderPathEditText.getText().toString())) {
                    try {
                        musicEditorViewModel.onTouchStartRecorderBtn(recordNameForRecorderPathEditText.getText().toString());
                        alertDialog.hide();
                        musicEditorActivity.animate(AnimationType.ANIMATE_RECORDING);
                    } catch (Exception e) {
                        Toast.makeText(musicEditorActivity.getApplicationContext(), e.toString() , Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(musicEditorActivity.getApplicationContext() , R.string.errorFileNameIsEmpty , Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel_alert_btn:
                alertDialog.hide();
                break;
            case R.id.sure_alert_btn:
                sampleListViewModel.onTouchDeleteAllBtn();
                alertDialog.hide();
                break;
        }
    }
}


