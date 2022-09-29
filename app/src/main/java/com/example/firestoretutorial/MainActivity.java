package com.example.firestoretutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Tutorial_Check";
    //Initialize cloud firestore
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button userlist = findViewById(R.id.userlist);
        CollectionReference collectionReference = firebaseFirestore.collection("User");
        HashMap<String, String> User = new HashMap<>();
        User.put("Name", "Galib Mahmud Jim");
        User.put("Email", "galibmahmudjim@gmail.com");
        User.put("Department", "CSE");
        User.put("Phone", "01837485786");
        User.put("Roll", "62");
        User.put("Hall", "Fazlul Haque Hall");
        collectionReference.document(User.get("Email")).set(User);

        HashMap<String, String> User2 = new HashMap<>();
        User2.put("Name", "Lubna Lamia");
        User2.put("Email", "lubnalamia@gmail.com");
        User2.put("Department", "CSE");
        User2.put("Phone", "0183666666");
        User2.put("Roll", "26");
        User2.put("Hall", "Shamsun Nahar Hall");
        collectionReference.document(User2.get("Email")).set(User2);

        HashMap<String, String> User3 = new HashMap<>();
        User3.put("Name", "Mansif");
        User3.put("Email", "mansif@gmail.com");
        User3.put("Department", "CSE");
        User3.put("Phone", "0183554666");
        User3.put("Roll", "25");
        User3.put("Hall", "Fazlul Haque Hall");
        collectionReference.document(User3.get("Email")).set(User3);

        Query query = collectionReference.whereEqualTo("Roll", "25");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        query = collectionReference.whereEqualTo("Hall", "Fazlul Haque Hall");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.get("Name"));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserlistActivity.class));
            }
        });
    }
}