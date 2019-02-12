package com.example.jeremy.artgenerator.logic;

import android.media.MediaRecorder;

import com.example.jeremy.artgenerator.datasources.SampleDataSource;
import com.example.jeremy.artgenerator.business_logic.data.Duration;
import com.example.jeremy.artgenerator.business_logic.data.Path;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.wrappers.ExecutorWrapper;

import java.io.IOException;

public class MusicRecorderLogic implements IMusicRecorderLogic {

    //audio recorder
    private MediaRecorder mediaRecorder;
    //thread executor
    private ExecutorWrapper executorWrapper;
    //global vars
    private int duration;
    private boolean isRunning;
    private Sample sample;

    public MusicRecorderLogic(ExecutorWrapper executorWrapper) {
        this.executorWrapper = executorWrapper;
        duration = 0;
    }

    private void preparingMediaRecorder() throws IOException {
        mediaRecorder.prepare();
        mediaRecorder.start();
        isRunning = true;
        incrementDuration();
    }

    private void initMediaRecorder(String recordName) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(recordPath);
        sample = new Sample(recordName , new Path(recordPath), null);
    }

    @Override
    public void createRecord(final String recordName) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mediaRecorder == null) initMediaRecorder(recordName);
                    preparingMediaRecorder();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Sample stopRecorder() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                sample.setDuration(new Duration(duration));
                mediaRecorder.stop();
                isRunning = false;
                mediaRecorder.release();
            }
        });
        return sample;
    }

    private void incrementDuration() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) duration++;
            }
        }).start();
    }

}
