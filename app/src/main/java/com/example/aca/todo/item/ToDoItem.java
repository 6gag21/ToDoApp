package com.example.aca.todo.item;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity(tableName = "todo_items")
public class ToDoItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;
    @ColumnInfo(name = "reminder_date")
    @Ignore
    private Date reminderDate;
    @Ignore
    private Date date;
    private boolean reminder;
    @Ignore
    private int imageButtonVisibility;




    public ToDoItem(){
    }

    public long getId() {
        return id;
    }

    public void setId(long mId) {
        this.id = mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        this.description = mDescription;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date mDate) {
        this.reminderDate = mDate;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean mReminder) {
        this.reminder = mReminder;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date mDate) {
        this.date = mDate;
    }

    public int getImageButtonVisibility() {
        return imageButtonVisibility;
    }

    public void setImageButtonVisibility(int mImageButtonVisibility) {
        this.imageButtonVisibility = mImageButtonVisibility;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<ToDoItem> CREATOR = new Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel source) {
            return new ToDoItem(source);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    private ToDoItem(Parcel in){

    }

}
