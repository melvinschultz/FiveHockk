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

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        final TextView textViewWelcomeMessage = (TextView) findViewById(R.id.textViewWelcomeMessage);
        final TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        final TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        final Button buttonLogout = (Button) findViewById(R.id.buttonLogout);
        final Button buttonBack = (Button) findViewById(R.id.buttonBackMenu);

        // when a dev click on "Question Page"
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accueilActivity = new Intent(UserAccountActivity.this, AccueilActivity.class);
                // back to menu
                UserAccountActivity.this.startActivity(accueilActivity);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(UserAccountActivity.this, LoginActivity.class));
                finish();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance(); // Get database instance
        DatabaseReference myRef = database.getReference(); // Get database reference
        DatabaseReference mUsersRef = myRef.child("users"); // Get users reference

        mUsersRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 // Get current user UID
                 String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                 // Get current user profile using uid
                 User user = dataSnapshot.child(uID).getValue(User.class);
                 String message = "Welcome";

                 textViewWelcomeMessage.setText(message);
                 textViewUsername.setText(user.username);
                 textViewEmail.setText(user.email);
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
        });
    }
}
