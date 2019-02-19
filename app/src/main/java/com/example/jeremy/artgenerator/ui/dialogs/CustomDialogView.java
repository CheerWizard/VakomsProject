package com.example.jeremy.artgenerator.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.constants.DialogType;

import androidx.appcompat.app.AlertDialog;

public class CustomDialogView implements IContract.IView, View.OnClickListener {

    private IContract.IViewModel viewModel;

    private Context context;

    private LayoutInflater layoutInflater;

    private AlertDialog alertDialog;

    private Button sureRemoveAllButton , cancelRemoveAllButton;

    private DialogType dialogType;

    public CustomDialogView(Context context , IContract.IViewModel viewModel) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.viewModel = viewModel;
    }

    public void create(DialogType dialogType) {
        this.dialogType = dialogType;
        AlertDialog.Builder builder = null;
        final View alertRemoveAllView = layoutInflater.inflate(R.layout.dialog_alert_remove_all , null);
        initAll(alertRemoveAllView);
        switch (dialogType) {
            case ALERT_REMOVE_ALL_DIALOG:
                builder = new AlertDialog.Builder(context);
                builder.setView(alertRemoveAllView);
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean checkFileName(String fileName) {
        return (fileName != null && !fileName.equals(""));
    }

    private void initAll(View v) {
        initViews(v);
        initVars();
        initListeners();
    }

    private void initVars() {
        //empty
    }

    private void initViews(View v) {
        switch (this.dialogType) {
            case ALERT_REMOVE_ALL_DIALOG:
                //for remove all alert dialog view
                final TextView alertRemoveAllTextView = v.findViewById(R.id.alert_text_view);
                sureRemoveAllButton = v.findViewById(R.id.sure_alert_btn);
                cancelRemoveAllButton = v.findViewById(R.id.cancel_alert_btn);
                break;
        }
    }

    private void initListeners() {
        switch (this.dialogType) {
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
            case R.id.cancel_alert_btn:
                alertDialog.hide();
                break;
            case R.id.sure_alert_btn:
                viewModel.onTouchDeleteAllBtn();
                alertDialog.hide();
                break;
        }
    }
}


