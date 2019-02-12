package com.example.jeremy.artgenerator.view_models;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.constants.StorageType;
import com.example.jeremy.artgenerator.logic.MusicPlayerLogic;
import com.example.jeremy.artgenerator.logic.MusicRecorderLogic;

import androidx.lifecycle.ViewModel;

public class MusicEditorViewModel extends ViewModel implements IContract.IViewModel {
    //helpful logic
    private MusicPlayerLogic musicPlayerLogic;
    private MusicRecorderLogic musicRecorderLogic;

    public MusicEditorViewModel(MusicPlayerLogic musicPlayerLogic, MusicRecorderLogic musicRecorderLogic) {
        this.musicPlayerLogic = musicPlayerLogic;
        this.musicRecorderLogic = musicRecorderLogic;
    }

    public void onTouchStartRecorderBtn(String sampleName) {
        musicRecorderLogic.createRecord(sampleName);
    }

    public void onTouchStopRecorderBtn() {
        musicRecorderLogic.stopRecorder();
    }

    public void onTouchStartPlayerBtn(String recordName) {
        musicPlayerLogic.play(recordName , StorageType.EXTERNAL_STORAGE);
    }

    public void onTouchPauseBtn() {
        musicPlayerLogic.pause();
    }

    public void onTouchStopPlayerBtn() {
        musicPlayerLogic.stop();
    }

    public void onTouchPadBtn(String soundName) {
        musicPlayerLogic.play(soundName , StorageType.INTERNAL_STORAGE);
    }
    
    @Override
    public void onClose() {
    }
}
