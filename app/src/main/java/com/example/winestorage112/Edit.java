package com.example.winestorage112;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText nTitle,nContent,nProducer, nVintage, nGrapes;
    Calendar c;
    String todaysDate;
    String currentTime;
    long nId;
    Spinner nCountry;
    String countryString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        nId = i.getLongExtra("ID",0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(nId);

        final String title = note.getTitle();
        String content = note.getContent();
        String producer = note.getProducer();
        String vintage = note.getVintage();
        String country = note.getCountry();
        String grapes = note.getGrapes();
        nTitle = findViewById(R.id.noteTitle);
        nContent = findViewById(R.id.noteDetails);
        nProducer = findViewById(R.id.producer);
        nVintage = findViewById(R.id.vintage);
        nCountry = findViewById(R.id.country);
        nGrapes = findViewById(R.id.grapes);

        String[] items = new String[] {
                "Germany", "France", "Spain", "Italy"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        nCountry.setAdapter(adapter);
        nCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryString = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        nTitle.setText(title);
        nContent.setText(content);
        nProducer.setText(producer);
        nVintage.setText(vintage);
        nGrapes.setText(grapes);

        // set current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: "+todaysDate);
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: "+currentTime);
    }


    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            Note note = new Note(nId,nTitle.getText().toString(),nContent.getText().toString(),todaysDate,currentTime,nProducer.getText().toString(),nVintage.getText().toString(),countryString,nGrapes.getText().toString());
            Log.d("EDITED", "edited: before saving id -> " + note.getId());
            SimpleDatabase sDB = new SimpleDatabase(getApplicationContext());
            long id = sDB.editNote(note);
            Log.d("EDITED", "EDIT: id " + id);
            goToMain();
            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }


}