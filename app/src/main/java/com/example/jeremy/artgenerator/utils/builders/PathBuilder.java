package com.example.jeremy.artgenerator.utils.builders;

import android.content.Context;
import android.os.Environment;

import com.example.jeremy.artgenerator.constants.AudioFormatConstants;
import com.example.jeremy.artgenerator.constants.StorageType;
import com.example.jeremy.artgenerator.business_logic.data.Path;

import java.util.UUID;

public final class PathBuilder {

    public static Path build(Context context , String soundName , StorageType storageType) {
        Path path = null;
        switch (storageType) {
            case EXTERNAL_STORAGE:
                path = new Path(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + soundName + AudioFormatConstants.GP3);
                break;
            case INTERNAL_STORAGE:
                path = new Path("android.resource://" + context.getPackageName() + "/raw/" + soundName);
                break;
        }
        return path;
    }

}
