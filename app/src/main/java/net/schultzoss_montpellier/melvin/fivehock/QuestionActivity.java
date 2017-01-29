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
import android.widget.Toast;

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
    int count = 0;
    int categorieId;

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

        /*buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                QuestionActivity.this.startActivity(questionIntent);
            }
        });*/

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final List<Question> allQuestions = new ArrayList<>();
        final List<Reponse> allReponses = new ArrayList<>();
        //final Button[] buttonsArray = new Button[] {buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive};

        //System.out.println(buttonsArray);

//        buttonsArray.add(0, buttonAnswerOne);
//        buttonsArray.add(1, buttonAnswerTwo);
//        buttonsArray.add(2, buttonAnswerThree);
//        buttonsArray.add(3, buttonAnswerFour);
//        buttonsArray.add(4, buttonAnswerFive);
//        System.out.println(buttonsArray.get(0).toString());

        Query fetchQuestions = mDatabase.child("questions").orderByKey();
        fetchQuestions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println("There are " + dataSnapshot.getChildrenCount() + " questions");

                for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                    Question question = questionDataSnapshot.getValue(Question.class);
//                    System.out.println(mDatabase..getKey());
//                    allQuestions.add(question);
                    allQuestions.add(question);

                    // System.out.println(question.getEnonce() + " - " + question.getReponse());
                    // TODO: 22/01/17 -> question.getId() = 0 always, because there is no key for this value in Firebase Database ?? I think it's this... To explore this bug
                }

                randomQuestion();

                buttonSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count += 1;

                        //System.out.println(count);

                        if (count >= 5) {
                            Toast.makeText(QuestionActivity.this, "You have finish this quiz !", Toast.LENGTH_SHORT).show();
                            count = 0;
                        } else {
                            randomQuestion();
                        }
                    }
                });
            }

            private void randomQuestion() {
                Random random1 = new Random();
                int max1 = allQuestions.size() - 1;
                int min1 = 0;
                int nombreAleatoire1 = random1.nextInt(max1 - min1 + 1) + min1;

                //System.out.println(nombreAleatoire1);

                textViewQuestion.setText(allQuestions.get(nombreAleatoire1).getEnonce());

                categorieId = allQuestions.get(nombreAleatoire1).getCategorieId();

                System.out.println(allQuestions.get(nombreAleatoire1).getCategorieId());
//                System.out.println(allQuestions.get(nombreAleatoire1).getEnonce());
/*
                Random random2 = new Random();
                int max2 = allQuestions.size() - 1;
                int min2 = 0;
                int nombreAleatoire2 = random2.nextInt(max2 - min2 + 1) + min2;

                System.out.println(nombreAleatoire2);

                buttonsArray[nombreAleatoire2].setText(allQuestions.get(nombreAleatoire1).getReponse());

                System.out.println(buttonsArray[nombreAleatoire2]);*/
//                System.out.println(allQuestions.get(nombreAleatoire1).getReponse());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query fetchReponses = mDatabase.child("reponses").orderByChild("categorieId");
        fetchReponses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot reponseDataSnapshot : dataSnapshot.getChildren()) {
                    Reponse reponse = reponseDataSnapshot.getValue(Reponse.class);

                    allReponses.add(reponse);
                }

                randomReponse();
            }

            private void randomReponse() {
                Random random1 = new Random();
                int max1 = allReponses.size() - 1;
                int min1 = 0;
                int nombreAleatoire1 = random1.nextInt(max1 - min1 + 1) + min1;

                //System.out.println(nombreAleatoire1);

                for (int i = 0; i < allReponses.size(); i++) {
                    System.out.println(allReponses.get(i).getValeur());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
