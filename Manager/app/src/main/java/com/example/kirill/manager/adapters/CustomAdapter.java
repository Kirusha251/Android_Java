package com.example.kirill.manager.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirill.manager.R;
import com.example.kirill.manager.StudentDetailFragment;
import com.example.kirill.manager.StudentListFragment;
import com.example.kirill.manager.units.Student;

import java.util.ArrayList;

/**
 * Created by Kirill on 26.10.2016.
 */

public class CustomAdapter extends ArrayAdapter<Student>{
   public ArrayList<Student> studentList = null;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Student> resource) {
        super(context,R.layout.custom_layout,resource);
// TODO Auto-generated constructor stub
        this.context = context;
        this.studentList = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        final ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            /*convertView = inflater.inflate(R.layout.custom_layout,null);
            holder.txtName = (TextView)convertView.findViewById(R.id.textView1);
            holder.chkTick = (CheckBox)convertView.findViewById(R.id.checkBox1);*/
            convertView = inflater.inflate(R.layout.rowlayout,null);
            holder.checkedTextView = (CheckedTextView) convertView.findViewById(R.id.txt_lan);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkedTextView.setText(studentList.get(position).getName()+" "+ studentList.get(position).getSurname());
        holder.checkedTextView.setChecked(studentList.get(position).isSelected());
        holder.checkedTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(studentList.get(position).isSelected()==false){
                    holder.checkedTextView.setChecked(true);
                    studentList.get(position).setSelected(true);
                }
                else{
                    holder.checkedTextView.setChecked(false);
                    studentList.get(position).setSelected(false);
                }

                return true;
            }
        });
        holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show();
            }
        });
        //holder.txtName.setText(studentList.get(position).getName()+" "+ studentList.get(position).getSurname());
        /*holder.chkTick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                studentList.get(position).setSelected(isChecked);
            }
        });*/
        /*holder.chkTick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               //holder.chkTick.setChecked(!holder.chkTick.isChecked());
                return false;
            }
        });*/
        return convertView;
    }

    @Override
    public void remove(Student object) {
        studentList.remove(object);
        //super.remove(object);
    }

    static class ViewHolder {
        //TextView txtName;
        //CheckBox chkTick;
        CheckedTextView checkedTextView;
    }
}
