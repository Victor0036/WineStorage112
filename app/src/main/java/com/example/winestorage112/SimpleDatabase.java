package com.example.winestorage112;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SimpleDatabase extends SQLiteOpenHelper {
    // declare require values
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "WineDB";
    private static final String TABLE_NAME = "SimpleTable1";

    public SimpleDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // declare table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_PRODUCER = "producer";
    private static final String KEY_VINTAGE = "vintage";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_GRAPES = "grapes";


    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE "+TABLE_NAME+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_TITLE+" TEXT,"+
                KEY_CONTENT+" TEXT,"+
                KEY_DATE+" TEXT,"+
                KEY_TIME+" TEXT," +
                KEY_PRODUCER+" TEXT," +
                KEY_VINTAGE+" TEXT," +
                KEY_COUNTRY+" TEXT," +
                KEY_GRAPES+" TEXT" +" )";
        db.execSQL(createDb);
    }

    // upgrade db if older version exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_TITLE,note.getTitle());
        v.put(KEY_CONTENT,note.getContent());
        v.put(KEY_DATE,note.getDate());
        v.put(KEY_TIME,note.getTime());
        v.put(KEY_PRODUCER,note.getProducer());
        v.put(KEY_VINTAGE,note.getVintage());
        v.put(KEY_COUNTRY,note.getCountry());
        v.put(KEY_GRAPES,note.getGrapes());

        // inserting data into db
        long ID = db.insert(TABLE_NAME,null,v);
        Log.d("Insert DB", "Id: " + ID);
        return  ID;
    }

    public Note getNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME, KEY_PRODUCER, KEY_VINTAGE, KEY_COUNTRY, KEY_GRAPES};
        Cursor cursor=  db.query(TABLE_NAME,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8));

    }

    public List<Note> getAllNotes(){
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{

                Note note = new Note();
                note.setId(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                note.setProducer(cursor.getString(5));
                note.setVintage(cursor.getString(6));
                note.setCountry(cursor.getString(7));
                note.setGrapes(cursor.getString(8));
                allNotes.add(note);

            }while (cursor.moveToNext());
        }

        return allNotes;

    }

    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getId());
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        c.put(KEY_PRODUCER,note.getProducer());
        c.put(KEY_VINTAGE,note.getVintage());
        c.put(KEY_COUNTRY,note.getCountry());
        c.put(KEY_GRAPES,note.getGrapes());

        return db.update(TABLE_NAME,c,KEY_ID+"=?",new String[]{String.valueOf(note.getId())});
    }



    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }





}