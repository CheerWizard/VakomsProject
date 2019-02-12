package com.example.jeremy.artgenerator.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.constants.Preferences;
import com.example.jeremy.artgenerator.constants.StorageType;
import com.example.jeremy.artgenerator.ui.GlobalPadApplication;
import com.example.jeremy.artgenerator.utils.builders.PathBuilder;

public class MusicPlayerLogic implements IMusicPlayerLogic {
    //audio player + thread executor
    private SoundPool soundPool;
    //settings
    private SharedPreferences sharedPreferences;
    //context
    private Context context;
    //global var
    private int pathId;

    public MusicPlayerLogic() {
        initSoundPool();
        context = GlobalPadApplication.getInstance();
        sharedPreferences = context.getSharedPreferences(Preferences.SOUND_POOL_SETTINGS , Context.MODE_PRIVATE);
    }

    private void initSoundPool() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                soundPool = new SoundPool.Builder()
                        .setMaxStreams(20)
                        .setAudioAttributes(audioAttributes)
                        .build();
            }
            else soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void play(final String soundName , StorageType storageType) {
        switch (storageType) {
            case INTERNAL_STORAGE:
                final int playId = load(soundName);
                if (sharedPreferences == null)
                    soundPool.play(playId, Preferences.Default.left_volume, Preferences.Default.right_volume, Preferences.Default.priority, Preferences.Default.loop, Preferences.Default.rate);
                else soundPool.play(playId,
                        sharedPreferences.getInt(Preferences.SOUND_POOL_LEFT_VOLUME, Preferences.Default.left_volume),
                        sharedPreferences.getInt(Preferences.SOUND_POOL_RIGHT_VOLUME, Preferences.Default.right_volume),
                        sharedPreferences.getInt(Preferences.SOUND_POOL_PRIORITY, Preferences.Default.priority),
                        sharedPreferences.getInt(Preferences.SOUND_POOL_LOOP, Preferences.Default.loop),
                        sharedPreferences.getInt(Preferences.SOUND_POOL_RATE, Preferences.Default.rate));
                break;
            case EXTERNAL_STORAGE:
                pathId = soundPool.load(PathBuilder.build(context, soundName, storageType).toString(), 1);
                soundPool.play(pathId, Preferences.Default.left_volume, Preferences.Default.right_volume, Preferences.Default.priority, Preferences.Default.loop, Preferences.Default.rate);
                break;
        }
    }

    private int load(String soundName) {
        int id = 0;
        switch (soundName) {
            case :
                soundPool.load(context , R.raw.background_bass_1 , 1);
                break;
        }
        return id;
    }

    @Override
    public void stop() {
        soundPool.stop(pathId);
    }

    @Override
    public void pause() {
       soundPool.pause(pathId);
    }
}
