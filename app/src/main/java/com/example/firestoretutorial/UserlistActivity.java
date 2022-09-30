package com.example.firestoretutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

class profile {
    String name;
    String Department;
    String Phone;
    String Hall;
    String Roll;
    String Email;

    public profile(String name, String Department, String Phone, String Hall, String Roll, String Email)
    {
        this.Department=Department;
        this.Email=Email;
        this.Roll=Roll;
        this.Hall=Hall;
        this.name=name;
        this.Phone=Phone;
    }
}

public class UserlistActivity extends AppCompatActivity {

    private static final String TAG = "Tutorial_check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        ListView listView = findViewById(R.id.listview);
        List<profile> list = new ArrayList<>();
        ArrayAdapter<profile> arrayAdapter = new ArrayAdapter<profile>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        firebaseFirestore.collection("User")
                .whereArrayContains("Hall", "Fazlul Haque Hall")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                });
        firebaseFirestore.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.get("Email"));
                                arrayAdapter.add(new profile(document.get("Name").toString(),document.get("Department").toString(),document.get("Phone").toString(),document.get("Hall").toString(),document.get("Roll").toString(),document.get("Email").toString()));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}