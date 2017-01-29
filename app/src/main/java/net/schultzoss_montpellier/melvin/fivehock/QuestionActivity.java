package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class QuestionActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();


        /*Query fetchQuestionByKey = mDatabase.child("questions").orderByKey();
        fetchQuestionByKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        Query fetchQuestions = mDatabase.child("questions").orderByKey();
        fetchQuestions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " questions");

                final List<Question> allQuestions = new ArrayList<>();

                for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                    Question question = questionDataSnapshot.getValue(Question.class);
//                    System.out.println(mDatabase..getKey());
//                    allQuestions.add(question);
                    allQuestions.add(question);

                    // System.out.println(question.getEnonce() + " - " + question.getReponse());
                    // TODO: 22/01/17 -> question.getId() = 0 always, because there is no key for this value in Firebase Database ?? I think it's this... To explore this bug
                }

                buttonSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Random random = new Random();
                        int max = 1;
                        int min = 0;
                        int nombreAleatoire = random.nextInt(max - min + 1) + min;

                        System.out.println(nombreAleatoire);

                        textViewQuestion.setText(allQuestions.get(nombreAleatoire).getEnonce());
                        System.out.println(allQuestions.get(nombreAleatoire).getEnonce());
                    }
                });

                //System.out.println(allQuestions.get(0).getEnonce());

                // supprimer doublons
                /*Set s = new HashSet();
                s.addAll(allQuestions);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println(fetchQuestions);

        /*String question = "Quelle est la capitale de la France ?";
        textViewQuestion.setText(question);

        String[] answers = new String[] { "Paris", "Marseille", "Grenoble", "Nice", "Calais" };
//        String[] answers = { "Paris", "Marseille", "Grenoble", "Nice", "Calais" };
        String goodAnswer = "Paris";

        buttonAnswerOne.setText(answers[0]);
        buttonAnswerTwo.setText(answers[1]);
        buttonAnswerThree.setText(answers[2]);
        buttonAnswerFour.setText(answers[3]);
        buttonAnswerFive.setText(answers[4]);*/
    }
}
