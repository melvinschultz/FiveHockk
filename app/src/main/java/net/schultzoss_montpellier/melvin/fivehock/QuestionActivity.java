package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;

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

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        Query fetchQuestionByKey = mFirebaseDatabase.child("questions").orderByKey();
        fetchQuestionByKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " questions");
//                List questions = new ArrayList<>();
                for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                    Question question = questionDataSnapshot.getValue(Question.class);
                    System.out.println(question.getId() + " - " + question.getIdTheme() + " - " + question.getIdCategorie() + " - " + question.getIdReponse() + " - " + question.getQuestion());
                    // TODO: 22/01/17 -> question.getId() = 0 always, because there is no key for this value in Firebase Database ?? I think it's this... To explore this bug 
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println(fetchQuestionByKey);

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
