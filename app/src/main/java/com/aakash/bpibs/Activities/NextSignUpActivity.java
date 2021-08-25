package com.aakash.bpibs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aakash.bpibs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class NextSignUpActivity extends AppCompatActivity {

    private RadioGroup genderRadio;
    private RadioButton genderButton;
    private DatePicker dobPicker;
    private Button signUpProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_sign_up);
        genderRadio = findViewById(R.id.gender_radio);
        dobPicker = findViewById(R.id.birth_date);
        signUpProceed = findViewById(R.id.next_signup);

        String student_name = getIntent().getStringExtra("STUDENT");
        String father_name = getIntent().getStringExtra("FATHER");
        String roll_number = getIntent().getStringExtra("ROLL");
        String class_in = getIntent().getStringExtra("CLASSIN");
        String sem_in = getIntent().getStringExtra("SEMIN");


        signUpProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = genderRadio.getCheckedRadioButtonId();
                genderButton = findViewById(selectedId);

                String mobile_Number = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

                String gender = genderButton.getText().toString();
                if (gender.equals(null) || gender.equals("")) {
                    Toast.makeText(NextSignUpActivity.this, "Please select gender.", Toast.LENGTH_SHORT).show();
                    return;
                }


                String day = String.valueOf(dobPicker.getDayOfMonth());
                String month = String.valueOf(dobPicker.getMonth());
                String year = String.valueOf(dobPicker.getYear());

                String birth_Date = day + "/" + month + "/" + year;
                Boolean verified = false;


                com.aakash.bpibs.ModelClass.StudentModel studentModel = new com.aakash.bpibs.ModelClass.StudentModel(
                        mobile_Number,
                        student_name,
                        father_name,
                        roll_number,
                        class_in,
                        sem_in,
                        gender,
                        birth_Date,
                        null,
                        verified
                );

                FirebaseDatabase.getInstance().getReference("Student").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .setValue(studentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(NextSignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NextSignUpActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NextSignUpActivity.this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
}