package com.example.jeremy.artgenerator.adapters;

import android.widget.Button;
import android.widget.TextView;

import com.example.jeremy.artgenerator.business_logic.data.Sample;

import java.util.List;

//wrap list view and recycler view adapters
public interface IListAdapter {
    void onUpdateList(List<Sample> samples);
    interface SampleListEventListener {
        void OnTouchIncrementPriorityBtn(int position);
        void OnTouchDecrementPriorityBtn(int position);
        void OnTouchDownloadBtn(int position);
        void OnTouchUploadBtn(int position);
        void OnTouchCancelBtn(int position);
        void OnTouchSaveBtn(int position);
        void OnTouchDeleteBtn(int position);
    }
    void addListener(SampleListEventListener sampleListEventListener);
    TextView getSamplePriorityTextView();
    TextView getSampleNameTextView();
    Button getIncrementPriorityButton();
    Button getDecrementPriorityButton();
    Button getDownloadButton();
    Button getUploadButton();
    Button getCancelButton();
    Button getSaveButton();
    Button getDeleteButton();
}
