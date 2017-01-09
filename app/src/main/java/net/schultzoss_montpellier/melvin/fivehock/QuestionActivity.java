package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final TextView textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
        final Button buttonAnswerOne = (Button) findViewById(R.id.buttonAnswerOne);
        final Button buttonAnswerTwo = (Button) findViewById(R.id.buttonAnswerTwo);
        final Button buttonAnswerThree = (Button) findViewById(R.id.buttonAnswerThree);
        final Button buttonAnswerFour = (Button) findViewById(R.id.buttonAnswerFour);
        final Button buttonAnswerFive = (Button) findViewById(R.id.buttonAnswerFive);
        final Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        final Button buttonSkip = (Button) findViewById(R.id.buttonSkip);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userAccountIntent = new Intent(QuestionActivity.this, UserAccountActivity.class);
                QuestionActivity.this.startActivity(userAccountIntent);
            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                QuestionActivity.this.startActivity(questionIntent);
            }
        });

        String question = "Quelle est la capitale de la France ?";
        textViewQuestion.setText(question);

        String[] answers = new String[] { "Paris", "Marseille", "Grenoble", "Nice", "Calais" };
//        String[] answers = { "Paris", "Marseille", "Grenoble", "Nice", "Calais" };
        String goodAnswer = "Paris";

        buttonAnswerOne.setText(answers[0]);
        buttonAnswerTwo.setText(answers[1]);
        buttonAnswerThree.setText(answers[2]);
        buttonAnswerFour.setText(answers[3]);
        buttonAnswerFive.setText(answers[4]);

        
    }
}
