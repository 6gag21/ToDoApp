package com.example.aca.todo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aca.todo.R;

public class ToDoItemViewHolder extends RecyclerView.ViewHolder {

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cb_for_delete:
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemRemoveClick(getAdapterPosition());
                    }

                    default:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            }
        }
    };

    private OnItemClickListener mOnItemClickListener;

    private TextView mTitle;
    private TextView mDescription;
    private TextView mDate;
    private TextView mReminderDate;
    private CheckBox mCheckBox;
    private ImageButton mImageButton;

    public TextView getTitle() {
        return mTitle;
    }

    public void setTitle(TextView mTitle) {
        this.mTitle = mTitle;
    }

    public TextView getDescription() {
        return mDescription;
    }

    public void setDescription(TextView mDescription) {
        this.mDescription = mDescription;
    }

    public TextView getDate() {
        return mDate;
    }

    public void setDate(TextView mDate) {
        this.mDate = mDate;
    }

    public TextView getReminderDate() {
        return mReminderDate;
    }

    public void setReminderDate(TextView mReminderDate) {
        this.mReminderDate = mReminderDate;
    }

    public CheckBox getCheckBox() {
        return mCheckBox;
    }

    public void setCheckBox(CheckBox mCheckBox) {
        this.mCheckBox = mCheckBox;
    }

    public ImageButton getImageButton() {
        return mImageButton;
    }

    public void setImageButton(ImageButton mImageButton) {
        this.mImageButton = mImageButton;
    }

    public ToDoItemViewHolder(@NonNull View itemView) {
        super(itemView);

        mTitle = itemView.findViewById(R.id.tv_title);
        mDescription = itemView.findViewById(R.id.tv_description);
        mDate = itemView.findViewById(R.id.tv_date_and_time);
        mReminderDate = itemView.findViewById(R.id.tv_date_and_time_reminder);
        mCheckBox = itemView.findViewById(R.id.cb_for_delete);
        mImageButton = itemView.findViewById(R.id.ib_reminder_cardView);
        mCheckBox.setVisibility(View.GONE);
        mImageButton.setVisibility(View.GONE);

        itemView.findViewById(R.id.cb_for_delete).setOnClickListener(mOnClickListener);
        itemView.setOnClickListener(mOnClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int adapterPosition);
        void onItemRemoveClick(int adapterPosition);
    }
}
