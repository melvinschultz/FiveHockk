package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        final TextView textViewWelcomeMessage = (TextView) findViewById(R.id.textViewWelcomeMessage);
        final EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final Button buttonLogout = (Button) findViewById(R.id.buttonLogout);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        String message = "Hello " + username + ", welcome to your user account !";
        textViewWelcomeMessage.setText(message);
        editTextUsername.setText(username);
        editTextEmail.setText(email);

    }
}
