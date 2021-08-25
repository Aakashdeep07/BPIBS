package com.aakash.bpibs.ProfileFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.bpibs.Adapters.AssignmentAdapter;
import com.aakash.bpibs.ModelClass.AssignmentModel;
import com.aakash.bpibs.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference databaseReference;
    private String key;
    private RecyclerView recyclerView;
    private AssignmentAdapter assignmentAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AssignmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignmentFragment newInstance(String param1, String param2) {
        AssignmentFragment fragment = new AssignmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        key = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child(key);
        databaseReference.keepSynced(true);


        recyclerView = view.findViewById(R.id.assignment_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);

        getStudent();


        return view;
    }

    private void getStudent() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String courseIn = dataSnapshot.child("classIn").getValue(String.class);
                String semIn = dataSnapshot.child("semIn").getValue(String.class);

                if (courseIn != null || semIn != null) {
                    getAssignment(courseIn, semIn);
                }
                if (courseIn == null || semIn == null) {
                    Toast.makeText(getActivity(), " NULL" + courseIn + semIn, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Could not fetch assignment.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAssignment(String courseIn, String semIn) {
        FirebaseRecyclerOptions<AssignmentModel> options = new FirebaseRecyclerOptions.Builder<AssignmentModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Assignment").child(courseIn).child(semIn), AssignmentModel.class)
                .setLifecycleOwner(getViewLifecycleOwner())
                .build();
        assignmentAdapter = new AssignmentAdapter(options, getActivity().getApplicationContext());
        assignmentAdapter.startListening();
        recyclerView.setAdapter(assignmentAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        assignmentAdapter.stopListening();
    }
}