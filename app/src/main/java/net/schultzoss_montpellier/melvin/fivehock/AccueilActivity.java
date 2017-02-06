package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        final Button viewStartQuizButton = (Button) findViewById(R.id.ViewStartQuizButton);
        final Button viewAddQuestionButton = (Button) findViewById(R.id.ViewAddQuestionButton);
        final Button viewCorrectQuestionButton = (Button) findViewById(R.id.ViewCorrectQuestionButton);

        // when click on "Start a quiz"
        viewStartQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionIntent = new Intent(AccueilActivity.this, QuestionActivity.class);
                // start the QuestionActivity (show Question Page)
                AccueilActivity.this.startActivity(questionIntent);
            }
        });

        // when click on "Add a question"
        viewAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addQuestionIntent = new Intent(AccueilActivity.this, AddQuestionActivity.class);
                // start the QuestionActivity (show Question Page)
                AccueilActivity.this.startActivity(addQuestionIntent);
            }
        });

        // when click on "Correct a question"
        viewAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //show the user an alert
            Toast.makeText(AccueilActivity.this, "Page non disponible pour le moment", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
