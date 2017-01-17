package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegister;
    //    private Button buttonResetPassword;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLoginMe;

    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Get firabase database instance and set true for offline persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // get reference to users node
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("users");

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
//        buttonResetPassword = (Button) findViewById(R.id.buttonResetPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewLoginMe = (TextView) findViewById(R.id.textViewLoginMe);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        textViewLoginMe = (TextView) findViewById(R.id.textViewLoginMe);

        textViewLoginMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        /*if (firebaseAuth.getCurrentUser() != null) {
            // start the profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
        }*/

        /*buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, ResetPasswordActivity.class));
            }
        });*/

        /*buttonLogin.setOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = editTextUsername.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Please enter an username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmailAndPassword:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {
                                    final String userId = auth.getCurrentUser().getUid();
                                    createNewUser(userId, username, email);

                                    startActivity(new Intent(RegisterActivity.this, UserAccountActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void createNewUser(String userId, String username, String email) {
        User user = new User(username, email);

        mFirebaseDatabase.child(userId).setValue(user);
    }
}