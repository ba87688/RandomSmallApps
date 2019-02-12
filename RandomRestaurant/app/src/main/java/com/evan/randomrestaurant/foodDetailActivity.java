package com.evan.randomrestaurant;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.evan.randomrestaurant.MainNavDrawer.POSITION;

public class foodDetailActivity extends AppCompatActivity {

    private ImageView imageOfFood;
    private TextView description;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int position;
    private String imageUrl;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //textview and imageview
        imageOfFood = (ImageView)findViewById(R.id.imageViewDetail);
        description = (TextView) findViewById(R.id.textViewDetail);
        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Category");

        //get intent
        position = getIntent().getIntExtra(POSITION,-1);
        name = getIntent().getStringExtra("name");
        imageUrl = getIntent().getStringExtra("image");

        getData();

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

    private void getData() {
        description.setText(name);
        Picasso.get().load(imageUrl).into(imageOfFood);

    }

}
