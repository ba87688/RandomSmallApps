package com.evan.notepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {
    private String action;
    private EditText editor;
    private  String noteFilter;
    private String previousString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor= (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(NotesProvider.CONTENT_ITEM_TYPE);

        if(uri==null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.newNote));
        }
        //edit a note
        else{
            action= Intent.ACTION_EDIT;
            //where clause
            //id appended at end.
            noteFilter = DBHelper.NOTE_ID +"=" + uri.getLastPathSegment();
            //get the one row from the database via cursor
            Cursor cursor = getContentResolver().query(uri, DBHelper.ALL_COLUMNS, noteFilter,
                    null, null);

            //get data by moving to the one and only row
            cursor.moveToFirst();
            previousString = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TEXT));
            editor.setText(previousString);
            editor.requestFocus();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //create a menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(action.equals(Intent.ACTION_EDIT)){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.editor_menu, menu);
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            //incase user presses back button on menu
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.delete:
                deleteIt();
                break;


        }
        return true;
    }

    private void deleteIt() {
        getContentResolver().delete(NotesProvider.CONTENT_URI,noteFilter, null);
        Toast.makeText(this, "Note was deleted" , Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        //return main activity
        finish();
    }

    private void finishEditing(){
        //find out what the user types in = step 1
        String newText = editor.getText().toString().trim();
        switch (action){
            case Intent.ACTION_INSERT:
                //if note is blank, cancel it
                if(newText.length()==0){
                    setResult(RESULT_CANCELED);
                }
                else{
                    insertNote(newText);
                }
                break;
            case Intent.ACTION_EDIT:
                if(newText.length()==0){
                    deleteIt();
                }else if(previousString.equals(newText)){
                    //if user didnt make any changes, dont save or do anything to database.
                    setResult(RESULT_CANCELED);
                }else{
                    updateNote(newText);
                }
        }
        finish();
    }
    
    private void updateNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.NOTE_TEXT,noteText);
        //update the one selected row (Filter by noteFilter)
        getContentResolver().update(NotesProvider.CONTENT_URI,values,noteFilter,null);
        Log.d("UPDATING", "updateNote: successful");
        setResult(RESULT_OK);
    }

    public void insertNote(String newNote){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NOTE_TEXT, newNote);
        //call my content provider thats registered in the manifest
        //use the constant to id the provider you want to communicate with
        getContentResolver().insert(NotesProvider.CONTENT_URI, contentValues);

        setResult(RESULT_OK);


    }

    @Override
    public void onBackPressed() {
        finishEditing();
        }


}

