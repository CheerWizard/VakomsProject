package com.example.jeremy.artgenerator.utils.cache;

import com.example.jeremy.artgenerator.constants.SystemModes;

public final class SystemModeCache {

    private static int system_mode = SystemModes.OFFLINE_MODE;

    public static void setSystem_mode(int mode) {
        system_mode = mode;
    }

    public static int getSystem_mode() {
        return system_mode;
    }
}
