package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizScoreActivity extends AppCompatActivity {

    private int userPoints = 0;
    private TextView textViewUserPoints;
    private TextView textViewUserXp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);

        final TextView textViewUserPoints = (TextView) findViewById(R.id.textViewUserPoints);
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
        }
        textViewUserPoints.setText(Integer.toString(userPoints));

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
