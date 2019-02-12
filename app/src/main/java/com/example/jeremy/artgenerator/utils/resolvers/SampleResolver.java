package com.example.jeremy.artgenerator.utils.resolvers;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.constants.SamplePriorities;
import com.example.jeremy.artgenerator.constants.SortType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class SampleResolver {
    //returns ordered list by appropriate types
    public static List<Sample> sort(final List<Sample> list, SortType sortType) {
        switch (sortType) {
            //sort by sample priority
            case BY_PRIORITY:
                Collections.sort(list, new Comparator<Sample>() {
                    @Override
                    public int compare(Sample o1, Sample o2) {
                        return o1.compareTo(o2);
                    }
                });
                break;
            //sort by sample name
            case BY_SAMPLE_NAME:
                Collections.sort(list, new Comparator<Sample>() {
                    @Override
                    public int compare(Sample o1, Sample o2) {
                        return o1.getSampleName().compareTo(o2.getSampleName());
                    }
                });
                break;
        }
        return list;
    }

    public static void incrementPriority(Handler handler , int priority) {
        if (priority >= SamplePriorities.LOWEST_PRIORITY && priority < SamplePriorities.BIGGEST_PRIORITY + 1) {
            int res = priority;
            res++;
            handler.sendMessage(handler.obtainMessage(ProcessStates.Successful.STATUS_SAMPLE_PRIORITY_CHANGED , res));
        }
        else handler.sendEmptyMessage(ProcessStates.Failed.STATUS_SAMPLE_PRIORITY_OUT_OF_BOUNDS);
    }

    public static void decrementPriority(Handler handler , int priority) {
        if (priority > SamplePriorities.LOWEST_PRIORITY - 1 && priority <= SamplePriorities.BIGGEST_PRIORITY) {
            int res = priority;
            res--;
            handler.sendMessage(handler.obtainMessage(ProcessStates.Successful.STATUS_SAMPLE_PRIORITY_CHANGED , res));
        }
        else handler.sendEmptyMessage(ProcessStates.Failed.STATUS_SAMPLE_PRIORITY_OUT_OF_BOUNDS);
    }
}
