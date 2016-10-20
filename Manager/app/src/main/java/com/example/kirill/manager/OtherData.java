package com.example.kirill.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kirill.manager.units.Student;

public class OtherData extends AppCompatActivity {
    Student student;
    EditText countryText;
    EditText nationalityText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_data);
        countryText = (EditText)findViewById(R.id.countryText);
        nationalityText = (EditText)findViewById(R.id.nationalityText);
        if(savedInstanceState!=null){
            student = savedInstanceState.getParcelable("student");
            countryText.setText(student.getCountry());
            nationalityText.setText(student.getNationality());
        }
        else {
            student = (Student)getIntent().getParcelableExtra("student");
        }
    }

    public void onClickNextButtonO(View view){
        student.setCountry(countryText.getText().toString());
        student.setNationality(nationalityText.getText().toString());
        Intent intent = new Intent(OtherData.this,ConfirmPageActivity.class);
        intent.putExtra("student",student);
        startActivity(intent);
    }
    public void onClickPrevButtonO(View view){
        onBackPressed();
    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("student",student);
    }

}
