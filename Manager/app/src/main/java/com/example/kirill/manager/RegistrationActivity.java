package com.example.kirill.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.kirill.manager.units.Student;

public class RegistrationActivity extends AppCompatActivity {
    EditText login;
    EditText password;
    EditText confirmPassword;
    String fileName = "PasswordFile";
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        login = (EditText)findViewById(R.id.loginR);
        password = (EditText)findViewById(R.id.passwordR);
        confirmPassword = (EditText)findViewById(R.id.confirmPasswordR);
        if(savedInstanceState!=null){
            login.setText(savedInstanceState.get("login").toString());
            password.setText(savedInstanceState.get("pass").toString());
            confirmPassword.setText(savedInstanceState.get("confPass").toString());
        }
    }
    public void onRegistrationButtonClick(View view){
        if(readFromSharedPreferenceFile(getApplicationContext(),login.getText().toString()).equals("Not Found")) {
            if (password.getText().toString().hashCode() == confirmPassword.getText().toString().hashCode()) {
                writeToSharedPreferenceFile(getApplicationContext(),login.getText().toString(),
                        Integer.toString(password.getText().toString().hashCode()));
                student = new Student(" "," ",1," ",1," "," ");
                student.setLoginHash(login.getText().toString());
                Intent intent = new Intent(RegistrationActivity.this,FullNameActivity.class);
                intent.putExtra("student",student);
                startActivity(intent);
                finish();
            }
        }
    }

    public String readFromSharedPreferenceFile(Context context, String key){

        String value;
        SharedPreferences sharedPreferencesExample = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        value = sharedPreferencesExample.getString(key,"Not Found");// 1-ый параметр ключ, 2-ой параметр,
        // что вернуть если по заданному ключу ничего нет
        return  value;
    }

    public void writeToSharedPreferenceFile(Context context, String key, String value){

        SharedPreferences sharedPreferencesExample = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesExample.edit();
        editor.putString(key,value);
        editor.apply();
        Log.d("Log_2", "Файл " + fileName + " записан");

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("login",login.getText().toString());
        outState.putString("pass",password.getText().toString());
        outState.putString("confPass",confirmPassword.getText().toString());
    }
}
