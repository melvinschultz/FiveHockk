package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.ceil;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        final Button viewStartQuizButton = (Button) findViewById(R.id.ViewStartQuizButton);
        final Button viewAddQuestionButton = (Button) findViewById(R.id.ViewAddQuestionButton);
        final Button viewCorrectQuestionButton = (Button) findViewById(R.id.ViewCorrectQuestionButton);
        final TextView textViewLevel = (TextView) findViewById(R.id.TextViewLevel);
        final ProgressBar horizontalProgressBar = (ProgressBar) findViewById(R.id.horizontal_progress_bar);
        final CircleImageView profilePicture = (CircleImageView) findViewById(R.id.profile_image);

        FirebaseDatabase database = FirebaseDatabase.getInstance(); // Get database instance
        DatabaseReference myRef = database.getReference(); // Get database reference
        DatabaseReference mUsersRef = myRef.child("users"); // Get users reference

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
        viewCorrectQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //show the user an alert
            Toast.makeText(AccueilActivity.this, "Page non disponible pour le moment", Toast.LENGTH_SHORT).show();
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent userAccountIntent = new Intent(AccueilActivity.this, UserAccountActivity.class);
                // start the QuestionActivity (show Question Page)
                AccueilActivity.this.startActivity(userAccountIntent);
            }
        });


        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get current user UID
                String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Get current user profile using uid
                User user = dataSnapshot.child(uID).getValue(User.class);
                int experience = user.xp;
                if(experience == 0){
                    textViewLevel.setText("Level 1");
                }else{
                    int level = (int) ceil(Math.round(experience/10)+1);
                    textViewLevel.setText("Level "+level);
                }
                int currentXp = (experience%10)*10;

                horizontalProgressBar.setProgress(currentXp);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
