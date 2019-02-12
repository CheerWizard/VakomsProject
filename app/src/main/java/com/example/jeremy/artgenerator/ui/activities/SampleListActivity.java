package com.example.jeremy.artgenerator.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.adapters.IListAdapter;
import com.example.jeremy.artgenerator.adapters.SampleListViewAdapter;
import com.example.jeremy.artgenerator.adapters.SampleRecyclerViewAdapter;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.DialogType;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.constants.RequestConstants;
import com.example.jeremy.artgenerator.constants.SystemModes;
import com.example.jeremy.artgenerator.view_models.SampleListViewModel;
import com.example.jeremy.artgenerator.services.SampleDownloaderService;
import com.example.jeremy.artgenerator.services.SampleUploaderService;
import com.example.jeremy.artgenerator.ui.dialogs.CustomDialogView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class SampleListActivity extends AppCompatActivity implements IContract.IView , Handler.Callback , View.OnClickListener {

    private IListAdapter listAdapter;

    private SampleListViewModel sampleListViewModel;

    private RecyclerView sampleRecyclerView;
    private ListView sampleListView;
    private FloatingActionButton addSampleFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_list);
        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVars();
        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove_all_btn:
                new CustomDialogView(this , sampleListViewModel).create(DialogType.ALERT_REMOVE_ALL_DIALOG);
                break;
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
    protected void onStop() {
        super.onStop();
        sampleListViewModel.onClose();
    }

    private void initVars() {
        if (Build.VERSION.SDK_INT < 24) {
            listAdapter = new SampleListViewAdapter(this, sampleListViewModel.getSampleListLiveData().getValue());
            sampleListView.setAdapter((ListAdapter) listAdapter);
            sampleListViewModel.getSampleListLiveData().observe(this, new Observer<List<Sample>>() {
                @Override
                public void onChanged(List<Sample> samples) {
                    listAdapter.onUpdateList(samples);
                }
            });
        }
        else {
            listAdapter = new SampleRecyclerViewAdapter(this, sampleListViewModel.getSampleListLiveData().getValue());
            sampleRecyclerView.setAdapter((RecyclerView.Adapter) listAdapter);
            sampleListViewModel.getSampleListLiveData().observe(this, new Observer<List<Sample>>() {
                @Override
                public void onChanged(List<Sample> samples) {
                    listAdapter.onUpdateList(samples);
                }
            });
        }
    }

    private void initViews() {
        sampleRecyclerView = findViewById(R.id.sample_recycler_view);
        addSampleFloatingActionButton = findViewById(R.id.floating_add_sample_btn);
        sampleListView = findViewById(R.id.sample_list_view);
    }

    private void initListeners() {
        addSampleFloatingActionButton.setOnClickListener(this);
        listAdapter.addListener(new SampleListViewAdapter.SampleListEventListener() {
            @Override
            public void OnTouchIncrementPriorityBtn(int position) {
                sampleListViewModel.onTouchPlusPriorityBtn(Integer.valueOf(listAdapter.getSamplePriorityTextView().getText().toString()));
            }

            @Override
            public void OnTouchDecrementPriorityBtn(int position) {
                sampleListViewModel.onTouchMinusPriorityBtn(Integer.valueOf(listAdapter.getSamplePriorityTextView().getText().toString()));
            }

            @Override
            public void OnTouchDownloadBtn(int position) {
                listAdapter.getUploadButton().setEnabled(false);
                listAdapter.getUploadButton().setVisibility(View.INVISIBLE);
                startService(new Intent(SampleListActivity.this, SampleDownloaderService.class)
                        .putExtra(getResources().getString(R.string.sample), sampleListViewModel.getSampleLiveData(position).getValue())
                        .putExtra(getResources().getString(R.string.pending_intent), createPendingResult(RequestConstants.REQUEST_DOWNLOAD_SERVICE_CODE, null, 0)));
                listAdapter.getDownloadButton().setEnabled(false);
            }

            @Override
            public void OnTouchUploadBtn(int position) {
                listAdapter.getDownloadButton().setEnabled(false);
                listAdapter.getDownloadButton().setVisibility(View.INVISIBLE);
                startService(new Intent(SampleListActivity.this, SampleUploaderService.class)
                        .putExtra(getResources().getString(R.string.sample), sampleListViewModel.getSampleLiveData(position).getValue())
                        .putExtra(getResources().getString(R.string.pending_intent), createPendingResult(RequestConstants.REQUEST_UPLOAD_SERVICE_CODE, null, 0)));
                listAdapter.getUploadButton().setEnabled(false);
            }

            @Override
            public void OnTouchCancelBtn(int position) {
                if (SampleDownloaderService.getInstance().isRunning())
                    stopService(new Intent(SampleListActivity.this, SampleDownloaderService.class));
                else if (SampleUploaderService.getInstance().isRunning())
                    stopService(new Intent(SampleListActivity.this, SampleUploaderService.class));
                else
                    Toast.makeText(SampleListActivity.this, R.string.nothing_cancel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnTouchSaveBtn(int position) {
                final Sample sample = new Sample(listAdapter.getSampleNameTextView().getText().toString(), Integer.valueOf(listAdapter.getSamplePriorityTextView().getText().toString()));
                sampleListViewModel.onTouchUpdateBtn(sample);
            }

            @Override
            public void OnTouchDeleteBtn(int position) {
                sampleListViewModel.onTouchDeleteBtn(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_add_sample_btn:
                startActivity(new Intent(this , MusicEditorActivity.class));
                break;
        }
    }


    @Override
    public void animate(AnimationType animationType) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ProcessStates.Successful.STATUS_DOWNLOAD_START:
                Toast.makeText(SampleListActivity.this, R.string.download_started, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successful.STATUS_UPLOAD_START:
                Toast.makeText(SampleListActivity.this, R.string.upload_started, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Successful.STATUS_DOWNLOAD_FINISH:
                final Sample sample = data.getParcelableExtra(getResources().getString(R.string.sample));
                if (sample != null) sampleListViewModel.onTouchAddBtn(sample);
                listAdapter.getDownloadButton().setEnabled(true);
                break;
            case ProcessStates.STATUS_CONNECTION_FAILED:
                Toast.makeText(this, R.string.internet_connection_failed, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Failed.STATUS_DOWNLOAD_FAILED:
                Toast.makeText(this, R.string.download_failed, Toast.LENGTH_SHORT).show();
                listAdapter.getDownloadButton().setEnabled(true);
                break;
            case ProcessStates.Successful.STATUS_UPLOAD_FINISH:
                listAdapter.getUploadButton().setEnabled(true);
                break;
            case ProcessStates.Failed.STATUS_UPLOAD_FAILED:
                Toast.makeText(this, R.string.upload_failed, Toast.LENGTH_SHORT).show();
                listAdapter.getUploadButton().setEnabled(true);
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ProcessStates.STATUS_CONNECTION_FAILED:
                Toast.makeText(this, R.string.internet_connection_failed, Toast.LENGTH_SHORT).show();
                break;
            case SystemModes.OFFLINE_MODE:
                listAdapter.getUploadButton().setEnabled(false);
                listAdapter.getUploadButton().setVisibility(View.INVISIBLE);
                listAdapter.getDownloadButton().setEnabled(false);
                listAdapter.getDownloadButton().setVisibility(View.INVISIBLE);
                break;
            case SystemModes.ONLINE_MODE:
                listAdapter.getUploadButton().setEnabled(true);
                listAdapter.getUploadButton().setVisibility(View.VISIBLE);
                listAdapter.getDownloadButton().setEnabled(true);
                listAdapter.getDownloadButton().setVisibility(View.VISIBLE);
                break;
            case ProcessStates.Successful.STATUS_SAMPLE_PRIORITY_CHANGED:
                listAdapter.getSamplePriorityTextView().setText(String.valueOf(msg.arg1));
                break;
            case ProcessStates.Successful.STATUS_SAMPLE_FIELDS_CHANGED:
                Toast.makeText(this, R.string.saved_successfully, Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Failed.STATUS_SAMPLE_PRIORITY_OUT_OF_BOUNDS:
                Toast.makeText(this , R.string.priority_out_of_limits , Toast.LENGTH_SHORT).show();
                break;
            case ProcessStates.Failed.STATUS_SAMPLE_FIELDS_EMPTY:
                Toast.makeText(this, R.string.empty_sample_fields, Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
