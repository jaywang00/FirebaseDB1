package com.example.demouicontrol.firebasedb1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> valueList = new ArrayList<>();
    private ArrayList<String> keyList = new ArrayList<>();
    private EditText et;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference myRef;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        et = (EditText) findViewById(R.id.editText);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("test2");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                keyList.add(key);
                valueList.add(value);
                updateAdapter();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("onChildRemoved-dataSnapshot = " + dataSnapshot);
                String key = dataSnapshot.getKey();

                valueList.remove(keyList.indexOf(key));
                keyList.remove(key);
                updateAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("onChildChanged-dataSnapshot = " + dataSnapshot + " , s =" + s);
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                keyList.set(keyList.indexOf(key), key);
                valueList.set(keyList.indexOf(key), value);
                updateAdapter();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onSend(View v) {
        // 快速參考 https://www.firebase.com/docs/android/quickstart.html
        int lastKey = Integer.parseInt(keyList.get(keyList.size() - 1));
        myRef.child((lastKey + 1) + "").setValue(
                et.getText().toString());
    }

    private void updateAdapter() {
        adapter.clear();
        adapter.addAll(valueList);
        adapter.notifyDataSetChanged();
        listView.setSelection(valueList.size() - 1);
    }
}
