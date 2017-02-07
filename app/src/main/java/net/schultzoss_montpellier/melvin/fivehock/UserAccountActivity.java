package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.tasks.OnFailureListener;
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
import de.hdodenhof.circleimageview.CircleImageView;
import static java.lang.Math.ceil;
import android.os.Bundle;
import java.io.File;

public class UserAccountActivity extends AppCompatActivity {
    Context mContext = this;
    FirebaseStorage mStorage = FirebaseStorage.getInstance();
    StorageReference storageRef = mStorage.getReferenceFromUrl("gs://fivehock-7caab.appspot.com");
    StorageReference imagesRef = storageRef.child("images");

    String defaultImg = "44aS3pW.jpg";

    FirebaseDatabase database = FirebaseDatabase.getInstance(); // Get database instance
    DatabaseReference myRef = database.getReference(); // Get database reference
    DatabaseReference mUsersRef = myRef.child("users"); // Get users reference

    private static final int SELECTED_PICTURE = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        final TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        final EditText mailEdit = (EditText) findViewById(R.id.mailEditUserAccount);
        final TextView textViewLevel = (TextView) findViewById(R.id.textViewLevel);
        final TextView textViewExperience = (TextView) findViewById(R.id.TextViewExperience);
        final ProgressBar horizontalProgressBar = (ProgressBar) findViewById(R.id.horizontal_progress_bar);
        
        final CircleImageView profilePicture = (CircleImageView) findViewById(R.id.profile_image);
        
        final ImageView changeAvatar = (ImageView) findViewById(R.id.changeAvatar);
        final Button changeEmail = (Button) findViewById(R.id.changeUserEmail);
        final Button buttonLogout = (Button) findViewById(R.id.buttonLogout);
        final Button buttonBack = (Button) findViewById(R.id.buttonBackMenu);

        FirebaseDatabase database = FirebaseDatabase.getInstance(); // Get database instance
        DatabaseReference myRef = database.getReference(); // Get database reference
        DatabaseReference mUsersRef = myRef.child("users"); // Get users reference

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


        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        changeAvatar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

        mUsersRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 // Get current user UID
                 String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                 // Get current user profile using uid
                 User user = dataSnapshot.child(uID).getValue(User.class);
                 textViewUsername.setText(user.username);

                 mailEdit.setText(user.email);

                 //Level and XP of user
                 int experience = user.xp;
                 if(experience == 0){
                     textViewLevel.setText("Level 1");
                 }else {
                     int level = (int) ceil(Math.round(experience / 10) + 1);
                 }
                 int currentXp = (experience%10)*10;

                 horizontalProgressBar.setProgress(currentXp);
                 textViewExperience.setText("Experience : "+experience%10+" / "+10);

                 imagesRef.child(user.avatar.equals("")?defaultImg:user.avatar).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         try {
                             // Download was started on main thread which crashed the app,
                             // so a thread dedicated to the download is used
                             Bitmap bmp = new RetrieveImageTask().execute(uri.toString()).get();
                             profilePicture.setImageBitmap(bmp);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        ImageView profilePicture = (ImageView) findViewById(R.id.profile_image);

        System.out.println("ON ACTIVITY RESULT ???");
        // TODO Auto-generated method stub
        UserAccountActivity.super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK){
                    System.out.println("VERY GOOD");
                    Uri uri = data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);

                    cursor.close();

                    Bitmap selectedAvatar= BitmapFactory.decodeFile(filePath);
                    Drawable d=new BitmapDrawable(selectedAvatar);

                    System.out.println("PATH : "+filePath);
                    System.out.println("Drawable : "+d);

                    profilePicture.setImageDrawable(d);

                    //EXPORT THE IMAGE TO FIREBASE
                    // Create a storage reference from our app
                    /*
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://<your-bucket-name>");

                    Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                    StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                    uploadTask = riversRef.putFile(file);

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    });
                    */
                }
        }
    }
}
