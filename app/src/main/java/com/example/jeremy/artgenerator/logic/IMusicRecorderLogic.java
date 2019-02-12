package com.example.jeremy.artgenerator.logic;

import com.example.jeremy.artgenerator.business_logic.data.Sample;

public interface IMusicRecorderLogic {
    void createRecord(String recordName);
    Sample stopRecorder();
}
