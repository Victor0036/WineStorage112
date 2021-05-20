package com.example.winestorage112;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winestorage112.ui.AboutUs;
import com.example.winestorage112.ui.MainActivityViewModel;
import com.example.winestorage112.ui.SignInActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    TextView noItemText;
    SimpleDatabase simpleDatabase;
    List<Note> allNotes;
    FloatingActionButton fab;
    FloatingActionButton aboutUs;
    private MainActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        noItemText = findViewById(R.id.noItemText);
        fab = findViewById(R.id.fab);
        aboutUs = findViewById(R.id.aboutUs);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutUs.class));
            }

        });
        simpleDatabase = new SimpleDatabase(this);
        allNotes = simpleDatabase.getAllNotes();
        recyclerView = findViewById(R.id.allNotesList);
        displayList(allNotes);

        if(allNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
        }

    }

    private void displayList(List<Note> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,allNotes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Toast.makeText(this, "Add New Note", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, AddNote.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> getAllNotes = simpleDatabase.getAllNotes();
        if(getAllNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getAllNotes);
        }


    }
    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                String message = "Welcome " + user.getDisplayName();
             //   welcomeMessage.setText(message);
            } else
                startLoginActivity();
        });
    }
    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

}