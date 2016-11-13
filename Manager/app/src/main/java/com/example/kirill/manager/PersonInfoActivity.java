package com.example.kirill.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kirill.manager.container.Container;
import com.example.kirill.manager.units.Student;

public class PersonInfoActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_person_info);
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
        if(getIntent().getParcelableExtra("student")!=null){
            student = (Student) getIntent().getParcelableExtra("student");
            Container.addStudent(student);
            name.setText(name.getText().toString()+" "+student.getName());
            surname.setText(surname.getText().toString()+" "+student.getSurname());
            age.setText(age.getText().toString()+" "+Integer.toString(student.getAge()));
            yearOfBirth.setText(yearOfBirth.getText().toString()+" "+student.getYearthOfbirth());
            rating.setText(rating.getText().toString()+" "+Integer.toString(student.getRating()));
            country.setText(country.getText().toString()+" "+student.getCountry());
            nationality.setText(nationality.getText().toString()+" "+student.getNationality());
        }
        else {
            student = Container.getStudentByHashLogin((String)getIntent().getStringExtra("loginHash"));
            name.setText(name.getText().toString()+" "+student.getName());
            surname.setText(surname.getText().toString()+" "+student.getSurname());
            age.setText(age.getText().toString()+" "+Integer.toString(student.getAge()));
            yearOfBirth.setText(yearOfBirth.getText().toString()+" "+student.getYearthOfbirth());
            rating.setText(rating.getText().toString()+" "+Integer.toString(student.getRating()));
            country.setText(country.getText().toString()+" "+student.getCountry());
            nationality.setText(nationality.getText().toString()+" "+student.getNationality());
        }

    }


    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("student",student);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.student_menu,menu);
        return true;
    }
    public void changeDataClick(MenuItem menuItem){
        Intent intent = new Intent(this,FullNameActivity.class);
        intent.putExtra("student",student);
        startActivity(intent);
    }
    public void deleteUserClick(MenuItem menuItem){
        Container.studentList.remove(student);
        deleteFromSharedPreferenceFile(getApplicationContext(),student.getLoginHash());
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void deleteFromSharedPreferenceFile(Context context, String key){


        SharedPreferences sharedPreferencesExample = context.getSharedPreferences("PasswordFile", Context.MODE_PRIVATE);
        sharedPreferencesExample.edit().remove(key).apply();


    }
    public void exitClick(MenuItem menuItem){
        Container.SaveToInternalStorage(getFilesDir(),"fileName.json");
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
