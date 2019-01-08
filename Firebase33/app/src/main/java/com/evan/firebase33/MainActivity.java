package com.evan.firebase33;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //edit texts and button on main page
    EditText editText1, editText2;
    Button submit;

    //a list for the artists
    List<Artist> artistList;

    //firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.submit);
        editText1 = (EditText)findViewById(R.id.addOne);
        editText2 = (EditText)findViewById(R.id.addTwo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseArtist = firebaseDatabase.getReference("Hello");
        databaseArtist.setValue("Hello world");

        //create teh array list that holds the artist objects
        artistList = new ArrayList<>();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });
    }

    private void addArtist() {
        String name = editText1.getText().toString().trim();
        String lastName = editText2.getText().toString().trim();
        Toast.makeText(this, name +" " +lastName, Toast.LENGTH_SHORT).show();

        if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(lastName)){
            String id = databaseArtist.push().getKey();
            final Artist artist = new Artist(id, name, lastName);

            databaseArtist
                    .child(id).setValue(artist);


            //get data from database of firebase

            databaseArtist.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //clean out the aritst list
                    artistList.clear();

                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        Artist artista = data.getValue(Artist.class);

                        artistList.add(artista);
                    }
                    for(Artist art : artistList){
                        TextView textView = new TextView(MainActivity.this);
                        textView.setText(art.getFirstName() + " " +art.getLastName());
                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
                        linearLayout.addView(textView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Toast.makeText(this, "Artist added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Stuff is empty son", Toast.LENGTH_SHORT).show();
            Log.d("Loser", "you failed");
        }
    }
}
