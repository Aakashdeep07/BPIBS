package com.aakash.bpibs.ProfileFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.bpibs.Adapters.BookAdapter;
import com.aakash.bpibs.ModelClass.BookHandler;
import com.aakash.bpibs.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EBookLoader#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EBookLoader extends Fragment {


    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String initClass = "BCA";
    private TextView course;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EBookLoader() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EBookLoader.
     */
    // TODO: Rename and change types and number of parameters
    public static EBookLoader newInstance(String param1, String param2) {
        EBookLoader fragment = new EBookLoader();
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
        View view = inflater.inflate(R.layout.fragment_e_book_loader, container, false);
        course = view.findViewById(R.id.course_title);
        Bundle bundle = getArguments();
        if (bundle != null) {
            initClass = bundle.getString("course");
            course.setText(initClass);
        }


        databaseReference = FirebaseDatabase.getInstance().getReference().child("EBook");
        databaseReference.keepSynced(true);

        recyclerView = view.findViewById(R.id.ebook_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);

        fetchBooks(initClass);
        return view;
    }

    private void fetchBooks(String classIs) {

        FirebaseRecyclerOptions<BookHandler> options = new FirebaseRecyclerOptions.Builder<BookHandler>()
                .setQuery(databaseReference.child("Class").child(classIs), BookHandler.class)
                .build();

        bookAdapter = new BookAdapter(options, getActivity().getApplicationContext());
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.startListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        fetchBooks(initClass);
        bookAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fetchBooks(initClass);
        bookAdapter.stopListening();
    }
}