package net.schultzoss_montpellier.melvin.fivehock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity {

    private Button buttonAddQuestion;
    private EditText editTextQuestion;
    private EditText editTextGoodAnswer;
    private ProgressBar progressBar;

    private DatabaseReference questionsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        // Get firabase database instance and set true for offline persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // get reference to users node
        questionsReference = FirebaseDatabase.getInstance().getReference("questions");

        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        editTextQuestion = (EditText) findViewById(R.id.editTextQuestion);
        editTextGoodAnswer = (EditText) findViewById(R.id.editTextGoodAnswer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String enonce = editTextQuestion.getText().toString().trim();
                final String reponse = editTextGoodAnswer.getText().toString().trim();

                if (TextUtils.isEmpty(enonce)) {
                    Toast.makeText(getApplicationContext(), "Please enter a question", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(reponse)) {
                    Toast.makeText(getApplicationContext(), "Please enter a good answer for this question", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                addQuestion(enonce, reponse);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void addQuestion(String enonce, String reponse) {
        Question question = new Question();

        question.setEnonce(enonce);
        question.setReponse(reponse);

        DatabaseReference newQuestion = questionsReference.push();

        newQuestion.setValue(question);
    }
}
