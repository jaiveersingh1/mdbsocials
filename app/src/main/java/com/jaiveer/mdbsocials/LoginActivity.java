package com.jaiveer.mdbsocials;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "BOB tag";
    private FirebaseAuth mAuth;
    private boolean newUser = true;

    private TextView instructionsText;
    private TextView alternateInstructionsText;
    private EditText emailText;
    private EditText passwordText;
    private Button mainBtn;
    private Button alternateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        instructionsText = findViewById(R.id.instructionsText);
        alternateInstructionsText = findViewById(R.id.alternateInstructionsText);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        mainBtn = findViewById(R.id.mainBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOrSignUp(newUser);
            }
        });

        alternateBtn = findViewById(R.id.alternateBtn);
        alternateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser = !newUser;
                switchLoginOrSignUp(newUser);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            newUser = false;
            Toast.makeText(this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    private void switchLoginOrSignUp(boolean newUser) {
        if(newUser) {
            instructionsText.setText(R.string.newUserInstructions);
            alternateInstructionsText.setText(R.string.alternateNewUserInstructions);
            mainBtn.setText(R.string.signup);
            alternateBtn.setText(R.string.login);
        } else {
            instructionsText.setText(R.string.oldUserInstructions);
            alternateInstructionsText.setText(R.string.alternateOldUserInstructions);
            mainBtn.setText(R.string.login);
            alternateBtn.setText(R.string.signup);
        }
    }

    private void loginOrSignUp(boolean newUser) {
        if(!isEmailValid(emailText.getText().toString())) {
            Toast.makeText(this, "Invalid Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordText.getText().toString().length() <= 6) {
            Toast.makeText(this, "Password too short!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(newUser) {
            createAccount(emailText.getText().toString(), passwordText.getText().toString());
        } else {
            signIn(emailText.getText().toString(), passwordText.getText().toString());
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
