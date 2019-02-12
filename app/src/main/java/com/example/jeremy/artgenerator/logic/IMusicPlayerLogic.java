package com.example.jeremy.artgenerator.logic;

import com.example.jeremy.artgenerator.business_logic.data.Path;
import com.example.jeremy.artgenerator.constants.StorageType;

public interface IMusicPlayerLogic {
    void play(String soundName , StorageType storageType);
    void stop();
    void pause();
}
