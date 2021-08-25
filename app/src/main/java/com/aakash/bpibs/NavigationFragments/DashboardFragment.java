package com.aakash.bpibs.NavigationFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aakash.bpibs.ModelClass.StudentModel;
import com.aakash.bpibs.ProfileFragments.AssignmentFragment;
import com.aakash.bpibs.ProfileFragments.EBookFragment;
import com.aakash.bpibs.ProfileFragments.QuestionFragment;
import com.aakash.bpibs.ProfileFragments.TimeTableFragment;
import com.aakash.bpibs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView userName, courseName;
    private ImageView userAvatar;
    private RelativeLayout attendance, assignment, ebook, notifications, questions, timeTable;
    private DatabaseReference databaseReference;
    private String gender;

    FragmentTransaction transaction;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        userName = view.findViewById(R.id.user_name);
        courseName = view.findViewById(R.id.course_name);
        userAvatar = view.findViewById(R.id.user_avatar);
        attendance = view.findViewById(R.id.attendance_layout);
        assignment = view.findViewById(R.id.assignment_layout);
        notifications = view.findViewById(R.id.notification_layout);
        ebook = view.findViewById(R.id.ebook_layout);
        questions = view.findViewById(R.id.ques_layout);
        timeTable = view.findViewById(R.id.time_layout);
        transaction = getFragmentManager().beginTransaction();
        fetchCardValues();


        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Reserved for Major Project.", Toast.LENGTH_SHORT).show();
            }
        });


        assignment.setOnClickListener(v -> {

            AssignmentFragment assignmentFragment = new AssignmentFragment();
            transaction.replace(R.id.container, assignmentFragment, null).addToBackStack(null).commit();

        });

        notifications.setOnClickListener(v -> {

        });


        ebook.setOnClickListener(v -> {
            EBookFragment eBookFragment = new EBookFragment();
            transaction.replace(R.id.container, eBookFragment, null).addToBackStack(null).commit();
        });


        questions.setOnClickListener(v -> {
            QuestionFragment questionFragment = new QuestionFragment();
            transaction.replace(R.id.container, questionFragment, null).addToBackStack(null).commit();
        });


        timeTable.setOnClickListener(v -> {
            TimeTableFragment timeTableFragment = new TimeTableFragment();
            transaction.replace(R.id.container, timeTableFragment, null).addToBackStack(null).commit();
        });

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
                studentModel.setStudentName(dataSnapshot.getValue(StudentModel.class).getStudentName());
                studentModel.setRollNumber(dataSnapshot.getValue(StudentModel.class).getRollNumber());
                studentModel.setClassIn(dataSnapshot.getValue(StudentModel.class).getClassIn());
                studentModel.setBirthDate(dataSnapshot.getValue(StudentModel.class).getBirthDate());
                studentModel.setGender(dataSnapshot.getValue(StudentModel.class).getGender());

                userName.setText(studentModel.getStudentName());
                courseName.setText(studentModel.getClassIn());
                gender = studentModel.getGender();

                if (gender.equalsIgnoreCase("Female")) {
                    userAvatar.setImageDrawable(getResources().getDrawable(R.drawable.girl));
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}