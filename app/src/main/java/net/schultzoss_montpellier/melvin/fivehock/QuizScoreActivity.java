package net.schultzoss_montpellier.melvin.fivehock;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizScoreActivity extends AppCompatActivity {

    private int userPoints = 0;
    private int userGoodAnswers = 0;
//    private TextView textViewUserPoints;
//    private TextView textViewUserXp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);

        final TextView textViewUserGoodAnswers = (TextView) findViewById(R.id.textViewUserGoodAnswers);
        final TextView textViewUserXp = (TextView) findViewById(R.id.textViewUserXp);

        final ProgressBar circleProgressBar = (ProgressBar) findViewById(R.id.circle_progress_bar);


        final Button buttonBack = (Button) findViewById(R.id.buttonBackMenu);

        // back to menu
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accueilActivity = new Intent(QuizScoreActivity.this, AccueilActivity.class);
                QuizScoreActivity.this.startActivity(accueilActivity);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userPoints = extras.getInt("userPoints");
            userGoodAnswers = extras.getInt("userPoints")/2;
        }
        textViewUserGoodAnswers.setText(Integer.toString(userGoodAnswers));
        textViewUserXp.setText("+ " + Integer.toString(userPoints) + " xp");


        if (userPoints > 0) {
            ObjectAnimator animation = ObjectAnimator.ofInt(circleProgressBar, "progress", 0, userPoints); // see this max value coming back here, we animate towards that value
            animation.setDuration (1000); // 1000 milliseconds
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }

        // Get current user UID
        final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Get the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Get the database URL
        DatabaseReference myRef = database.getReference();
        // reference à l'utilisateur (avec un id précis (uID))
        final DatabaseReference mUserRef = myRef.child("users").child(uID);

        // Listener pour le changement d'une valeur (en loccurrence l'xp de l'user dans notre cas)
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // récupère le schema de classe User pour pouvoir getter les données de l'utilisateur
                User user = dataSnapshot.getValue(User.class);

                // récupère l'xp de l'utilisateur avant update
                int userCurrentXp = user.getXp();
                // calcule de la nouvelle xp
                int userNewXp = userCurrentXp + userPoints;

                // appelle la fonction qui se charge de changer la valeur en bdd
                updateUserXp(mUserRef, userNewXp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // grace à la reference bdd, on set la valeur du champ xp
    private void updateUserXp(DatabaseReference mUserRef, int userNewXp) {
        mUserRef.child("xp").setValue(userNewXp);
    }
}
