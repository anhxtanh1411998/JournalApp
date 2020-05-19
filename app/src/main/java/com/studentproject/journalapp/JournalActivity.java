package com.studentproject.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class JournalActivity extends AppCompatActivity {
    private RecyclerView recyclerViewJournal;
    ArrayList<journalshow> listJournal = new ArrayList<>();
    private Toolbar toolbar;
    private ImageView Edit;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    journalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        getReferenceUI();
        addEvent();
        addListJournal();
        showRecycleView();
    }


    private void getReferenceUI() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Edit = findViewById(R.id.img_edit);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_myword);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addListJournal() {
        DatabaseReference myRef = databaseReference.child("journal");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listJournal.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    journalshow journal = data.getValue(journalshow.class);
                    listJournal.add(journal);
                    // Log.w("Ket qua", listJournal.get(0).getTime());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Log.w("Ket qua", listJournal.get(0).getTime());

    }

    public void showRecycleView() {
        recyclerViewJournal = findViewById(R.id.recycle_journal);
        adapter = new journalAdapter(listJournal, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewJournal.setLayoutManager(mLayoutManager);
        recyclerViewJournal.setHasFixedSize(true);
        recyclerViewJournal.setAdapter(adapter);
    }

    private void addEvent() {
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JournalActivity.this, OptionsJournal.class));
            }
        });

    }
}
