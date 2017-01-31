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

    int count = 0;
    List<Question> allQuestions = new ArrayList<>();
    String bonneReponse;
    String reponseA;
    String reponseB;
    String reponseC;
    String reponseD;
    String reponseE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        final Button buttonSkip = (Button) findViewById(R.id.buttonSkip);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userAccountIntent = new Intent(QuestionActivity.this, UserAccountActivity.class);
                QuestionActivity.this.startActivity(userAccountIntent);
            }
        });

        fetchAllQuestions();

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;

                if (count >= 5) {
                    Toast.makeText(QuestionActivity.this, "You have finish this quiz !", Toast.LENGTH_SHORT).show();
                    count = 0;
                } else {
                    fetchAllQuestions();
                }
            }
        });
    }

    protected void fetchAllQuestions() {
        System.out.println(bonneReponse);

        final TextView textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
        final Button buttonAnswerOne = (Button) findViewById(R.id.buttonAnswerOne);
        final Button buttonAnswerTwo = (Button) findViewById(R.id.buttonAnswerTwo);
        final Button buttonAnswerThree = (Button) findViewById(R.id.buttonAnswerThree);
        final Button buttonAnswerFour = (Button) findViewById(R.id.buttonAnswerFour);
        final Button buttonAnswerFive = (Button) findViewById(R.id.buttonAnswerFive);

        // Get the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Get the database URL
        DatabaseReference myRef = database.getReference();
        System.out.println(myRef);

        // Get the questions URL
        DatabaseReference mQuestionsRef = myRef.child("questions");
        System.out.println(mQuestionsRef);

        // Get all questions datas
        mQuestionsRef.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                    String questionDataSnapshotKey = questionDataSnapshot.getKey();
                    Question question = questionDataSnapshot.getValue(Question.class);

                    allQuestions.add(question);
                }

                // Get a random number in order to get question randomly
                Random random1 = new Random();
                int max1 = allQuestions.size() - 1;
                int min1 = 0;
                int nombreAleatoire1 = random1.nextInt(max1 - min1 + 1) + min1;

                textViewQuestion.setText(allQuestions.get(nombreAleatoire1).getEnonce());
                bonneReponse = allQuestions.get(nombreAleatoire1).getBonneReponse().intern();
                buttonAnswerOne.setText(allQuestions.get(nombreAleatoire1).getReponseA());
                buttonAnswerTwo.setText(allQuestions.get(nombreAleatoire1).getReponseB());
                buttonAnswerThree.setText(allQuestions.get(nombreAleatoire1).getReponseC());
                buttonAnswerFour.setText(allQuestions.get(nombreAleatoire1).getReponseD());
                buttonAnswerFive.setText(allQuestions.get(nombreAleatoire1).getReponseE());

                buttonAnswerOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(buttonAnswerOne.getText());

                        reponseA = buttonAnswerOne.getText().toString().intern();

                        if (reponseA == bonneReponse) {
                            System.out.println(reponseA + ": VRAI !");
                            Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println(reponseA + ": FAUX !");
                            Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buttonAnswerTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(buttonAnswerTwo.getText());

                        reponseB = buttonAnswerTwo.getText().toString().intern();

                        if (reponseB == bonneReponse) {
                            System.out.println(reponseB + ": VRAI !");
                            Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println(reponseB + ": FAUX !");
                            Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buttonAnswerThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(buttonAnswerThree.getText());

                        reponseC = buttonAnswerThree.getText().toString().intern();

                        if (reponseC == bonneReponse) {
                            System.out.println(reponseC + ": VRAI !");
                            Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println(reponseC + ": FAUX !");
                            Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buttonAnswerFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(buttonAnswerFour.getText());

                        reponseD = buttonAnswerFour.getText().toString().intern();

                        if (reponseD == bonneReponse) {
                            System.out.println(reponseD + ": VRAI !");
                            Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println(reponseD + ": FAUX !");
                            Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buttonAnswerFive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(buttonAnswerFive.getText());

                        reponseE = buttonAnswerFive.getText().toString().intern();

                        if (reponseE == bonneReponse) {
                            System.out.println(reponseE + ": VRAI !");
                            Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println(reponseE + ": FAUX !");
                            Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
