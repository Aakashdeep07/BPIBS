package com.aakash.bpibs.ProfileFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.aakash.bpibs.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeTableFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RelativeLayout bca, bba, mvoc, bvoc, mca, mba;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimeTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeTableFragment newInstance(String param1, String param2) {
        TimeTableFragment fragment = new TimeTableFragment();
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
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);


        bca = view.findViewById(R.id.bca_layout);
        mca = view.findViewById(R.id.mca_layout);
        bba = view.findViewById(R.id.bba_layout);
        bvoc = view.findViewById(R.id.bvoc_layout);
        mvoc = view.findViewById(R.id.mvoc_layout);
        mba = view.findViewById(R.id.mba_layout);


        Bundle bundle = new Bundle();
        String key = "course";
        bca.setOnClickListener(v -> {
            bundle.putString(key, "BCA");
            TimeTableLoader timeTableLoader = new TimeTableLoader();
            timeTableLoader.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, timeTableLoader, null).addToBackStack(null).commit();
        });
        mca.setOnClickListener(v -> {
            bundle.putString(key, "MCA");
            TimeTableLoader timeTableLoader = new TimeTableLoader();
            timeTableLoader.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, timeTableLoader, null).addToBackStack(null).commit();
        });
        bba.setOnClickListener(v -> {
            bundle.putString(key, "BBA");
            TimeTableLoader timeTableLoader = new TimeTableLoader();
            timeTableLoader.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, timeTableLoader, null).addToBackStack(null).commit();
        });
        mba.setOnClickListener(v -> {
            bundle.putString(key, "MBA");
            TimeTableLoader timeTableLoader = new TimeTableLoader();
            timeTableLoader.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, timeTableLoader, null).addToBackStack(null).commit();
        });
        bvoc.setOnClickListener(v -> {
            bundle.putString(key, "BVOC");
            TimeTableLoader timeTableLoader = new TimeTableLoader();
            timeTableLoader.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, timeTableLoader, null).addToBackStack(null).commit();
        });
        mvoc.setOnClickListener(v -> {
            bundle.putString(key, "MVOC");
            TimeTableLoader timeTableLoader = new TimeTableLoader();
            timeTableLoader.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, timeTableLoader, null).addToBackStack(null).commit();
        });


        return view;
    }
}