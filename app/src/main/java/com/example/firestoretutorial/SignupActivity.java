package com.example.firestoretutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

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

                if(phonenumber.getText().toString().isEmpty())
                {
                    phonenumber.setError("Phone Number can not be empty");
                }

                if(email.getText().toString().isEmpty())
                {
                    email.setError("Empty can not be empty");
                }

                if(roll.getText().toString().isEmpty())
                {
                    roll.setError("Phone Number can not be empty");
                }

                Task T1 = firebaseFirestore.collection("User")
                        .whereEqualTo("Phone", phonenumber.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        phonenumber.setError("Used Phone Number");
                                    }
                                }
                            }
                        });
                Task T2 = firebaseFirestore.collection("User")
                        .whereEqualTo("Email", email.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        email.setError("Used Email");
                                    }
                                }
                            }
                        });
                Task T3 =  firebaseFirestore.collection("User")
                        .whereEqualTo("Roll", roll.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        roll.setError("Roll detected");
                                    }
                                }
                            }
                        });
                Tasks.whenAllSuccess(T1, T2, T3).addOnCompleteListener(new OnCompleteListener<List<Object>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Object>> task) {
                        if(email.getError()==null && roll.getError()==null&&phonenumber.getError()==null)
                        {
                            HashMap<String,String> user = new HashMap<>();
                            user.put("Name",name.getText().toString());
                            user.put("Roll",roll.getText().toString());
                            user.put("Department",department.getText().toString());
                            user.put("Phone",phonenumber.getText().toString());
                            user.put("Email",email.getText().toString());
                            user.put("Hall",hall.getText().toString());
                            firebaseFirestore.collection("User")
                                    .document(email.getText().toString()).set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignupActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

}



