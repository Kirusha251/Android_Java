package com.example.kirill.manager.container;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kirill.manager.units.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kirill on 23.10.2016.
 */

public  class Container {
    public static SQLiteDatabase dataBase;
    public static ArrayList<Student> studentList = new ArrayList<>();

    public static void addStudent(Student student){
        studentList.add(student);
    }
    public static void insertStudent(Student student){
        ContentValues values = new ContentValues();
        values.put("NAME",student.getName());
        values.put("SURNAME",student.getSurname());
        values.put("AGE",student.getAge());
        values.put("BIRTHDAY",student.getYearthOfbirth());
        values.put("RATING",student.getRating());
        values.put("COUNTRY",student.getCountry());
        values.put("NATIONALITY",student.getNationality());
        values.put("LOGINHASH",student.getLoginHash());
        values.put("SELECTED",Boolean.toString(student.isSelected()));
        dataBase.insert("Students",null,values);
    }
    public static void deleteStudent(Student student){
        dataBase.delete("Students", "NAME=?", new String[]{student.getName()});
    }
    public static void updateStudent(Student student){
        ContentValues values = new ContentValues();
        values.put("NAME",student.getName());
        values.put("SURNAME",student.getSurname());
        values.put("AGE",student.getAge());
        values.put("BIRTHDAY",student.getYearthOfbirth());
        values.put("RATING",student.getRating());
        values.put("COUNTRY",student.getCountry());
        values.put("NATIONALITY",student.getNationality());
        values.put("LOGINHASH",student.getLoginHash());
        values.put("SELECTED",Boolean.toString(student.isSelected()));
        dataBase.update("Students",values,"NAME=?",new String[]{student.getName()});
    }
    public static void removeStudent(int index){
        studentList.remove(index);
    }
    public static void getStudentFromJson(File dir,String fileName){

            File file = new File(dir,fileName);
            String data="";
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    setStudentList(new ArrayList<Student>());
                } else {
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    while ((data = bufferedReader.readLine()) != null) {
                        sb.append(data);
                    }
                    data = sb.toString();
                    Type type = new TypeToken<ArrayList<Student>>(){}.getType();
                    ArrayList<Student> std = new ArrayList<>();
                    std=new Gson().fromJson(data, type);
                    if(std==null){
                        Container.setStudentList(new ArrayList<Student>());
                    }
                    else {
                        Container.setStudentList(std);
                    }
                }
            }
            catch (Exception e){

            }
    }
    public static void SaveToInternalStorage(File dir,String fileName){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Student>>(){}.getType();
        String s = gson.toJson(studentList,type);
        BufferedWriter bw;
        File file = new File(dir,fileName);
        try{
            FileWriter fw = new FileWriter(file,false);
            bw = new BufferedWriter(fw);
            bw.write(s);
            bw.close();


        }
        catch (IOException e){
        }

    }
    public static Cursor getSelectedStudentsCursor(){
        return dataBase.query("Students",new String[]{"_id","NAME","SURNAME","AGE","BIRTHDAY","RATING",
                "COUNTRY","NATIONALITY","LOGINHASH","SELECTED"},"SELECTED=?",new String[]{Boolean.TRUE.toString()},null,null,null);
    }
    public static Cursor getStudentCursor(){
        return dataBase.query("Students",new String[]{"_id","NAME","SURNAME","AGE","BIRTHDAY","RATING",
                "COUNTRY","NATIONALITY","LOGINHASH","SELECTED"},null,null,null,null,null);

    }
    public static ArrayList<Student> getStudentList() {
        studentList.clear();
        Cursor cursor = dataBase.query("Students",new String[]{"NAME","SURNAME","AGE","BIRTHDAY","RATING",
        "COUNTRY","NATIONALITY","LOGINHASH","SELECTED"},null,null,null,null,null);
        if(cursor.moveToFirst()){

            do{

                studentList.add(new Student(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),
                        cursor.getInt(4),cursor.getString(5),cursor.getString(6),Boolean.valueOf(cursor.getString(8)),cursor.getString(7)));
            }
            while(cursor.moveToNext());

        }
        return Container.studentList;
    }

    public static void setStudentList(ArrayList<Student> studentList) {
        Container.studentList = studentList;
    }
    public static Student getStudentByHashLogin(String hashLogin){
        for (Student st: studentList
             ) {
            if(hashLogin.equals(st.getLoginHash()))
            {
                return  st;
            }
        }
        return null;
    }
    public static ArrayList<String> getStudentNames(){
            String[] studentNames = new String[studentList.size()];
            int i = 0;
            for (Student student : studentList
                    ) {
                studentNames[i] = student.getName() + ", " + student.getSurname() + ".";
                i++;
            }
            return new ArrayList<String>(Arrays.asList(studentNames));
        }
}
