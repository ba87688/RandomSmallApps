package com.evan.notepad;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter cursorAdapter;

    private static final int EDITOR_REQUEST_CODE= 1101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //display data
//        //query current notes table from the database, returns cursor object
//        Cursor cursor = getContentResolver().query(NotesProvider.CONTENT_URI, DBHelper.ALL_COLUMNS,
//                null, null,null);

        //where data is coming from
        String[] from = {DBHelper.NOTE_TEXT};
//        for(int i = 0;i<from.length;i++){
//            Log.d("inside loop ",  from[i]);}

        int[] to = {R.id.textViewNote};
        //create a cursor adapter
         cursorAdapter = new SimpleCursorAdapter(this, R.layout.note_list_items,
                null, from, to, 0);

        ListView listView= (ListView)findViewById(R.id.mainListView);
        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri uri = Uri.parse(NotesProvider.CONTENT_URI+"/"+id);
                intent.putExtra(NotesProvider.CONTENT_ITEM_TYPE,uri);
                startActivityForResult(intent,EDITOR_REQUEST_CODE);
            }
        });


        //use this class to manage the loader
        //seperate threads
        getLoaderManager().initLoader(0,null,this);

    }

    private void inserNote(String newNote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NOTE_TEXT, newNote);
        //call my content provider thats registered in the manifest
        //use the constant to id the provider you want to communicate with
        Uri noteUri = getContentResolver().insert(NotesProvider.CONTENT_URI, contentValues);

        Log.d("MainActivity ",  "Note inserted "+noteUri.getLastPathSegment());
    }

    //create a menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle clicks events for menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sample:
                inserData();
                break;
            case R.id.delete_all:
                deleteNotes();
                break;


        }
         return super.onOptionsItemSelected(item);

    }

    private void deleteNotes() {
        //create a dialogue to ask user to confirm if all data is to be deleted
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    //delete all data
                    getContentResolver().delete(NotesProvider.CONTENT_URI,null,null);
                    //restart loader
                    restartLoader();
                    Toast.makeText(MainActivity.this, "The data was deleted", Toast.LENGTH_SHORT).show();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialogListener)
                .setNegativeButton(getString(android.R.string.no),dialogListener)
                .show();
    }

    private void inserData() {
        inserNote("Hey man");
        inserNote("Yo yo yo \n man");
        inserNote("Jack Black was the man. Jack Black was the man. Jack Black was the man. Jack Black was the man." +
                "Jack Black was the man.");

//        each time you change data, loader needs to restart and reread data from back end database.
            restartLoader();
    }

    private void restartLoader(){
        getLoaderManager().restartLoader(0,null, this);


    }

    //loader interface methods


    //called when data is needed from content provider
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,NotesProvider.CONTENT_URI,null, null, null,null);
    }

    //when the data comes back, the onLoadFinished is called...
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    //pass data to cursor adapter
        cursorAdapter.swapCursor(data);
    }
    //this is called when data needs to be wiped out...
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);

    }


    public void openEditor(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivityForResult(intent, EDITOR_REQUEST_CODE);
    }


    //when back or complete is pressed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==EDITOR_REQUEST_CODE && resultCode == RESULT_OK){
            //this will restart loader and refill data from database.
            // needed because new data might be added
            restartLoader();
        }
    }
}
