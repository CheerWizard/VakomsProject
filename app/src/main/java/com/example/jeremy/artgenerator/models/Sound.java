package com.example.jeremy.artgenerator.models;

import com.example.jeremy.artgenerator.constants.SoundType;
import com.example.jeremy.artgenerator.business_logic.data.Path;

public class Sound {
    //sound model with appropriate fields
    private int id;
    private SoundType soundType;
    private String soundName;
    private Path path;

    public Sound() {
        //empty constructor
    }

    public Sound(int id , String soundName , Path path , SoundType soundType) {
        this.id = id;
        this.soundType = soundType;
        this.soundName = soundName;
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public SoundType getSoundType() {
        return soundType;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setSoundType(SoundType soundType) {
        this.soundType = soundType;
    }
}
