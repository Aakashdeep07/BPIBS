package com.aakash.bpibs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aakash.bpibs.Activities.MainActivity;
import com.aakash.bpibs.Activities.SignUpActivity;
import com.aakash.bpibs.Fragment.MobileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        frameLayout = findViewById(R.id.fragment_container);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            loadView();
        }

        if (currentUser != null) {
            checkSession(currentUser);

        }

    }

    private void loadView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MobileFragment mobileFragment = new MobileFragment();
        frameLayout.removeAllViews();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fragment_container, mobileFragment, null).commit();

    }

    private void checkSession(FirebaseUser currentUser) {

        if (currentUser != null) {
            String mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child(mobile);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean is_Verified = dataSnapshot.child("verified").getValue(Boolean.class);
                    if (is_Verified == null) {
                        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else if (!is_Verified) {

                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();


                    } else {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SignInActivity.this, "Something unexpected happened.", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }


}