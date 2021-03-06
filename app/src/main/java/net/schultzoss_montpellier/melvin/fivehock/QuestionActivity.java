package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.schultzoss_montpellier.melvin.fivehock.R.color.colorFalseAnswer;
import static net.schultzoss_montpellier.melvin.fivehock.R.color.colorFivehockPrimary;
import static net.schultzoss_montpellier.melvin.fivehock.R.color.colorGoodAnswer;

public class QuestionActivity extends AppCompatActivity {

    int count = 0;
    int userPoints = 0;
    boolean goodAnswer = false;
    String currentQuestion;
    List<String> alreadyAsked = new ArrayList<>();
    List<Question> allQuestions = new ArrayList<>();
    String bonneReponse;
    String reponseA;
    String reponseB;
    String reponseC;
    String reponseD;
    String reponseE;

    private Handler mHandler = new Handler(); // créé pour le timer

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
//        final Button buttonSkip = (Button) findViewById(R.id.buttonSkip);

        buttonCancel.setEnabled(true);
//        buttonSkip.setEnabled(true);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userAccountIntent = new Intent(QuestionActivity.this, UserAccountActivity.class);
                QuestionActivity.this.startActivity(userAccountIntent);
            }
        });

        /*buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToNextQuestion(goodAnswer, buttonCancel);
            }
        });*/

        fetchAllQuestions(buttonCancel);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void fetchAllQuestions(final Button buttonCancel) {
        goodAnswer = false;

        final TextView textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
        final Button buttonAnswerOne = (Button) findViewById(R.id.buttonAnswerOne);
        final Button buttonAnswerTwo = (Button) findViewById(R.id.buttonAnswerTwo);
        final Button buttonAnswerThree = (Button) findViewById(R.id.buttonAnswerThree);
        final Button buttonAnswerFour = (Button) findViewById(R.id.buttonAnswerFour);
        final Button buttonAnswerFive = (Button) findViewById(R.id.buttonAnswerFive);

        buttonAnswerOne.setEnabled(true);
        buttonAnswerTwo.setEnabled(true);
        buttonAnswerThree.setEnabled(true);
        buttonAnswerFour.setEnabled(true);
        buttonAnswerFive.setEnabled(true);

        buttonAnswerOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFivehockPrimary)));
        buttonAnswerTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFivehockPrimary)));
        buttonAnswerThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFivehockPrimary)));
        buttonAnswerFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFivehockPrimary)));
        buttonAnswerFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFivehockPrimary)));

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

                displayQuestion(allQuestions, textViewQuestion, buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void displayQuestion(List<Question> allQuestions, TextView textViewQuestion, Button buttonCancel, Button buttonAnswerOne, Button buttonAnswerTwo, Button buttonAnswerThree, Button buttonAnswerFour, Button buttonAnswerFive) {
        // Get a random number in order to get question randomly
        Random random1 = new Random();
        int max1 = allQuestions.size() - 1;
        int min1 = 0;
        int nombreAleatoire1 = random1.nextInt(max1 - min1 + 1) + min1;

        currentQuestion = allQuestions.get(nombreAleatoire1).getId();
        System.out.println(currentQuestion);

        if (alreadyAsked.contains(currentQuestion)) {
            System.out.println("Question déjà posée");
            displayQuestion(allQuestions, textViewQuestion, buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
        } else {
            alreadyAsked.add(currentQuestion);
            System.out.println(alreadyAsked);

            textViewQuestion.setText(allQuestions.get(nombreAleatoire1).getEnonce());
            bonneReponse = allQuestions.get(nombreAleatoire1).getBonneReponse().intern();
            buttonAnswerOne.setText(allQuestions.get(nombreAleatoire1).getReponseA());
            buttonAnswerTwo.setText(allQuestions.get(nombreAleatoire1).getReponseB());
            buttonAnswerThree.setText(allQuestions.get(nombreAleatoire1).getReponseC());
            buttonAnswerFour.setText(allQuestions.get(nombreAleatoire1).getReponseD());
            buttonAnswerFive.setText(allQuestions.get(nombreAleatoire1).getReponseE());
        }

        questionButtonsGlobalListener(bonneReponse, buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
    }

    protected void questionButtonsGlobalListener(final String bonneReponse, final Button buttonCancel, final Button buttonAnswerOne, final Button buttonAnswerTwo, final Button buttonAnswerThree, final Button buttonAnswerFour, final Button buttonAnswerFive) {
        buttonAnswerOne.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                System.out.println(buttonAnswerOne.getText());

                reponseA = buttonAnswerOne.getText().toString().intern();

                if (reponseA == bonneReponse) {
                    System.out.println(reponseA + ": VRAI !");
//                    Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorGoodAnswer)));

                    goodAnswer = true;
//                            System.out.println(userPoints + " points");

                } else {
                    System.out.println(reponseA + ": FAUX !");
//                    Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerOne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFalseAnswer)));

//                            System.out.println(userPoints + " points");
                }

                disabledButtons(buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
                skipToNextQuestion(goodAnswer, buttonCancel);
            }
        });

        buttonAnswerTwo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                System.out.println(buttonAnswerTwo.getText());

                reponseB = buttonAnswerTwo.getText().toString().intern();

                if (reponseB == bonneReponse) {
                    System.out.println(reponseB + ": VRAI !");
//                    Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorGoodAnswer)));

                    goodAnswer = true;
//                            System.out.println(userPoints + " points");
                } else {
                    System.out.println(reponseB + ": FAUX !");
//                    Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerTwo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFalseAnswer)));

//                            System.out.println(userPoints + " points");
                }

                disabledButtons(buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
                skipToNextQuestion(goodAnswer, buttonCancel);
            }
        });

        buttonAnswerThree.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                System.out.println(buttonAnswerThree.getText());

                reponseC = buttonAnswerThree.getText().toString().intern();

                if (reponseC == bonneReponse) {
                    System.out.println(reponseC + ": VRAI !");
//                    Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorGoodAnswer)));

                    goodAnswer = true;
//                            System.out.println(userPoints + " points");
                } else {
                    System.out.println(reponseC + ": FAUX !");
//                    Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerThree.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFalseAnswer)));

//                            System.out.println(userPoints + " points");
                }

                disabledButtons(buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
                skipToNextQuestion(goodAnswer, buttonCancel);
            }
        });

        buttonAnswerFour.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                System.out.println(buttonAnswerFour.getText());

                reponseD = buttonAnswerFour.getText().toString().intern();

                if (reponseD == bonneReponse) {
                    System.out.println(reponseD + ": VRAI !");
//                    Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorGoodAnswer)));

                    goodAnswer = true;
//                            System.out.println(userPoints + " points");
                } else {
                    System.out.println(reponseD + ": FAUX !");
//                    Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerFour.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFalseAnswer)));

//                            System.out.println(userPoints + " points");
                }

                disabledButtons(buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
                skipToNextQuestion(goodAnswer, buttonCancel);
            }
        });

        buttonAnswerFive.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                System.out.println(buttonAnswerFive.getText());

                reponseE = buttonAnswerFive.getText().toString().intern();

                if (reponseE == bonneReponse) {
                    System.out.println(reponseE + ": VRAI !");
//                    Toast.makeText(QuestionActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorGoodAnswer)));

                    goodAnswer = true;
//                            System.out.println(userPoints + " points");
                } else {
                    System.out.println(reponseE + ": FAUX !");
//                    Toast.makeText(QuestionActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
                    buttonAnswerFive.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorFalseAnswer)));

