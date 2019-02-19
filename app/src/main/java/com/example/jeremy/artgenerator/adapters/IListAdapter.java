package com.example.jeremy.artgenerator.adapters;

import android.view.View;

import java.util.List;

//wrap view adapters
public interface IListAdapter<T> {
    void onUpdateList(List<T> samples);
    interface ListEventListener {
        void onTouchDeleteBtn(int position);
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
    void addListener(ListEventListener listEventListener);
}
