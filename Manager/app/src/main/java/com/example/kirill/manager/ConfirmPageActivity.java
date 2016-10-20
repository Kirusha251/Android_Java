package com.example.kirill.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kirill.manager.units.Student;

public class ConfirmPageActivity extends AppCompatActivity {
    Student student;
    TextView name;
    TextView surname;
    TextView age;
    TextView yearOfBirth;
    TextView rating;
    TextView country;
    TextView nationality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_page);
        name = (TextView)findViewById(R.id.nameText);
        surname = (TextView)findViewById(R.id.surnameText);
        age = (TextView)findViewById(R.id.ageText);
        yearOfBirth = (TextView)findViewById(R.id.yearOfBirthText);
        rating = (TextView)findViewById(R.id.ratingText);
        country = (TextView)findViewById(R.id.countryText);
        nationality = (TextView)findViewById(R.id.nationalityText);
        if(savedInstanceState!=null){
            student = savedInstanceState.getParcelable("student");
            name.setText(name.getText().toString()+" "+student.getName());
            surname.setText(surname.getText().toString()+" "+student.getSurname());
            age.setText(age.getText().toString()+" "+student.getAge());
            yearOfBirth.setText(yearOfBirth.getText().toString()+" "+student.getYearthOfbirth());
            rating.setText(rating.getText().toString()+" "+student.getRating());
            country.setText(country.getText().toString()+" "+student.getCountry());
            nationality.setText(nationality.getText().toString()+" "+student.getNationality());
        }
        else {
            student = (Student)getIntent().getParcelableExtra("student");
            name.setText(name.getText().toString()+" "+student.getName());
            surname.setText(surname.getText().toString()+" "+student.getSurname());
            age.setText(age.getText().toString()+" "+Integer.toString(student.getAge()));
            yearOfBirth.setText(yearOfBirth.getText().toString()+" "+student.getYearthOfbirth());
            rating.setText(rating.getText().toString()+" "+Integer.toString(student.getRating()));
            country.setText(country.getText().toString()+" "+student.getCountry());
            nationality.setText(nationality.getText().toString()+" "+student.getNationality());
        }
    }

    public void onClickConfirmButton(View v){
        Intent intent = new Intent(ConfirmPageActivity.this,MainActivity.class);
        intent.putExtra("student",student);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    public void onClickPrevButtonC(View v){
        onBackPressed();
    }
}
