package com.example.aca.todo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;

import com.example.aca.todo.R;
import com.example.aca.todo.adapter.ToDoItemAdapter;
import com.example.aca.todo.item.ToDoItem;

public class ListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ListFragment(){
    }

    private ToDoItemAdapter.OnItemClickedListener mOnItemClickedListener = new ToDoItemAdapter.OnItemClickedListener() {
        @Override
        public void onItemClicked(ToDoItem toDoItem) {
           if(mListener != null){
               mListener.onEditItem(toDoItem);
           }
        }
    };

    private ToDoItemAdapter mToDoItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view){
        mToDoItemAdapter = new ToDoItemAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        mToDoItemAdapter.setOnItemClickedListener(mOnItemClickedListener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mToDoItemAdapter);
    }

    public void addToDoItem(ToDoItem toDoItem){
        mToDoItemAdapter.addItem(toDoItem);
    }

    public void editToDoItem(ToDoItem toDoItem){
        mToDoItemAdapter.editItem(toDoItem);
    }

    public void setOnInteractionListener(OnFragmentInteractionListener onInteractionListener){
        mListener = onInteractionListener;
    }

    public interface OnFragmentInteractionListener{
        void onEditItem(ToDoItem toDoItem);
    }
}
