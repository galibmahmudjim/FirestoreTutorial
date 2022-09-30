package com.example.firestoretutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText phonenumber;
    private EditText roll;
    private EditText department;
    private EditText hall;
    private ProgressBar signupActivityLoadingProgressBar;
    private Button signupActivitySubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.Name);
        email = (EditText) findViewById(R.id.Email);
        phonenumber = (EditText) findViewById(R.id.PhoneNumber);
        hall = (EditText) findViewById(R.id.Hallname);
        roll = findViewById(R.id.Roll);
        department = findViewById(R.id.Department);
        signupActivitySubmitButton = findViewById(R.id.SignupActivitySubmitButton);
        signupActivityLoadingProgressBar = (ProgressBar) findViewById(R.id.SignupActivityLoadingProgressBar);
        signupActivityLoadingProgressBar.setVisibility(View.GONE);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        signupActivitySubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> user = new HashMap<>();
                user.put("Name", name.getText().toString());
                user.put("Roll", roll.getText().toString());
                user.put("Department", department.getText().toString());
                user.put("Phone Number", phonenumber.getText().toString());
                user.put("Email", email.getText().toString());
                user.put("Hall", hall.getText().toString());
                firebaseFirestore.collection("User").document(email.getText().toString()).set(user);
            }
        });
    }
}