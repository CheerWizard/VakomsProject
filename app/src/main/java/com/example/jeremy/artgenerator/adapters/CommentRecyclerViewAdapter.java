package com.example.jeremy.artgenerator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.business_logic.data.Comment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder> implements IListAdapter<Comment> {

    private List<Comment> list;
    private IListAdapter.ListEventListener listEventListener;
    private LayoutInflater layoutInflater;

    public CommentRecyclerViewAdapter(Context context, List<Comment> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public CommentRecyclerViewAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentViewHolder(layoutInflater.inflate(R.layout.comment_view_holder , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerViewAdapter.CommentViewHolder commentViewHolder, int i) {
        final int position = i;
        Comment comment = list.get(position);
        //text view render
        commentViewHolder.nameTextView.setText(comment.getName());
        commentViewHolder.bodyTextView.setText(comment.getBody());
        //set listeners on buttons
        if (listEventListener != null) {
            commentViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listEventListener.onTouchDeleteBtn(position);
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

    public void onUpdateList(List<Comment> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public void addListener(ListEventListener listEventListener) {
        this.listEventListener = listEventListener;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView , bodyTextView;
        private Button deleteButton;

        private CommentViewHolder(View view) {
            super(view);
            initViews(view);
        }

        private void initViews(View view) {
            //init text views
            final TextView nameLabelTextView = (TextView) view.findViewById(R.id.title_label_text_view);
            final TextView bodyLabelTextView = (TextView) view.findViewById(R.id.body_label_text_view);
            nameTextView = view.findViewById(R.id.title_text_view);
            bodyTextView = view.findViewById(R.id.body_text_view);
            //init buttons
            deleteButton = view.findViewById(R.id.delete_btn);
        }
    }

}