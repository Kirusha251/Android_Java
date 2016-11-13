package com.example.kirill.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kirill.manager.adapters.CustomAdapter;
import com.example.kirill.manager.container.Container;
import com.example.kirill.manager.units.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class AdminPageActivity extends AppCompatActivity {
    Student student;
    ArrayList<Student> listStudent = new ArrayList<>();
    ListView studentView;
    public CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        studentView = (ListView)findViewById(R.id.studentListView);
        Container.setStudentList(new ArrayList<Student>());
        Container.getStudentFromJson(getFilesDir(),"fileName.json");
        listStudent=Container.getStudentList();
        if(getIntent().getParcelableExtra("student")!=null) {
            student = (Student) getIntent().getParcelableExtra("student");
            listStudent.add(student);
        }
        if(savedInstanceState!=null){
            student = savedInstanceState.getParcelable("student");
            listStudent= savedInstanceState.getParcelable("studentList");
        }
        adapter = new CustomAdapter(getApplicationContext(),listStudent);
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
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }
    public void onSortMenuItemClick(MenuItem item){
        Collections.sort(adapter.studentList,new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getSurname().compareTo(o2.getSurname());
            }
        });
        adapter.notifyDataSetChanged();

    }
    public void onExitMenuItemClick(MenuItem item){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void addStudentClick(MenuItem item){
        Container.setStudentList(listStudent);
        Container.SaveToInternalStorage(getFilesDir(),"fileName.json");
        Intent intent = new Intent(AdminPageActivity.this,FullNameActivity.class);
        intent.putExtra("student",new Student(" "," ",1," ",1," "," "));
        startActivity(intent);
        finish();
    }
    public void deleteSelectedClick(MenuItem item){
        ArrayList<Student> temp = new ArrayList<>();
        for (Student student:adapter.studentList
                ) {
            if(student.isSelected()) {
                temp.add(student);
                listStudent.remove(student);
                deleteFromSharedPreferenceFile(getApplicationContext(),student.getLoginHash());
            }
        }
        for (Student st:temp
                ) {
            adapter.remove(st);
        }
        adapter.notifyDataSetChanged();
        Container.SaveToInternalStorage(getFilesDir(),"fileName.json");

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
        Container.getStudentFromJson(getFilesDir(),"fileName.json");
        listStudent = Container.getStudentList();
        super.onResume();
    }
    public void deleteFromSharedPreferenceFile(Context context, String key){


        SharedPreferences sharedPreferencesExample = context.getSharedPreferences("PasswordFile", Context.MODE_PRIVATE);
        sharedPreferencesExample.edit().remove(key).apply();


    }


}
