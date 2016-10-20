package com.example.kirill.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.example.kirill.manager.units.Student;

public class FullNameActivity extends AppCompatActivity {
    Student student;
    EditText nameText;
    EditText surnameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_name);
        nameText = (EditText)findViewById(R.id.nameText);
        surnameText = (EditText)findViewById(R.id.surnameText);
        if(savedInstanceState!=null){
            student = savedInstanceState.getParcelable("student");
            nameText.setText(student.getName());
            surnameText.setText(student.getSurname());
        }
        else {
            student = (Student)getIntent().getParcelableExtra("student");
        }

    }
    public void onClickNextButtonF(View v){
        student.setName(nameText.getText().toString());
        student.setSurname(surnameText.getText().toString());
        Intent intent = new Intent(FullNameActivity.this,BirthdayActivity.class);
        intent.putExtra("student",student);
        startActivity(intent);
    }
    public void onClickPrevButtonF(View view){
        onBackPressed();
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("student",student);
    }
}
