package com.example.jeremy.artgenerator.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.AnimationType;
import com.example.jeremy.artgenerator.view_models.MusicEditorViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class DrumEditorFragment extends Fragment implements IContract.IView, View.OnClickListener{

    private MusicEditorViewModel musicEditorViewModel;

    private View view;

    private final int btnId [] = {
            R.id.kick_1 , R.id.kick_2 ,
            R.id.snare_1 , R.id.snare_2 ,
            R.id.hat ,
            R.id.clap,
            R.id.crash , R.id.mod_crash , R.id.reverse_crash ,
            R.id.percussion_1 , R.id.percussion_2 ,
            R.id.shaker ,
            R.id.fx_1 , R.id.fx_2 , R.id.fx_3 , R.id.fx_4 , R.id.fx_5 , R.id.fx_6 , R.id.fx_7 , R.id.fx_8};

    private final Button [] buttons = new Button[btnId.length];

    public DrumEditorFragment() {}

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicEditorViewModel = ViewModelProviders.of(this).get(MusicEditorViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drum_editor_layout , container , false);
        initViews();
        initListeners();
        return view;
    }

    @Override
    public void onStop() {
        musicEditorViewModel.onClose();
        super.onStop();
    }

    private void initViews() {
        for (int i = 0 ; i < btnId.length ; i++) buttons[i] = (Button) view.findViewById(btnId[i]);
    }

    private void initListeners() {
        for (int i = 0 ; i < btnId.length ; i++) buttons[i].setOnClickListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {
    }

    @Override
    public void onClick(View v) {
        final String[] btnName = getResources().getStringArray(R.array.drum_names);
        for (int i = 0 ; i < btnId.length ; i++) {
            if (v.getId() == btnId[i]) {
                try {
                    musicEditorViewModel.onTouchPadBtn(btnName[i]);
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.errorFileDirectory, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
