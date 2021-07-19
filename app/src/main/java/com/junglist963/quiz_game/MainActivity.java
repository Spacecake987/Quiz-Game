package com.junglist963.quiz_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnStartQuiz, btnSignOut;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignOut= findViewById(R.id.btnSignOut);
        btnStartQuiz=findViewById(R.id.btnStartQuiz);

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.junglist963.quiz_game.QuizActivity.class);
                startActivity(intent);

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(i);
                finish();
                Toast.makeText(MainActivity.this, "SignOut Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}