package net.schultzoss_montpellier.melvin.fivehock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuizScoreActivity extends AppCompatActivity {

    private int userPoints = 0;
    private TextView textViewUserPoints;
    private TextView textViewUserXp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);

        final TextView textViewUserPoints = (TextView) findViewById(R.id.textViewUserPoints);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userPoints = extras.getInt("userPoints");
        }

        System.out.println(userPoints);
        textViewUserPoints.setText(Integer.toString(userPoints));

    }
}
