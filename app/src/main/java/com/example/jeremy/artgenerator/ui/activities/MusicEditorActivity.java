package com.example.jeremy.artgenerator.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.adapters.MusicEditorViewPageAdapter;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.DialogType;
import com.example.jeremy.artgenerator.constants.PermissionConstants;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.constants.StorageType;
import com.example.jeremy.artgenerator.constants.SystemModes;
import com.example.jeremy.artgenerator.view_models.MusicEditorViewModel;
import com.example.jeremy.artgenerator.ui.dialogs.CustomDialogView;
import com.example.jeremy.artgenerator.ui.fragments.BassEditorFragment;
import com.example.jeremy.artgenerator.ui.fragments.DrumEditorFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class MusicEditorActivity extends AppCompatActivity implements IContract.IView, View.OnClickListener, Handler.Callback {

    private MusicEditorViewModel musicEditorViewModel;

    private TextView systemModeTextView;
    private Button startRecorderButton , stopRecorderButton , stopPlayerButton , startPlayerButton , contextMenuButton;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_editor);
        musicEditorViewModel = ViewModelProviders.of(this).get(MusicEditorViewModel.class);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVars();
        initListeners();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initVars() {

        MusicEditorViewPageAdapter musicEditorViewPageAdapter = new MusicEditorViewPageAdapter(getSupportFragmentManager());
        musicEditorViewPageAdapter.addFragment(new DrumEditorFragment());
        musicEditorViewPageAdapter.addFragment(new BassEditorFragment());

        viewPager.setAdapter(musicEditorViewPageAdapter);
    }

    private void initViews() {
        startRecorderButton = (Button) findViewById(R.id.start_music_recorder);
        startPlayerButton = (Button) findViewById(R.id.start_music_player);
        stopPlayerButton = (Button) findViewById(R.id.stop_music_player);
        stopRecorderButton = (Button) findViewById(R.id.stop_music_recorder);
        viewPager = (ViewPager) findViewById(R.id.music_editor_view_pager);
        systemModeTextView = (TextView) findViewById(R.id.system_mode_text_view);
    }

    private void initListeners() {
       startPlayerButton.setOnClickListener(this);
       startRecorderButton.setOnClickListener(this);
       stopRecorderButton.setOnClickListener(this);
       stopPlayerButton.setOnClickListener(this);
       registerForContextMenu(contextMenuButton);
    }

    @Override
    protected void onStop() {
        super.onStop();
        musicEditorViewModel.onClose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_music_genres , menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dubstep:
                return true;
            case R.id.rock:
                return true;
            case R.id.classical:
                return true;
            case R.id.metallic:
                return true;
            case R.id.hip_hop:
                return true;
            default : return super.onContextItemSelected(item);
        }
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

    private boolean checkPermission(String permission) {
        return (ContextCompat.checkSelfPermission(this , permission) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean isExternalStorageReadable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

    private boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private void requestPermission(String [] permissions , String permission) {
        if (permission == Manifest.permission.READ_EXTERNAL_STORAGE) ActivityCompat.requestPermissions(this , permissions , PermissionConstants.READ_EXTERNAL_STORAGE_PERMISSION_CODE);
        else if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) ActivityCompat.requestPermissions(this , permissions , PermissionConstants.WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
        else if (permission == Manifest.permission.RECORD_AUDIO) ActivityCompat.requestPermissions(this , permissions , PermissionConstants.AUDIO_RECORD_PERMISSION_CODE);
    }
    @Override
    public void animate(AnimationType animationType) {
        switch (animationType) {
            case ANIMATE_PLAYING:
                animatePlaying();
                break;
            case ANIMATE_RECORDING:
                animateRecording();
                break;
            case ANIMATE_UNPLAYING:
                animateUnplaying();
                break;
            case ANIMATE_UNRECORDING:
                animateUnrecording();
                break;
        }
    }

    private void animateRecording() {
        startRecorderButton.setEnabled(false);
        startRecorderButton.setVisibility(View.INVISIBLE);
        stopRecorderButton.setEnabled(true);
        stopRecorderButton.setVisibility(View.VISIBLE);
    }

    private void animateUnrecording() {
        startRecorderButton.setEnabled(true);
        startRecorderButton.setVisibility(View.VISIBLE);
        stopRecorderButton.setEnabled(false);
        stopRecorderButton.setVisibility(View.INVISIBLE);
    }

    private void animatePlaying() {
        startPlayerButton.setEnabled(false);
        startPlayerButton.setVisibility(View.INVISIBLE);
        stopPlayerButton.setEnabled(true);
        stopPlayerButton.setVisibility(View.VISIBLE);
    }

    private void animateUnplaying() {
        startPlayerButton.setEnabled(true);
        startPlayerButton.setVisibility(View.VISIBLE);
        stopPlayerButton.setEnabled(false);
        stopPlayerButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_music_player:
                if (isExternalStorageReadable() && checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) new CustomDialogView(this , musicEditorViewModel).create(DialogType.PLAYER_DIALOG);
                else requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            case R.id.stop_music_recorder:
                animate(AnimationType.ANIMATE_UNRECORDING);
                break;
            case R.id.stop_music_player:
                animate(AnimationType.ANIMATE_UNPLAYING);
                break;
            case R.id.start_music_recorder:
                if (isExternalStorageWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && checkPermission(Manifest.permission.RECORD_AUDIO)) new CustomDialogView(this , musicEditorViewModel).create(DialogType.RECORDER_DIALOG);
                else requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionConstants.READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) new CustomDialogView(this , musicEditorViewModel).create(DialogType.PLAYER_DIALOG);
        }
        else if (requestCode == PermissionConstants.WRITE_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) requestPermission(new String[]{Manifest.permission.RECORD_AUDIO} , Manifest.permission.RECORD_AUDIO);
        }
        else if (requestCode == PermissionConstants.AUDIO_RECORD_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) new CustomDialogView(this , musicEditorViewModel).create(DialogType.RECORDER_DIALOG);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SystemModes.OFFLINE_MODE:
                systemModeTextView.setText(getResources().getString(R.string.you_in_offline_mode));
                systemModeTextView.setTextColor(getResources().getColor(R.color.colorRed));
                break;
            case SystemModes.ONLINE_MODE:
                systemModeTextView.setText(getResources().getString(R.string.you_in_online_mode));
                systemModeTextView.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
            case ProcessStates.Successful.STATUS_PLAYER_START:
                Toast.makeText(this, R.string.player_start, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successful.STATUS_PLAYER_STOP:
                Toast.makeText(this, R.string.player_stop, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successful.STATUS_RECORDER_START:
                Toast.makeText(this, R.string.recorder_start, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successful.STATUS_RECORDER_STOP:
                Toast.makeText(this, R.string.recorder_stop, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successful.STATUS_PLAYER_PAUSE:
                Toast.makeText(this, R.string.player_pause, Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