//                            System.out.println(userPoints + " points");
                }

                disabledButtons(buttonCancel, buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive);
                skipToNextQuestion(goodAnswer, buttonCancel);
            }
        });
    }

    protected void skipToNextQuestion(final boolean goodAnswer, final Button buttonCancel) {
        count += 1;

        // après 2,5 secondes le code est exécuté (changement de question ou affichage page score selon le nombre de question déjà posée)
        mHandler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                if (goodAnswer) {
                    userPoints += 2;
                    System.out.println(userPoints);
                }

                if (count >= 5) {
//                    Toast.makeText(QuestionActivity.this, "Vous avez fini le quiz, voici votre score : "+userPoints+" points !", Toast.LENGTH_SHORT).show();
                    count = 0;
                    alreadyAsked.clear();

                    // on redirige vers la page de résultat du quiz
                    Intent quizScoreIntent = new Intent(QuestionActivity.this, QuizScoreActivity.class);
                    quizScoreIntent.putExtra("userPoints", userPoints);
                    QuestionActivity.this.startActivity(quizScoreIntent);
                } else {
                    fetchAllQuestions(buttonCancel);
                }
            }
        }, 2500); // 2,5 seconds
    }

    protected void disabledButtons(Button buttonCancel, Button buttonAnswerOne, Button buttonAnswerTwo, Button buttonAnswerThree, Button buttonAnswerFour, Button buttonAnswerFive) {
        buttonCancel.setEnabled(false);
        buttonAnswerOne.setEnabled(false);
        buttonAnswerTwo.setEnabled(false);
        buttonAnswerThree.setEnabled(false);
        buttonAnswerFour.setEnabled(false);
        buttonAnswerFive.setEnabled(false);
    }
}
