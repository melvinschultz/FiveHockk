package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        final TextView textViewQuestionPage = (TextView) findViewById(R.id.textViewQuestionPage);

        // when a dev click on "Question Page"
        textViewQuestionPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionIntent = new Intent(UserAccountActivity.this, QuestionActivity.class);
                // start the QuestionActivity (show Question Page)
                UserAccountActivity.this.startActivity(questionIntent);
            }
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        String message = "Hello " + username + ", welcome to your user account !";
        textViewWelcomeMessage.setText(message);
        editTextUsername.setText(username);
        editTextEmail.setText(email);

    }
}
