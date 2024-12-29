package com.example.nyannyanquiz;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String difficulty = "";
        int score = getIntent().getIntExtra("score", 0); // Default score is 0 if not found
        difficulty = getIntent().getStringExtra("difficulty");
        int multiplier;
        switch(difficulty)
        {
            case "easy":
                multiplier = 1;
                break;
            case "medium":
                multiplier = 2;
                break;
            case "hard":
                multiplier = 3;
                break;
            default:
                multiplier = 1;
        }
        // Use the score as needed, for example, display it in a TextView
        TextView scoreTextView = findViewById(R.id.resultScore);
        scoreTextView.setText(String.valueOf(score*multiplier));

        TextView correctTextView = findViewById(R.id.correctScore);
        correctTextView.setText(String.valueOf(score));

        TextView incorrectTextView = findViewById(R.id.wrongScore);
        incorrectTextView.setText(String.valueOf(10-score));

        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        Cursor cursor = myDB.readAllData();

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0); // _id
                String username = cursor.getString(1); // user_username

                if (username.equals("Yo")) { // Verifica que es el usuario "Yo"
                    myDB.updateData(id, "Yo", "contraseña123", score*multiplier); // Actualiza el puntaje
                    break;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();



        TextView home = findViewById(R.id.resultTextview);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}