package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.schultzoss_montpellier.melvin.fivehock.Tasks.RetrieveImageTask;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.ceil;

public class UserAccountActivity extends AppCompatActivity {
    Context mContext = this;
    FirebaseStorage mStorage = FirebaseStorage.getInstance();
    StorageReference storageRef = mStorage.getReferenceFromUrl("gs://fivehock-7caab.appspot.com");
    StorageReference imagesRef = storageRef.child("images");

    String defaultImg = "44aS3pW.jpg";

    FirebaseDatabase database = FirebaseDatabase.getInstance(); // Get database instance
    DatabaseReference myRef = database.getReference(); // Get database reference
    DatabaseReference mUsersRef = myRef.child("users"); // Get users reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        final TextView textViewWelcomeMessage = (TextView) findViewById(R.id.textViewWelcomeMessage);
        final TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        final TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        final TextView textViewLevel = (TextView) findViewById(R.id.TextViewLevel);
        final ProgressBar horizontal_progress_bar = (ProgressBar) findViewById(R.id.horizontal_progress_bar);
        final Button buttonLogout = (Button) findViewById(R.id.buttonLogout);
        final Button buttonBack = (Button) findViewById(R.id.buttonBackMenu);
        final CircleImageView profile_image = (CircleImageView) findViewById(R.id.profile_image);

        // when a dev click on "Question Page"
        textViewQuestionPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionIntent = new Intent(UserAccountActivity.this, QuestionActivity.class);
                // start the QuestionActivity (show Question Page)
                UserAccountActivity.this.startActivity(questionIntent);
            }
        });

        // when a dev click on "Question Page"
        textViewAddQuestionPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addQuestionIntent = new Intent(UserAccountActivity.this, AddQuestionActivity.class);
                // start the AddQuestionActivity (show Add Question Page)
                UserAccountActivity.this.startActivity(addQuestionIntent);
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
                 int experience = user.xp;
                 if(experience == 0){
                     textViewLevel.setText("Level 1");
                 }else{
                     int level = (int) ceil(Math.round(experience/10)+1);
                     textViewLevel.setText("Level "+ level);
                 }
                 int currentXp = (experience%10)*10;

                 horizontal_progress_bar.setProgress(currentXp);

                 imagesRef.child(user.avatar.equals("")?defaultImg:user.avatar).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         try {
                             // Download was started on main thread which crashed the app,
                             // so a thread dedicated to the download is used
                             Bitmap bmp = new RetrieveImageTask().execute(uri.toString()).get();
                             profile_image.setImageBitmap(bmp);
                         } catch (InterruptedException | ExecutionException e) {
                             e.printStackTrace();
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
