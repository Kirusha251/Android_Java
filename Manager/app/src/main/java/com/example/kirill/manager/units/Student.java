package com.example.kirill.manager.units;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill on 19.10.2016.
 */

public class Student implements Parcelable{
    private String name;
    private String surname;
    private int age;
    private String yearthOfbirth;
    private int rating;
    private String country;
    private String nationality;
    private boolean isSelected;
    private String loginHash;

    public String getLoginHash() {
        return loginHash;
    }

    public void setLoginHash(String loginHash) {
        this.loginHash = loginHash;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Student))return false;
        Student student = (Student) obj;
        if(!this.name.equals(((Student) obj).name)){
            return false;
        }
        if(!this.surname.equals(((Student) obj).surname)){
            return false;
        }
        if (!this.yearthOfbirth.equals(((Student) obj).yearthOfbirth)){
            return false;
        }
        else{
            return  true;
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Student(String name, String surname , int age, String yearthOfbirth, int rating, String country, String nationality){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.yearthOfbirth = yearthOfbirth;
        this.rating = rating;
        this.country = country;
        this.nationality = nationality;
        this.isSelected = false;
        this.loginHash="";

    }
    public Student(String name, String surname , int age, String yearthOfbirth, int rating, String country, String nationality,Boolean isSelected,String loginHash){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.yearthOfbirth = yearthOfbirth;
        this.rating = rating;
        this.country = country;
        this.nationality = nationality;
        this.isSelected = isSelected;
        this.loginHash=loginHash;

    }

    public Student(Parcel in){
        String[] data = new String[9];
        in.readStringArray(data);
        name = data[0];
        surname = data[1];
        age = Integer.valueOf(data[2]);
        yearthOfbirth = data[3];
        rating = Integer.valueOf(data[4]);
        country=data[5];
        nationality = data[6];
        isSelected = Boolean.getBoolean(data[7]);
        loginHash = data[8];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{name,surname, Integer.toString(age),yearthOfbirth,Integer.toString(rating),country,nationality,Boolean.toString(isSelected),loginHash});
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {

        @Override
        public Student createFromParcel(Parcel source) {
            return new Student (source);
        }

        @Override
        public Student [] newArray(int size) {
            return new Student[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getYearthOfbirth() {
        return yearthOfbirth;
    }

    public void setYearthOfbirth(String yearthOfbirth) {
        this.yearthOfbirth = yearthOfbirth;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
