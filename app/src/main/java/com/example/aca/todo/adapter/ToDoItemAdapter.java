package com.example.aca.todo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aca.todo.R;
import com.example.aca.todo.item.ToDoItem;
import com.example.aca.todo.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemViewHolder> {

    private ToDoItemViewHolder.OnItemClickListener mOnItemClickListener = new ToDoItemViewHolder.OnItemClickListener() {
        @Override
        public void onItemClick(int adapterPosition) {
            if(mOnItemClickedListener != null){
                mOnItemClickedListener.onItemClicked(mData.get(adapterPosition));
            }
        }

        @Override
        public void onItemRemoveClick(int adapterPosition) {
            removeItem(adapterPosition);
        }
    };

    private List<ToDoItem> mData;
    private OnItemClickedListener mOnItemClickedListener;

    public ToDoItemAdapter(){
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_item,viewGroup,false);
        ToDoItemViewHolder toDoItemViewHolder = new ToDoItemViewHolder(v);
        toDoItemViewHolder.setOnItemClickListener(mOnItemClickListener);
        return toDoItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemViewHolder toDoItemViewHolder, int i) {
        ToDoItem item = mData.get(i);

        toDoItemViewHolder.getTitle().setText(item.getTitle());
        toDoItemViewHolder.getDescription().setText(item.getDescription());
        toDoItemViewHolder.getDate().setText(DateUtil.formatDateToString(item.getDate()) +"  " + DateUtil.formatTimeToString(item.getDate()));
        if(item.getReminderDate() != null) {
            toDoItemViewHolder.getReminderDate().setText(DateUtil.formatDateToString(item.getReminderDate()) + "  " + DateUtil.formatTimeToString(item.getReminderDate()));
        }
        else {
        toDoItemViewHolder.getReminderDate().setText("");
        }

        toDoItemViewHolder.getImageButton().setVisibility(item.getImageButtonVisibility());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<ToDoItem> getData() {
        return mData;
    }

    public void setData(List<ToDoItem> mData) {
        this.mData = mData;
    }

    public void addItem(ToDoItem item){
        mData.add(item);
        notifyItemInserted(mData.size()-1);
    }

    public void editItem(ToDoItem item){
        mData.set((int) item.getId(),item);
        notifyItemChanged((int) item.getId());

    }

    public void removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickedListener (OnItemClickedListener onItemClickedListener){
        mOnItemClickedListener = onItemClickedListener;
    }

    public interface OnItemClickedListener{
        void onItemClicked(ToDoItem toDoItem);
    }
}
