package com.example.aca.todo.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.aca.todo.R;
import com.example.aca.todo.item.ToDoItem;
import com.example.aca.todo.util.DateUtil;

import java.util.Calendar;

public class ToDoItemFragment extends Fragment {

    public static final String ARG_TODO_ITEM = "arg.todo.item";

    public static final int CREATE_MODE = 0;
    public static final int EDIT_MODE = 1;

    private OnFragmentInteractionListener mListener;

    public ToDoItemFragment(){
    }

    public static ToDoItemFragment newInstance(ToDoItem toDoItem){
        ToDoItemFragment fragment = new ToDoItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TODO_ITEM, toDoItem);
        fragment.setArguments(args);
        return fragment;
    }

    DatePickerDialog.OnDateSetListener mOnDateSetListenerForReminder = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendarForReminder.set(Calendar.YEAR, year);
            calendarForReminder.set(Calendar.MONTH, month);
            calendarForReminder.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateReminderDate();
        }
    };

    TimePickerDialog.OnTimeSetListener mOnTimeSetListenerForReminder = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendarForReminder.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendarForReminder.set(Calendar.MINUTE, minute);

            updateReminderDate();
        }
    };

    DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            openTimePicker();
        }
    };

    TimePickerDialog.OnTimeSetListener mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            updateDate();
        }
    };

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.sc_reminder: {
                    if (isChecked) {
                        mDateAndTime.setVisibility(View.VISIBLE);
                    } else
                        mDateAndTime.setVisibility(View.GONE);
                    }

                break;

                case R.id.sc_priority: {
                    if (isChecked) {
                        mSeekBar.setVisibility(View.VISIBLE);
                    }
                    else mSeekBar.setVisibility(View.GONE);
                }
                break;
            }
        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.et_date:{
                    openDatePickerForReminder();
                }
                break;
                case R.id.et_time:{
                    openTimePickerForReminder();
                }
                break;
                case R.id.tv_date:{
                    openDatePicker();
                }
            }
        }
    };

    private TextInputEditText mTitle;
    private TextInputEditText mDescription;
    private SwitchCompat mReminderSwitch;
    private LinearLayout mDateAndTime;
    private TextView mDate;
    private EditText mDateSet;
    private EditText mTimeSet;
    private SwitchCompat mPrioritySwitch;
    private SeekBar mSeekBar;

    private int mMode;
    private ToDoItem mToDoItem;
    private Calendar calendarForReminder = Calendar.getInstance();
    private Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mToDoItem = getArguments().getParcelable((ARG_TODO_ITEM));
            if (mToDoItem == null){
                mMode = CREATE_MODE;
            }
            else{
                mMode = EDIT_MODE;
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);

        updateDate();
        updateReminderDate();

        if(mToDoItem != null){
            fill(mToDoItem);
        }
    }


    private void init(View view){
        mTitle = view.findViewById(R.id.et_title);
        mDescription = view.findViewById(R.id.et_description);
        mReminderSwitch = view.findViewById(R.id.sc_reminder);
        mDateAndTime = view.findViewById(R.id.ll_date_and_time);
        mDate = view.findViewById(R.id.tv_date);
        mDateSet = view.findViewById(R.id.et_date);
        mTimeSet = view.findViewById(R.id.et_time);
        mPrioritySwitch = view.findViewById(R.id.sc_priority);
        mSeekBar = view.findViewById(R.id.sb_priority);

        mDateAndTime.setVisibility(View.GONE);
        mSeekBar.setVisibility(View.GONE);
        mReminderSwitch.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mPrioritySwitch.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mDateSet.setOnClickListener(mOnClickListener);
        mTimeSet.setOnClickListener(mOnClickListener);
        mDate.setOnClickListener(mOnClickListener);
        }

    private void submit(){
        if(checkInput()){
            if(mListener != null){
                switch (mMode){
                    case CREATE_MODE:
                        mListener.onItemCreated(createItem());
                        break;
                    case EDIT_MODE:
                        mListener.onItemChanged(createItem());
                        break;
                }
            }
        }
    }

    private void fill(ToDoItem toDoItem){
        mTitle.setText(toDoItem.getTitle());
        mDescription.setText(toDoItem.getDescription());
        calendar.setTime(toDoItem.getDate());

            if(toDoItem.isReminder()) {
                calendarForReminder.setTime(toDoItem.getReminderDate());
            }

        mReminderSwitch.setChecked(toDoItem.isReminder());
        updateDate();
        updateReminderDate();

    }

    private ToDoItem createItem(){
        if(mToDoItem == null){
            mToDoItem = new ToDoItem();
        }

        mToDoItem.setTitle(mTitle.getText().toString());
        mToDoItem.setDescription(mDescription.getText().toString());
        mToDoItem.setDate(calendar.getTime());
        mToDoItem.setReminder(mReminderSwitch.isChecked());

        if(mReminderSwitch.isChecked()) {
            mToDoItem.setReminderDate(calendarForReminder.getTime());
            mToDoItem.setImageButtonVisibility(View.VISIBLE);
        } else{
            mToDoItem.setReminderDate(null);
            mToDoItem.setImageButtonVisibility(View.GONE);
        }

        return mToDoItem;
    }

    private void openDatePickerForReminder(){
        new DatePickerDialog(getActivity(), mOnDateSetListenerForReminder,
                calendarForReminder.get(Calendar.YEAR),
                calendarForReminder.get(Calendar.MONTH),
                calendarForReminder.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openTimePickerForReminder(){
        new TimePickerDialog(getActivity(), mOnTimeSetListenerForReminder,
                calendarForReminder.get(Calendar.HOUR_OF_DAY),
                calendarForReminder.get(Calendar.MINUTE),true).show();
    }

    private void openDatePicker(){
        new DatePickerDialog(getActivity(), mOnDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openTimePicker(){
        new TimePickerDialog(getActivity(), mOnTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),true).show();
    }

    private void updateReminderDate(){
        mDateSet.setText(DateUtil.formatDateToString(calendarForReminder.getTime()));
        mTimeSet.setText(DateUtil.formatTimeToString(calendarForReminder.getTime()));
    }

    private void updateDate(){
        mDate.setText(DateUtil.formatDateToString(calendar.getTime()) + " " + DateUtil.formatTimeToString(calendar.getTime()));
    }

    private boolean checkInput(){
        boolean isValid;
        if (isEmpty(mTitle)){
            isValid = false;
            setError();
        }
        else isValid = true;
        return isValid;
    }

    private void setError(){
        mTitle.setError("Title is empty");
    }

    private boolean isEmpty( TextInputEditText textInputEditText){
        return TextUtils.isEmpty(textInputEditText.getText().toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_todo_item,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.it_add:{
                submit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOnInteractionListener(OnFragmentInteractionListener listener){
        mListener = listener;
    }

        public interface OnFragmentInteractionListener{
        void onItemCreated(ToDoItem toDoItem);
        void onItemChanged(ToDoItem toDoItem);
        }
}
