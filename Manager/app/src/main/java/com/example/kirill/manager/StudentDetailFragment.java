package com.example.kirill.manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirill.manager.container.Container;
import com.example.kirill.manager.units.Student;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int idStudent;

    private OnFragmentInteractionListener mListener;

    public StudentDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentDetailFragment newInstance(String param1, String param2) {
        StudentDetailFragment fragment = new StudentDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            idStudent = savedInstanceState.getInt("studentId");
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void setIdStudent(int id){
        this.idStudent = id;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("studentId",idStudent);
    }

    @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        Container.getStudentList();
        if(view != null){
            TextView name = (TextView) view.findViewById(R.id.nameText);
            name.setText("Имя:"+Container.getStudentList().get(idStudent).getName());
            TextView surname = (TextView) view.findViewById(R.id.surnameText);
            surname.setText("Фамилия:"+Container.getStudentList().get(idStudent).getSurname());
            TextView age = (TextView) view.findViewById(R.id.ageText);
            age.setText("Возраст:"+Integer.toString(Container.getStudentList().get(idStudent).getAge()));
            TextView yearOfBirth = (TextView) view.findViewById(R.id.yearOfBirthText);
            yearOfBirth.setText("Дата рождения:"+Container.getStudentList().get(idStudent).getYearthOfbirth());
            TextView rating = (TextView) view.findViewById(R.id.ratingText);
            rating.setText("Рейтинг:"+Integer.toString(Container.getStudentList().get(idStudent).getRating()));
            TextView country = (TextView) view.findViewById(R.id.countryText);
            country.setText("Страна:"+Container.getStudentList().get(idStudent).getCountry());
            TextView nationality = (TextView)view.findViewById(R.id.nationalityText);
            nationality.setText("Национальность:"+Container.getStudentList().get(idStudent).getNationality());
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.selectStudentId);
            checkBox.setChecked(Container.getStudentList().get(idStudent).isSelected());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Student student;
                    student = Container.studentList.get(idStudent);
                    student.setSelected(isChecked);
                    Container.updateStudent(student);
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            idStudent = savedInstanceState.getInt("studentId");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
