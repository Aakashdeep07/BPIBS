package com.aakash.bpibs.ProfileFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.bpibs.Adapters.QuestionAdapter;
import com.aakash.bpibs.ModelClass.QuestionPaperHandler;
import com.aakash.bpibs.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionLoader#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionLoader extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private QuestionAdapter questionAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String initClass = "BCA";
    private TextView course;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionLoader() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionLoader.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionLoader newInstance(String param1, String param2) {
        QuestionLoader fragment = new QuestionLoader();
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
        View view = inflater.inflate(R.layout.fragment_question_loader, container, false);
        course = view.findViewById(R.id.course_title);
        Bundle bundle = getArguments();
        if (bundle != null) {
            initClass = bundle.getString("course");
            course.setText(initClass);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("QuestionPaper");
        databaseReference.keepSynced(true);


        recyclerView = view.findViewById(R.id.question_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);

        fetchAndSet(initClass);

        return view;
    }

    private void fetchAndSet(String whichClass) {
        FirebaseRecyclerOptions<QuestionPaperHandler> options =
                new FirebaseRecyclerOptions.Builder<QuestionPaperHandler>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("QuestionPaper").child("Class").child(whichClass).orderByChild("paperDescription"), QuestionPaperHandler.class)
                        .build();
        questionAdapter = new QuestionAdapter(options, getContext().getApplicationContext());
        recyclerView.setAdapter(questionAdapter);
        questionAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        fetchAndSet(initClass);
        questionAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchAndSet(initClass);
        questionAdapter.startListening();
    }


}