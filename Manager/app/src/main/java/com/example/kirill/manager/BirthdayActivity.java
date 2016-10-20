package com.example.kirill.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kirill.manager.units.Student;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthdayActivity extends AppCompatActivity {
    Student student;
    DatePicker dateOfBirthday;
    EditText ageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        dateOfBirthday = (DatePicker)findViewById(R.id.dateOfBirthdayPicker);
        ageText = (EditText)findViewById(R.id.ageText);
        if(savedInstanceState!=null){
            student = savedInstanceState.getParcelable("student");
            ageText.setText(student.getAge());
        }
        else {
            student = (Student)getIntent().getParcelableExtra("student");
        }

    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("student",student);
    }

    public void onClickNextButtonB(View v){
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateOfBirthday.getYear()-1900, dateOfBirthday.getMonth(), dateOfBirthday.getDayOfMonth());
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String inspectionDate  = df.format(calendar.getTimeInMillis());
        student.setAge(Integer.valueOf(ageText.getText().toString()));
        student.setYearthOfbirth(inspectionDate);
        Intent intent = new Intent(BirthdayActivity.this,OtherData.class);
        intent.putExtra("student",student);
        startActivity(intent);
    }

    public void onClickPrevButtonB(View v){
        onBackPressed();
    }

}
