package com.example.kirill.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kirill.manager.adapters.CustomAdapter;
import com.example.kirill.manager.container.Container;
import com.example.kirill.manager.units.Student;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    String fileName="PasswordFile";
    EditText login;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText)findViewById(R.id.loginText);
        password = (EditText)findViewById(R.id.passwordText);
        if(savedInstanceState!=null){
            if(savedInstanceState.get("login") != null) {
                login.setText(savedInstanceState.get("login").toString());
            }
            else{
                login.setText("");
            }
            if(savedInstanceState.get("pass") != null) {
                password.setText(savedInstanceState.get("pass").toString());
            }
            else {
                password.setText("");
            }
        }
        writeToSharedPreferenceFile(getApplicationContext(),"Admin",Integer.toString("Admin".hashCode()));
    }


    public void onEnterButtonClick(View view){
        if(readFromSharedPreferenceFile(getApplicationContext(),login.getText().toString())
                .equals(Integer.toString(password.getText().toString().hashCode()))){
            if(login.getText().toString().equals("Admin")) {
                Intent intent = new Intent(MainActivity.this, AdminPageActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
                intent.putExtra("loginHash",login.getText().toString());
                startActivity(intent);
                finish();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Неверынй логин или пароль",Toast.LENGTH_SHORT).show();
        }
    }

    public void registrationClick(View view){
        Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    public void writeToSharedPreferenceFile(Context context, String key, String value){

        SharedPreferences sharedPreferencesExample = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesExample.edit();
        editor.putString(key,value);
        editor.apply();
        Log.d("Log_2", "Файл " + fileName + " записан");

    }

    public String readFromSharedPreferenceFile(Context context, String key){

        String value;
        SharedPreferences sharedPreferencesExample = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        value = sharedPreferencesExample.getString(key,"Not Found");// 1-ый параметр ключ, 2-ой параметр,
        // что вернуть если по заданному ключу ничего нет
        return  value;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
            outState.putString("login",login.getText().toString());

            outState.putString("pass", password.getText().toString());

    }
}
