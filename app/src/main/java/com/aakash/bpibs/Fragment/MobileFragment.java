package com.aakash.bpibs.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.aakash.bpibs.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MobileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MobileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    FrameLayout frameLayout;
    TextInputLayout mobileNumber;
    CountryCodePicker codePicker;
    Button proceedButton;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MobileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MobileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MobileFragment newInstance(String param1, String param2) {
        MobileFragment fragment = new MobileFragment();
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
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);
        frameLayout = view.findViewById(R.id.mobile_frame);
        codePicker = view.findViewById(R.id.country_code_picker);
        mobileNumber = view.findViewById(R.id.mobile_number_input);
        proceedButton = view.findViewById(R.id.mobile_proceed);


        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getUserEnteredNumber = mobileNumber.getEditText().getText().toString().trim();
                if (getUserEnteredNumber.isEmpty() || getUserEnteredNumber.length() < 10) {
                    mobileNumber.setError("Valid Mobile Number is Required");
                    mobileNumber.requestFocus();
                    return;
                }
                String getMobileNumber = "+" + codePicker.getFullNumber() + getUserEnteredNumber;

                verifyOtp(getMobileNumber);
            }
        });


        return view;
    }

    private void verifyOtp(String getMobileNumber) {
        OtpFragment otpFragment = new OtpFragment();
        Bundle args = new Bundle();
        args.putString("mobile", getMobileNumber);
        frameLayout.removeAllViews();
        otpFragment.setArguments(args);
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).add(R.id.fragment_container, otpFragment).commit();

    }
}