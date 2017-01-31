package net.schultzoss_montpellier.melvin.fivehock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        final Button viewStartQuizButton = (Button) findViewById(R.id.ViewStartQuizButton);

        // when click on "Start a quiz"
        viewStartQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionIntent = new Intent(AccueilActivity.this, QuestionActivity.class);
                // start the QuestionActivity (show Question Page)
                AccueilActivity.this.startActivity(questionIntent);
            }
        });
    }
}
