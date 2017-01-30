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

//    private FirebaseDatabase database;
//    private DatabaseReference mRef;
    int count = 0;
    String categorieQuestion;
    List<Question> allQuestions = new ArrayList<>();
    List<Reponse> allReponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

//        final TextView textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
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

        fetchAllQuestions();
    }

        /*buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                QuestionActivity.this.startActivity(questionIntent);
            }
        });*/

        /*buttonSkip.setOnClickListener(new View.OnClickListener() {
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
        });*/


        //final Button[] buttonsArray = new Button[] {buttonAnswerOne, buttonAnswerTwo, buttonAnswerThree, buttonAnswerFour, buttonAnswerFive};

        //System.out.println(buttonsArray);

//        buttonsArray.add(0, buttonAnswerOne);
//        buttonsArray.add(1, buttonAnswerTwo);
//        buttonsArray.add(2, buttonAnswerThree);
//        buttonsArray.add(3, buttonAnswerFour);
//        buttonsArray.add(4, buttonAnswerFive);
//        System.out.println(buttonsArray.get(0).toString());

    protected void fetchAllQuestions() {
        final TextView textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);

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

                // Println datas in questions node
//                System.out.println(dataSnapshot);

                for (DataSnapshot questionDataSnapshot : dataSnapshot.getChildren()) {
                    String questionDataSnapshotKey = questionDataSnapshot.getKey();
                    Question question = questionDataSnapshot.getValue(Question.class);

//                    System.out.println(questionDataSnapshotKey);
//                    System.out.println(question);

                    allQuestions.add(question);
                }

                // Get a random number in order to get question randomly
                Random random1 = new Random();
                int max1 = allQuestions.size() - 1;
                int min1 = 0;
                int nombreAleatoire1 = random1.nextInt(max1 - min1 + 1) + min1;

                textViewQuestion.setText(allQuestions.get(nombreAleatoire1).getEnonce());

                categorieQuestion = allQuestions.get(nombreAleatoire1).getCategorie();

                fetchReponsesByCategorie(categorieQuestion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void fetchReponsesByCategorie(String categorieQuestion) {
        System.out.println(categorieQuestion);

        // Get the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Get the database URL
        DatabaseReference myRef = database.getReference();
        System.out.println(myRef);

        // Get the reponses URL
        DatabaseReference mReponsesRef = myRef.child("reponses");
        System.out.println(mReponsesRef);

        // Get all questions datas
        mReponsesRef.orderByChild(categorieQuestion).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    System.out.println(dataSnapshot);

                    // Println datas in questions node
                    System.out.println(dataSnapshot.getChildrenCount());

                    for (DataSnapshot reponseDataSnapshot : dataSnapshot.getChildren()) {
                        String reponseDataSnapshotKey = reponseDataSnapshot.getKey();
                        Reponse reponse = reponseDataSnapshot.getValue(Reponse.class);

//                    System.out.println(reponseDataSnapshotKey);
//                    System.out.println(reponse);

                        allReponses.add(reponse);
                    }


                    /*System.out.println(allReponses.get(0).getValeur());
                    System.out.println(allReponses.get(1));
                    System.out.println(allReponses.get(2));
                    System.out.println(allReponses.get(3));
                    System.out.println(allReponses.get(4));*/
                } catch (Throwable e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
