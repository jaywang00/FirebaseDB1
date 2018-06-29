package com.example.demouicontrol.firebasedb1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test2");

        String[] names = getResources().getStringArray(R.array.names);
        for(int i=0; i< names.length ;i++)
            myRef.child((i+1) + "").setValue(names[i]);
    }
}
