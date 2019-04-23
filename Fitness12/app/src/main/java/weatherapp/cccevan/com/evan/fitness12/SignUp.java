package weatherapp.cccevan.com.evan.fitness12;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final Pattern PASSWORDS_PATTERNS = Pattern.compile(".{5,}");

    private TextInputLayout textSubmitEmail, textSubmitPassword;

    private Button submitSignUp;
    private FirebaseAuth mAuth;
    boolean borat;
    public final static String TAG = "SIGN_UP";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textSubmitEmail = (TextInputLayout) findViewById(R.id.text_signup_email);
        textSubmitPassword = (TextInputLayout) findViewById(R.id.text_signup_password);
        //initialize FirebaseAuth instance...

        mAuth = FirebaseAuth.getInstance();
         borat = false;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        submitSignUp = (Button)findViewById(R.id.submit_sign_up);
        submitSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(borat){

                }
                else{
                    //get user email from editText,
                    //then, validate it
                    String inputEmail = textSubmitEmail.getEditText().getText().toString().trim();
                    //same with password
                    String inputPassword= textSubmitPassword.getEditText().getText().toString().trim();

                    if(!ValidateEmail(inputEmail) | !validatePassword(inputPassword)){

                    }else {
                        createAccount(inputEmail, inputPassword);
                        Intent intent = new Intent(SignUp.this,UserActivity.class);
                        //to avoid going back to login, clear all activities on stack
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
//        currentUser.getEmail();
    }

    public void createAccount(final String email, final String password){
        //call create user method of Firebase
        //the onComplete listener will tell us if sign up was successful
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {


                            //create a user object with user data
                            User newUser = new User(0,email, password,0,false,0);
                            //FirebaseDatabase.getInstance().getReference("fitness12-d213c")
                            FirebaseDatabase.getInstance().getReference()
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUp.this,"Great success",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {

                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Email already registered" ,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }



    public boolean ValidateEmail(String email){


        //if email is not valid, show error message to user
        if(email.isEmpty()){
            textSubmitEmail.setError("Your email can't be empty.");
            return false;

        }//check if email format is correct using preset regexp
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textSubmitEmail.setError("Email format is incorrect.");
            return false;
        }
        else {
            //remove error message if input is not empty
            textSubmitEmail.setError(null);
            return true;
        }



    }
    public boolean validatePassword(String password){
        //if password is not valid, show error message to user
        String usernamePassword = password;
        if(usernamePassword.isEmpty()){
            textSubmitPassword.setError("This can't be empty.");
            return false;
        }
        else if(!PASSWORDS_PATTERNS.matcher(usernamePassword).matches()){
            textSubmitPassword.setError("Has to be over 5 characters.");
            return false;
        }else {
            //remove error message if input is not empty
            textSubmitPassword.setError(null);
            return true;
        }
    }
}
