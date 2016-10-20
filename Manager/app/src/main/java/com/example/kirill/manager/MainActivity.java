package com.example.kirill.manager;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.kirill.manager.units.Student;

import java.util.ArrayList;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {
    Student student;
    ArrayList<Student> listStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listStudent = new ArrayList<>();
        if(getIntent().getParcelableExtra("student")!=null) {
            student = (Student) getIntent().getParcelableExtra("student");
            listStudent.add(student);
        }
        if(savedInstanceState!=null){
            student = savedInstanceState.getParcelable("student");
            listStudent= savedInstanceState.getParcelable("studentList");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }
    public void addStudentClick(MenuItem item){
        Intent intent = new Intent(MainActivity.this,FullNameActivity.class);
        intent.putExtra("student",new Student(" "," ",1," ",1," "," "));
        startActivity(intent);
        finish();
    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("student",student);
        savedInstanceState.putParcelableArrayList("studentList",listStudent);
    }


    @Override
    protected void onPause() {

        super.onPause();
    }
    /*Intent intent = new Intent(MainActivity.this,FullNameActivity.class);
    intent.putExtra("student",new Student(" "," ",1," ",1," "," "));
    startActivity(intent);*/
}
