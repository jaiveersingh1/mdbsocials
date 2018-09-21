package com.jaiveer.mdbgram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BOB TAG";
    private static final int NEW_SNAP = 2;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    MDBsnapAdapter adapter;

    ArrayList<MDBsnap> mdBsnaps;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdBsnaps = new ArrayList<>();

        mLinearLayoutManager = new LinearLayoutManager(this);
        adapter = new MDBsnapAdapter(mdBsnaps, this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("snaps");
        adapter.notifyDataSetChanged();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mdBsnaps.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    mdBsnaps.add(dataSnapshot1.getValue(MDBsnap.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewSnapActivity.class);
                startActivityForResult(intent, NEW_SNAP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_SNAP) {
            if (resultCode == RESULT_OK) {
                String url = data.getStringExtra("url");
                String caption = data.getStringExtra("caption");
                FirebaseUser currentUser = mAuth.getCurrentUser();
                MDBsnap newSnap = new MDBsnap(caption, currentUser.getEmail(), url);
                myRef.child(myRef.push().getKey()).setValue(newSnap);
            }
        }
    }
}
