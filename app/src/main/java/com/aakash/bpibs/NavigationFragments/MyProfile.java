package com.aakash.bpibs.NavigationFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aakash.bpibs.ModelClass.StudentModel;
import com.aakash.bpibs.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputLayout name, father, roll, className, gender;
    private Button editDoneButton;
    private TextView mobileNumber;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference databaseReference;

    public MyProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfile newInstance(String param1, String param2) {
        MyProfile fragment = new MyProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        mobileNumber = view.findViewById(R.id.mobile_number_profile);
        name = view.findViewById(R.id.name_input_profile);
        father = view.findViewById(R.id.father_input_profile);
        roll = view.findViewById(R.id.roll_input_profile);
        className = view.findViewById(R.id.class_text_profile);
        gender = view.findViewById(R.id.gender_text_profile);
        editDoneButton = view.findViewById(R.id.edit_profile);
        editDoneButton.setEnabled(false);

        fetchCardValues();


        return view;
    }

    private void fetchCardValues() {
        String mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child(mobile);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentModel studentModel = new StudentModel();
                studentModel.setMobileNumber(dataSnapshot.getValue(StudentModel.class).getMobileNumber());
                studentModel.setStudentName(dataSnapshot.getValue(StudentModel.class).getStudentName());
                studentModel.setFatherName(dataSnapshot.getValue(StudentModel.class).getFatherName());
                studentModel.setRollNumber(dataSnapshot.getValue(StudentModel.class).getRollNumber());
                studentModel.setClassIn(dataSnapshot.getValue(StudentModel.class).getClassIn());
                studentModel.setBirthDate(dataSnapshot.getValue(StudentModel.class).getBirthDate());
                studentModel.setGender(dataSnapshot.getValue(StudentModel.class).getGender());

                mobileNumber.setText(studentModel.getMobileNumber());
                name.getEditText().setText(studentModel.getStudentName());
                father.getEditText().setText(studentModel.getFatherName());
                roll.getEditText().setText(studentModel.getRollNumber());
                className.getEditText().setText(studentModel.getClassIn());
                gender.getEditText().setText(studentModel.getGender());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}