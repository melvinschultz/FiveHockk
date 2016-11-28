package net.schultzoss_montpellier.melvin.fivehock;

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
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextAge = (EditText) findViewById(R.id.editTextAge);
        final Button buttonLogout = (Button) findViewById(R.id.buttonLogout);

    }
}
