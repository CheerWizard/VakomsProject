package com.example.jeremy.artgenerator.utils.deserializers;

import com.example.jeremy.artgenerator.constants.FileNames;
import com.example.jeremy.artgenerator.models.Config;
import com.example.jeremy.artgenerator.ui.GlobalPadApplication;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ConfigDeserializer {

    private static Config config;

    private ConfigDeserializer() {}

    public static Config getConfig() {
        if (config == null) deserialize();
        return config;
    }

    private static void deserialize() {
        Gson gson = new Gson();
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            fileInputStream = GlobalPadApplication.getInstance().openFileInput(FileNames.CONFIG_JSON);
            inputStreamReader = new InputStreamReader(fileInputStream);
            config = gson.fromJson(inputStreamReader , Config.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
