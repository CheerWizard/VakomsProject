package com.example.jeremy.artgenerator.view_models;

import android.os.Handler;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.business_logic.repositories.SampleRepository;
import com.example.jeremy.artgenerator.utils.resolvers.SampleResolver;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SampleListViewModel extends ViewModel implements IContract.IViewModel {
    //repository
    private SampleRepository sampleRepository;

    private Handler handler;
//for dagger2
//    public SampleListViewModel(SampleRepository sampleRepository , Handler handler) {
//        this.sampleRepository = sampleRepository;
//        this.handler = handler;
//    }

    public SampleListViewModel() {
        sampleRepository = new SampleRepository();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
        sampleRepository.setHandler(handler);
    }

    public LiveData<Sample> getSampleLiveData(int position) {
        return sampleRepository.getSample(position);
    }

    public LiveData<List<Sample>> getSampleListLiveData() {
        return sampleRepository.getAllSamples();
    }

    public void onTouchUpdateBtn(Sample sample) {
        sampleRepository.update(sample);
    }

    public void onTouchPlusPriorityBtn(int priority) {
        SampleResolver.incrementPriority(handler , priority);
    }

    public void onTouchMinusPriorityBtn(int priority) {
        SampleResolver.decrementPriority(handler , priority);
    }

    public void onTouchDeleteBtn(int position) {
        sampleRepository.remove(position);
    }

    public void onTouchDeleteAllBtn() {
        sampleRepository.removeAll();
    }

    public void onTouchAddBtn(Sample sample) {
        sampleRepository.insert(sample);
    }

    @Override
    public void onClose() {
        sampleRepository.close();
    }
}
