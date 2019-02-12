package com.example.jeremy.artgenerator.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class BassEditorFragment extends Fragment implements IContract.IView, View.OnClickListener{

    private MusicEditorViewModel musicEditorViewModel;

    private View view;

    private final int btnId [] = {
            R.id.main_bass_1 , R.id.main_bass_2 , R.id.main_bass_3 , R.id.main_bass_4 , R.id.main_bass_5 ,
            R.id.main_bass_6 , R.id.main_bass_7 , R.id.main_bass_8 , R.id.main_bass_9 , R.id.main_bass_10 ,
            R.id.main_bass_11 , R.id.main_bass_12 , R.id.background_bass_1 , R.id.background_bass_2 ,
            R.id.background_bass_3 , R.id.background_bass_4 , R.id.background_bass_5 , R.id.background_bass_6 ,
            R.id.vocal_effect_1 , R.id.vocal_effect_2};

    private final Button[] buttons = new Button[btnId.length];

    public BassEditorFragment() {}

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicEditorViewModel = ViewModelProviders.of(this).get(MusicEditorViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bass_editor_layout , container , false);
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
        final String[] btnName = getResources().getStringArray(R.array.bass_names);
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

