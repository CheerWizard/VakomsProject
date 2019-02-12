package com.example.jeremy.artgenerator.constants;

public final class Preferences {
    public static final String SOUND_POOL_SETTINGS = "sound_pool_settings";
    public static final String SOUND_POOL_LEFT_VOLUME = "sound_pool_left_volume";
    public static final String SOUND_POOL_RIGHT_VOLUME = "sound_pool_right_volume";
    public static final String SOUND_POOL_PRIORITY = "sound_pool_priority";
    public static final String SOUND_POOL_RATE = "sound_pool_rate";
    public static final String SOUND_POOL_LOOP = "sound_pool_loop";

    public static class Default {
        public static final int priority = 1;
        public static final int left_volume = 1;
        public static final int right_volume = 1;
        public static final int loop = 0;
        public static final int rate = 0;
    }
}
