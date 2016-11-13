package com.example.kirill.manager.container;

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
import java.util.Calendar;

/**
 * Created by Kirill on 23.10.2016.
 */

public  class Container {
    public static ArrayList<Student> studentList = new ArrayList<>();

    public static void addStudent(Student student){
        studentList.add(student);
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

    public static ArrayList<Student> getStudentList() {
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
