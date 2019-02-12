package com.example.jeremy.artgenerator;

import com.example.jeremy.artgenerator.constants.StorageType;
import com.example.jeremy.artgenerator.utils.builders.PathBuilder;
import com.example.jeremy.artgenerator.ui.activities.LauncherActivity;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testPathBuilder() {
        LauncherActivity launcherActivity = new LauncherActivity();
        System.out.print(new PathBuilder(launcherActivity.getContext() , "kick" , StorageType.INTERNAL_STORAGE).build());
    }
}