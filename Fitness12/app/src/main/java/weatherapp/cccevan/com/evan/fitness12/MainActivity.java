package weatherapp.cccevan.com.evan.fitness12;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail,textInputPassword;
    private Button logInButton,signupButton;
    private static final Pattern PASSWORDS_PATTERNS = Pattern.compile(".{5,}");
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        textInputEmail = (TextInputLayout) findViewById(R.id.text_input_email);
        textInputPassword = (TextInputLayout) findViewById(R.id.text_input_password);
        //when user clicks login button, confirm his username and password
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUserInput();
            }
        });
        //when user clicks submit button, take them to the sign up page
        findViewById(R.id.button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });



    }

    private void confirmUserInput() {
        //check if all, username, email and password are valid. if not, show error.
        boolean validInputs = ( !validatePassword() | !ValidateEmail());
        Log.d("OOO", "confirmUserInput: "+validInputs);
        if(validInputs){
            Log.d("OOO", "confirmUserInput: "+validInputs);

            return;
        }
        else{
            mAuth.signInWithEmailAndPassword(textInputEmail.getEditText().getText().toString().trim(),
                    textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                                //to avoid going back to login, clear all activities on stack
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public boolean ValidateEmail(){

        //get user input without spaces at end
        String emailSubmitted = textInputEmail.getEditText().getText().toString().trim();

        //if email is not valid, show error message to user
        if(emailSubmitted.isEmpty()){
            textInputEmail.setError("Your email can't be empty.");
            return false;

        }
        //check if email format is correct using preset regexp
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailSubmitted).matches()){
            textInputEmail.setError("Email format is incorrect.");
            return false;
        }
        else {
            //remove error message if input is not empty
            textInputEmail.setError(null);
            return true;
        }

    }
//    public boolean validateUsername(){
//        //if username is not valid, show error message to user
//        String usernameSubmitted = textInputUsername.getEditText().getText().toString().trim();
//        if(usernameSubmitted.isEmpty()){
//            textInputUsername.setError("This can't be empty.");
//            return false;
//        }else {
//            //remove error message if input is not empty
//            textInputEmail.setError(null);
//            return true;
//        }
//    }
    public boolean validatePassword(){
        //if password is not valid, show error message to user
        String usernamePassword = textInputPassword.getEditText().getText().toString().trim();
        if(usernamePassword.isEmpty()){
            textInputPassword.setError("This can't be empty.");
            return false;
        }
        else if(!PASSWORDS_PATTERNS.matcher(usernamePassword).matches()){
            textInputPassword.setError("Has to be over 5 characters.");
            return false;
        }else {
            //remove error message if input is not empty
            textInputPassword.setError(null);
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check if user is already logged into firebase
        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this, UserActivity.class));
        }
    }
}
