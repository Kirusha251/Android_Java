package com.example.kirill.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.kirill.manager.adapters.CustomAdapter;
import com.example.kirill.manager.container.Container;
import com.example.kirill.manager.units.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminPageActivity extends AppCompatActivity implements StudentListFragment.StudentListListener {
    @Override
    public void itemClicked(int id) {
        StudentDetailFragment detail = new StudentDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        detail.setIdStudent(id);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            ft.replace(R.id.fragmContainer, detail);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }else{
            ft.replace(R.id.frameContainer,detail).addToBackStack("Stack");
        }
        ft.commit();
    }

    @Override
    public void sortButtonClicked() {

    }

    Student student;
    ArrayList<Student> listStudent = new ArrayList<>();
    ListView studentView;
    public CustomAdapter adapter;
    StudentListFragment frag;
    FragmentTransaction fTrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
       // Container.getStudentFromJson(getFilesDir(),"fileName.json");
        Container.getStudentList();
        frag = new StudentListFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            fTrans.replace(R.id.fragmContainer, frag);
            fTrans.addToBackStack(null);
            fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fTrans.commit();
        }else{
            StudentDetailFragment fragment1 = new StudentDetailFragment();
            fTrans.add(R.id.frameContainer,fragment1);
            fTrans.replace(R.id.listFragment,frag).commit();
        }
        /*listStudent=Container.getStudentList();
        if(getIntent().getParcelableExtra("student")!=null) {
            student = (Student) getIntent().getParcelableExtra("student");
            listStudent.add(student);
        }
        if(savedInstanceState!=null){
            student = savedInstanceState.getParcelable("student");
            listStudent= savedInstanceState.getParcelable("studentList");
        }*/
        /*adapter = new CustomAdapter(getApplicationContext(),listStudent);
        studentView.setAdapter(adapter);
        studentView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        studentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        studentView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view;
                checkBox.setChecked(!checkBox.isChecked());
                return true;
            }
        });*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }
    public void onSortMenuItemClick(MenuItem item){
        ArrayAdapter<String> arrayAdapter =(ArrayAdapter<String>)frag.getListAdapter();
        arrayAdapter.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        frag.setListAdapter(arrayAdapter);
        ((ArrayAdapter<String>) frag.getListAdapter()).notifyDataSetChanged();
    }
    public void onExitMenuItemClick(MenuItem item){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void getSelectedStudent(MenuItem item){
        SimpleCursorAdapter simpleCursorAdapter = (SimpleCursorAdapter) frag.getListAdapter();
        simpleCursorAdapter.changeCursor(Container.getSelectedStudentsCursor());
        frag.setListAdapter(simpleCursorAdapter);
        ((SimpleCursorAdapter) frag.getListAdapter()).notifyDataSetChanged();
    }
    public void getAllStudents(MenuItem item){
        SimpleCursorAdapter simpleCursorAdapter = (SimpleCursorAdapter) frag.getListAdapter();
        simpleCursorAdapter.changeCursor(Container.getStudentCursor());
        frag.setListAdapter(simpleCursorAdapter);
        ((SimpleCursorAdapter) frag.getListAdapter()).notifyDataSetChanged();
    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("student",student);
        savedInstanceState.putParcelableArrayList("studentList",listStudent);
    }


    @Override
    protected void onPause() {
        Container.setStudentList(listStudent);
        //Container.SaveToInternalStorage(getFilesDir(),"fileName.json");
        super.onPause();
    }

    @Override
    protected void onResume() {
        listStudent = Container.getStudentList();
        super.onResume();
    }
    public void deleteFromSharedPreferenceFile(Context context, String key){


        SharedPreferences sharedPreferencesExample = context.getSharedPreferences("PasswordFile", Context.MODE_PRIVATE);
        sharedPreferencesExample.edit().remove(key).apply();


    }


}
