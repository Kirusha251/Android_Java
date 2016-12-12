package com.example.kirill.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.kirill.manager.adapters.CustomAdapter;
import com.example.kirill.manager.container.Container;

/**
 * Created by Kirill on 15.11.2016.
 */

public class StudentListFragment extends ListFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //CustomAdapter adapter;
        //adapter = new CustomAdapter(getContext(),Container.getStudentList());
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, Container.getStudentNames());
        final SimpleCursorAdapter adapter= new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,Container.getStudentCursor(),
                new String[]{"NAME","SURNAME"},new int[]{android.R.id.text1,android.R.id.text2});
        setListAdapter(adapter);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteFromSharedPreferenceFile(getContext(),Container.getStudentList().get(position).getLoginHash());
                Container.deleteStudent(Container.getStudentList().get(position));
                adapter.changeCursor(Container.getStudentCursor());
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    static interface StudentListListener{
        void itemClicked(int id);
        void sortButtonClicked();
    }
    public void sort(){

    }

    private StudentListListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (StudentListListener)getActivity();
    }
    public void deleteFromSharedPreferenceFile(Context context, String key){


        SharedPreferences sharedPreferencesExample = context.getSharedPreferences("PasswordFile", Context.MODE_PRIVATE);
        sharedPreferencesExample.edit().remove(key).apply();


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(listener != null){
            listener.itemClicked((int)position);
        }
    }


}
