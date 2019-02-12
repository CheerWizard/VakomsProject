package com.example.jeremy.artgenerator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.business_logic.data.Sample;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleRecyclerViewAdapter.SampleViewHolder> implements IListAdapter {

    private List<Sample> list;
    private IListAdapter.SampleListEventListener sampleListEventListener;
    private LayoutInflater layoutInflater;
    private SampleViewHolder sampleViewHolder;

    public SampleRecyclerViewAdapter(Context context, List<Sample> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public SampleRecyclerViewAdapter.SampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        sampleViewHolder = new SampleViewHolder(layoutInflater.inflate(R.layout.sample_view_holder_layout , viewGroup , false));
        return sampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SampleRecyclerViewAdapter.SampleViewHolder sampleViewHolder, int i) {
        final int position = i;
        Sample sample = list.get(position);
        //text view render
        sampleViewHolder.samplePriorityTextView.setText(sample.getSamplePriority());
        sampleViewHolder.sampleNameTextView.setText(sample.getSampleName());
        //set listeners on buttons
        if (sampleListEventListener != null) {
            sampleViewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleListEventListener.OnTouchCancelBtn(position);
                }
            });
            sampleViewHolder.decrementPriorityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleListEventListener.OnTouchDecrementPriorityBtn(position);
                }
            });
            sampleViewHolder.incrementPriorityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleListEventListener.OnTouchIncrementPriorityBtn(position);
                }
            });
            sampleViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleListEventListener.OnTouchDeleteBtn(position);
                }
            });
            sampleViewHolder.saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleListEventListener.OnTouchSaveBtn(position);
                }
            });
            sampleViewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleListEventListener.OnTouchDownloadBtn(position);
                }
            });
            sampleViewHolder.uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleListEventListener.OnTouchUploadBtn(position);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onUpdateList(List<Sample> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public void addListener(SampleListEventListener sampleListEventListener) {
        this.sampleListEventListener = sampleListEventListener;
    }

    public TextView getSamplePriorityTextView() {
        return sampleViewHolder.samplePriorityTextView;
    }

    public TextView getSampleNameTextView() {
        return sampleViewHolder.sampleNameTextView;
    }

    public Button getIncrementPriorityButton() {
        return sampleViewHolder.incrementPriorityButton;
    }

    public Button getDecrementPriorityButton() {
        return sampleViewHolder.decrementPriorityButton;
    }

    public Button getDownloadButton() {
        return sampleViewHolder.downloadButton;
    }

    public Button getUploadButton() {
        return sampleViewHolder.uploadButton;
    }

    public Button getCancelButton() {
        return sampleViewHolder.cancelButton;
    }

    public Button getSaveButton() {
        return sampleViewHolder.saveButton;
    }

    public Button getDeleteButton() {
        return sampleViewHolder.deleteButton;
    }

    class SampleViewHolder extends RecyclerView.ViewHolder {

        private TextView samplePriorityTextView, sampleNameTextView;
        private Button incrementPriorityButton, decrementPriorityButton,
                downloadButton, uploadButton, cancelButton, saveButton, deleteButton;

        private SampleViewHolder(View view) {
            super(view);
            initViews(view);
        }

        private void initViews(View view) {
            //init text views
            final TextView samplePriorityLabelTextView = (TextView) view.findViewById(R.id.sample_priority_label);
            sampleNameTextView = (TextView) view.findViewById(R.id.sample_name);
            samplePriorityTextView = (TextView) view.findViewById(R.id.sample_priority);
            //init buttons
            incrementPriorityButton = (Button) view.findViewById(R.id.increment_priority);
            decrementPriorityButton = (Button) view.findViewById(R.id.decrement_priority);
            downloadButton = (Button) view.findViewById(R.id.download_btn);
            uploadButton = (Button) view.findViewById(R.id.upload_btn);
            cancelButton = (Button) view.findViewById(R.id.cancel_btn);
            saveButton = (Button) view.findViewById(R.id.save_btn);
            deleteButton = (Button) view.findViewById(R.id.delete_btn);
        }
    }

}