package com.aakash.bpibs.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aakash.bpibs.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    private Button nextButton;
    private TextView titleText;
    private TextInputLayout roll, name, father;
    private Spinner classSpinner, semSpinner;
    private ImageView bpibsLogo, backImage;
    private View viewLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nextButton = findViewById(R.id.next_signup);
        titleText = findViewById(R.id.title_text);
        roll = findViewById(R.id.roll_input);
        name = findViewById(R.id.name_input);
        father = findViewById(R.id.father_input);
        classSpinner = findViewById(R.id.class_spinner);
        semSpinner = findViewById(R.id.sem_spinner);
        bpibsLogo = findViewById(R.id.bpibs_logo_signup);
        backImage = findViewById(R.id.back_white_signup);
        viewLine = findViewById(R.id.line_view);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mobile_Number = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                String student_Name = name.getEditText().getText().toString().trim();
                String father_Name = father.getEditText().getText().toString().trim();
                String roll_Number = roll.getEditText().getText().toString().trim();
                String class_In = classSpinner.getSelectedItem().toString().trim();
                String sem_In = semSpinner.getSelectedItem().toString().trim();


                name.setError(null);
                roll.setError(null);
                father.setError(null);

//                String day = String.valueOf(birthDate.getDayOfMonth());
//                String month = String.valueOf(birthDate.getMonth());
//                String year = String.valueOf(birthDate.getYear());

//                birth_Date = day + "/" + month + "/" + year;
//                verified = false;


                if (TextUtils.isEmpty(student_Name)) {
                    name.setError("Please enter student's name");
                    name.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(father_Name)) {
                    father.setError("Please enter father's name");
                    father.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(roll_Number) || roll_Number.length() < 11) {
                    roll.setError("Please enter a valid Roll Number");
                    roll.requestFocus();
                    return;
                }

                if (class_In.equalsIgnoreCase("Select Class")) {
                    Toast.makeText(SignUpActivity.this, "Please select your class", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sem_In.equalsIgnoreCase("Select Semester")) {
                    Toast.makeText(SignUpActivity.this, "Please select your semester", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(SignUpActivity.this, NextSignUpActivity.class);
                intent.putExtra("STUDENT", student_Name);
                intent.putExtra("FATHER", father_Name);
                intent.putExtra("ROLL", roll_Number);
                intent.putExtra("CLASSIN", class_In);
                intent.putExtra("SEMIN", sem_In);
                //Add Transition

                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(nextButton, "transition_next_button");
                pairs[1] = new Pair<View, String>(titleText, "transition_title_text");
                pairs[2] = new Pair<View, String>(bpibsLogo, "transition_logo");
                pairs[3] = new Pair<View, String>(backImage, "transition_image");
                pairs[4] = new Pair<View, String>(viewLine, "transition_line");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);

                }


            }
        });
    }
}
