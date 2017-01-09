package net.schultzoss_montpellier.melvin.fivehock;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegisterHere;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            // start the UserAccountActivity here
            finish();
            startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewRegisterHere = (TextView) findViewById(R.id.textViewRegisterHere);

        buttonLogin.setOnClickListener(this);
        textViewRegisterHere.setOnClickListener(this);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }

        if (TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }

        // if validations are ok
        // we will first show a progressDialog
        progressDialog.setMessage("Logging User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            // start the profile activity here
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                        }
                        progressDialog.dismiss(); // dismiss the progress bar
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            userLogin();
        }

        if (view == textViewRegisterHere) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
