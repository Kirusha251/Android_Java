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
            if(student.getAge()!=0) {
                ageText.setText(Integer.toString(student.getAge()));
            }
            int date[] = savedInstanceState.getIntArray("date");
            dateOfBirthday.updateDate(date[0],date[1],date[2]);
        }
        else {
            student = (Student)getIntent().getParcelableExtra("student");
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateOfBirthday.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(year<=calendar.get(Calendar.YEAR)) {
                    ageText.setText(Integer.toString(calendar.get(Calendar.YEAR) - year));
                }
                else{
                    ageText.setText("Увы, но вы еще не родились");
                }
            }
        });

    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if(!ageText.getText().toString().equals("")) {
            student.setAge(Integer.valueOf(ageText.getText().toString()));
        }
        savedInstanceState.putParcelable("student",student);
        savedInstanceState.putIntArray("date", new int[]{dateOfBirthday.getYear(),dateOfBirthday
        .getMonth(),dateOfBirthday.getDayOfMonth()});
    }

    public void onClickNextButtonB(View v){
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateOfBirthday.getYear(), dateOfBirthday.getMonth(), dateOfBirthday.getDayOfMonth());
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String inspectionDate  = df.format(calendar.getTimeInMillis());
        if(!ageText.getText().toString().equals("")) {
            student.setAge(Integer.valueOf(ageText.getText().toString()));
        }
        student.setYearthOfbirth(inspectionDate);
        Intent intent = new Intent(BirthdayActivity.this,OtherData.class);
        intent.putExtra("student",student);
        startActivity(intent);
    }

    public void onClickPrevButtonB(View v){
        onBackPressed();
    }


}
