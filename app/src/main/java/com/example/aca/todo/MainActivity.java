package com.example.aca.todo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aca.todo.fragment.ListFragment;
import com.example.aca.todo.fragment.ToDoItemFragment;
import com.example.aca.todo.item.ToDoItem;
import com.example.aca.todo.persistence.dao.DbWrapper;
import com.example.aca.todo.persistence.dao.ToDoItemDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ToDoItemDao mToDoItemDao = DbWrapper.getAppDatabase().toDoItemDao();


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openAddToDoItem();
        }
    };

    private ListFragment.OnFragmentInteractionListener mOnItemsInteractionListener = new ListFragment.OnFragmentInteractionListener() {
        @Override
        public void onEditItem(ToDoItem toDoItem) {
            openEditToDoItem(toDoItem);
        }
    };

    private ToDoItemFragment.OnFragmentInteractionListener mOnItemInteractionListener = new ToDoItemFragment.OnFragmentInteractionListener() {
        @Override
        public void onItemCreated(ToDoItem toDoItem) {
            delegateItemCreationToFragment(toDoItem);
        }

        @Override
        public void onItemChanged(ToDoItem toDoItem) {
            delegateItemChangeToFragment(toDoItem);
        }
    };

    private ListFragment mListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        FloatingActionButton fb = findViewById(R.id.fb_add_item);
        fb.setOnClickListener(mOnClickListener);

        mListFragment =(ListFragment) (getFragmentManager().findFragmentById(R.id.fragment_activity_main));
        mListFragment.setOnInteractionListener(mOnItemsInteractionListener);
    }

    private void openAddToDoItem(){
        ToDoItemFragment toDoItemFragment = ToDoItemFragment.newInstance(null);
        toDoItemFragment.setOnInteractionListener(mOnItemInteractionListener);
        openFragmentInContainer(toDoItemFragment,true);
    }

    private void openEditToDoItem(ToDoItem toDoItem){
        ToDoItemFragment toDoItemFragment = ToDoItemFragment.newInstance(toDoItem);
        toDoItemFragment.setOnInteractionListener(mOnItemInteractionListener);
        openFragmentInContainer(toDoItemFragment,true);
    }

    private void delegateItemCreationToFragment(ToDoItem toDoItem){
        mListFragment.addToDoItem(toDoItem);
        addToDatabase(toDoItem);
        getFragmentManager().popBackStack();
    }
    private void delegateItemChangeToFragment(ToDoItem toDoItem){
        mListFragment.editToDoItem(toDoItem);
        addToDatabase(toDoItem);
        getFragmentManager().popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_by_date:{

            }
            break;

            case R.id.search_item:{

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return true;
    }


    private void openFragmentInContainer(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_main, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
    }

    private void addToDatabase(final ToDoItem  toDoItem){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mToDoItemDao.insert(toDoItem);
                List<ToDoItem> entities = mToDoItemDao.findAll();
                logEntities(entities);
            }
        }).start();
    }

    private void logEntities(List<ToDoItem> entities) {
        for (ToDoItem entity : entities) {
            Log.v("MainActivity", entity.toString());
        }
    }

}

