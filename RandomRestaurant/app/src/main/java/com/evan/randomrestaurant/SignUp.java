package com.evan.randomrestaurant;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evan.randomrestaurant.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    private  EditText fullName, phoneNumber, password;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        fullName = (EditText)findViewById(R.id.fullName);
        phoneNumber = (EditText)findViewById(R.id.phoneNumberSignUp);
        password = (EditText)findViewById(R.id.password);

        submit = (Button)findViewById(R.id.submit_singUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = database.getReference("user");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if the phone number is already in database
                        if(dataSnapshot.child(phoneNumber.getText().toString()).exists()){
                            Toast.makeText(SignUp.this, "This phone number is already in use.", Toast.LENGTH_SHORT).show();

                        }

                        else{
                            if(fullName.getText().toString().equals("") | password.getText().toString().equals("")){
                            Toast.makeText(SignUp.this, "input name and password.", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                //create a new user and add them to the database
                                User newUser = new User(fullName.getText().toString(), password.getText().toString());
//                            add the user to database
                                user_table.child(phoneNumber.getText().toString()).setValue(newUser);
                                Toast.makeText(SignUp.this, "Successfully signed up.", Toast.LENGTH_SHORT).show();

//                            close activity
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
