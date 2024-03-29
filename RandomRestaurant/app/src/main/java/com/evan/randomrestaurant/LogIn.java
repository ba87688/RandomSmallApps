package com.evan.randomrestaurant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evan.randomrestaurant.Common.Common;
import com.evan.randomrestaurant.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private Button signIn;
    private EditText userName, password;
    public static final String ERROR = "Error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        signIn = (Button)findViewById(R.id.submit_login);
        userName = (EditText)findViewById(R.id.phoneNumber);
        password = (EditText)findViewById(R.id.password);

        //initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = database.getReference("user");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("")|password.getText().toString().equals("")) {
                    Toast.makeText(LogIn.this, "Input username and password", Toast.LENGTH_SHORT).show();

                } else {

                    user_table.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(ERROR, "onDataChange: " + "it dont exist" + userName.getText().toString());


//                        get user info
                            if (dataSnapshot.child(userName.getText().toString()).exists()) {
                                User user = dataSnapshot.child(userName.getText().toString()).getValue(User.class);

                                if (user.getPassword().equals(password.getText().toString())) {
                                    Log.d(ERROR, "onDataChange: " + user.getPassword().equals(password.getText().toString()));
                                    Log.d(ERROR, "onDataChange: " + userName.getText().toString());
                                    Intent homeIntent = new Intent(LogIn.this, MainNavDrawer.class);
                                    //get the current user
                                    Toast.makeText(LogIn.this, "Sign in successful!", Toast.LENGTH_SHORT).show();

                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    Log.d(ERROR, "onDataChange: " + user.getPassword().equals(password.getText().toString()));
                                    Log.d(ERROR, "onDataChange: " + userName.getText().toString());
                                    Log.d(ERROR, "onDataChange: " + user.getPassword());

                                    Toast.makeText(LogIn.this, "Sign in not successful!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d(ERROR, "onDataChange: " + "it dont exist");

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


    }
}
