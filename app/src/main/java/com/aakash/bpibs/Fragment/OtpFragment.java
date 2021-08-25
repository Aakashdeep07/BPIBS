package com.aakash.bpibs.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aakash.bpibs.Activities.MainActivity;
import com.aakash.bpibs.Activities.SignUpActivity;
import com.aakash.bpibs.R;
import com.airbnb.lottie.LottieAnimationView;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView mobileForOtp;
    private PinView pin;
    private String codeFound;
    private Button verifyCode;
    private FirebaseAuth mAuth;
    private LinearLayout lottieLayout;
    private LottieAnimationView otpLottie;
    private TextView lottieText;
    private LinearLayout otpLayout;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtpFragment newInstance(String param1, String param2) {
        OtpFragment fragment = new OtpFragment();
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
        String mobileNumber = getArguments().getString("mobile");
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        mAuth = FirebaseAuth.getInstance();
        mobileForOtp = view.findViewById(R.id.mobile_for_otp);
        pin = view.findViewById(R.id.pinForm);
        verifyCode = view.findViewById(R.id.verify_otp);
        mobileForOtp.setText(mobileNumber);
        otpLottie = view.findViewById(R.id.ok_lottie);
        lottieText = view.findViewById(R.id.sending_otp);
        lottieLayout = view.findViewById(R.id.lottie_layout);
        otpLayout = view.findViewById(R.id.otp_layout);
        otpLottie.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieLayout.setVisibility(View.GONE);
                lottieText.setVisibility(View.GONE);
                otpLottie.setVisibility(View.GONE);
                otpLayout.setVisibility(View.VISIBLE);
            }
        }, 4000);


        sendVerificationCodeToUser(mobileNumber);

        verifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfUserExistsOrNot();
            }
        });

        return view;
    }

    private void sendVerificationCodeToUser(String mobileNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobileNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeFound = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pin.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            otpLayout.setVisibility(View.GONE);
            lottieLayout.setVisibility(View.VISIBLE);
            lottieText.setText("Oh Oh ! \n It was not expected!");
            lottieText.setTextSize(24);
            lottieText.setVisibility(View.VISIBLE);
            otpLottie.setAnimation("network_error.json");
            otpLottie.playAnimation();
            otpLottie.setRepeatCount(6);
            otpLottie.setVisibility(View.VISIBLE);
        }
    };

    private void verifyCode(String code) {

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeFound, code);
            signInWithPhoneAuthCredential(credential);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getActivity(), "Verification could not be completed.", Toast.LENGTH_SHORT).show();
            // The verification code entered was invalid
            otpLayout.setVisibility(View.GONE);
            lottieLayout.setVisibility(View.VISIBLE);
            lottieText.setText("Oh Oh ! \n It was not expected!");
            lottieText.setTextSize(24);
            lottieText.setVisibility(View.VISIBLE);
            otpLottie.setAnimation("network_error.json");
            otpLottie.playAnimation();
            otpLottie.setRepeatCount(6);
            otpLottie.setVisibility(View.VISIBLE);

        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser currentUser = mAuth.getCurrentUser();
//                            if (currentUser != null) {
//                                String mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
//                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child(mobile);
//                                databaseReference.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        Boolean is_Verified = dataSnapshot.child("verified").getValue(Boolean.class);
//                                        if (is_Verified == null) {
//                                            Intent intent = new Intent(getActivity(), SignUpActivity.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                            startActivity(intent);
//                                            Toast.makeText(getActivity(), "Please enter all the details", Toast.LENGTH_LONG).show();
//                                            getActivity().finish();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                                        Toast.makeText(getActivity(), "Something unexpected happened.", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//
//                            }

                            Toast.makeText(getActivity(), "Verification Successful.", Toast.LENGTH_SHORT).show();


                            otpLayout.setVisibility(View.GONE);
                            lottieLayout.setVisibility(View.VISIBLE);
                            lottieText.setText("OTP\nVerified!");
                            lottieText.setVisibility(View.VISIBLE);
                            lottieText.setTextSize(36);
                            otpLottie.setAnimation("process_complete.json");
                            otpLottie.playAnimation();
                            otpLottie.setRepeatCount(1);
                            otpLottie.setVisibility(View.VISIBLE);

                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            Handler handler = new Handler();
                            if (isNewUser) {

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getActivity(), SignUpActivity.class);
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        getActivity().finish();

                                    }
                                }, 4000);

                            } else {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        getActivity().finish();

                                    }
                                }, 4000);


                            }


                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getActivity(), "Verification could not be completed.", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                                otpLayout.setVisibility(View.GONE);
                                lottieLayout.setVisibility(View.VISIBLE);
                                lottieText.setText("Oh Oh ! \n It was not expected!");
                                lottieText.setVisibility(View.VISIBLE);
                                lottieText.setTextSize(24);
                                otpLottie.setAnimation("network_error.json");
                                otpLottie.playAnimation();
                                otpLottie.setRepeatCount(6);
                                otpLottie.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(getActivity(), "Failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
            otpLayout.setVisibility(View.GONE);
            lottieLayout.setVisibility(View.VISIBLE);
            lottieText.setText("Oh Oh ! \n It was not expected!");
            lottieText.setTextSize(24);
            lottieText.setVisibility(View.VISIBLE);
            otpLottie.setAnimation("network_error.json");
            otpLottie.setRepeatCount(6);
            otpLottie.playAnimation();
            otpLottie.setVisibility(View.VISIBLE);
        });
    }

    private void checkIfUserExistsOrNot() {
        String code = pin.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}