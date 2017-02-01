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
    private EditText editTextOptionA;
    private EditText editTextOptionB;
    private EditText editTextOptionC;
    private EditText editTextOptionD;
    private EditText editTextOptionE;
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
        editTextOptionA = (EditText) findViewById(R.id.editTextOptionA);
        editTextOptionB = (EditText) findViewById(R.id.editTextOptionB);
        editTextOptionC = (EditText) findViewById(R.id.editTextOptionC);
        editTextOptionD = (EditText) findViewById(R.id.editTextOptionD);
        editTextOptionE = (EditText) findViewById(R.id.editTextOptionE);
        editTextGoodAnswer = (EditText) findViewById(R.id.editTextGoodAnswer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String enonce = editTextQuestion.getText().toString().trim();
                final String reponseA = editTextOptionA.getText().toString().trim();
                final String reponseB = editTextOptionB.getText().toString().trim();
                final String reponseC = editTextOptionC.getText().toString().trim();
                final String reponseD = editTextOptionD.getText().toString().trim();
                final String reponseE = editTextOptionE.getText().toString().trim();
                final String bonneReponse = editTextGoodAnswer.getText().toString().trim();

                if (TextUtils.isEmpty(enonce)) {
                    Toast.makeText(getApplicationContext(), "Please enter a question", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(reponseA)) {
                    Toast.makeText(getApplicationContext(), "Please enter a good answer for this question", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(reponseB)) {
                    Toast.makeText(getApplicationContext(), "Please enter a good answer for this question", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(reponseC)) {
                    Toast.makeText(getApplicationContext(), "Please enter a good answer for this question", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(reponseD)) {
                    Toast.makeText(getApplicationContext(), "Please enter a good answer for this question", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(reponseE)) {
                    Toast.makeText(getApplicationContext(), "Please enter a good answer for this question", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(bonneReponse)) {
                    Toast.makeText(getApplicationContext(), "Please enter a good answer for this question", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                addQuestion(enonce, reponseA, reponseB, reponseC, reponseD, reponseE, bonneReponse);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void addQuestion(String enonce, String reponseA, String reponseB, String reponseC, String reponseD, String reponseE, String bonneReponse) {
        Question question = new Question();

        question.setEnonce(enonce);
        question.setReponseA(reponseA);
        question.setReponseB(reponseB);
        question.setReponseC(reponseC);
        question.setReponseD(reponseD);
        question.setReponseE(reponseE);
        question.setBonneReponse(bonneReponse);

        DatabaseReference newQuestion = questionsReference.push();

        newQuestion.setValue(question);

        progressBar.setVisibility(View.GONE);
    }
}
