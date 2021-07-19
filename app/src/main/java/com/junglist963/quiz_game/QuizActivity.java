package com.junglist963.quiz_game;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class  QuizActivity extends AppCompatActivity {


    TextView txtTime, txtCorrect, txtWrong, txtQuestion, txtA, txtB, txtC, txtD;
    Button btnFinish, btnNext;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Question");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference databaseReferenceSecond =database.getReference();

    String quizQuestion;
    String quizA;
    String quizB;
    String quizC;
    String quizD;
    String quizAnswer;
    int questionCount;
    int questionNumber = 1;
    String userAnswer;
    int userCorrect = 0;
    int userWrong = 0;

    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME = 25000;
    boolean timerContinue;
    long timeLeft = TOTAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initViews();



    }

    private void initViews() {
        txtTime=findViewById(R.id.textViewTime);
        txtCorrect=findViewById(R.id.textViewCorrectAnswer);
        txtWrong=findViewById(R.id.textViewWrongAnswer);
        txtQuestion=findViewById(R.id.textViewQuestion);
        txtA=findViewById(R.id.textViewA);
        txtB=findViewById(R.id.textViewB);
        txtC=findViewById(R.id.textViewC);
        txtD=findViewById(R.id.textViewD);
        btnFinish=findViewById(R.id.btnFinishGame);
        btnNext=findViewById(R.id.btnNextQuestion);

        game();

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendScore();
                Intent i = new Intent(QuizActivity.this, ScoreActivity.class);
                startActivity(i);
                finish();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                game();

            }
        });
        txtA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "a";
                if(quizAnswer.equals(userAnswer)){
                    txtA.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    txtCorrect.setText("Correct answer:" + userCorrect);
                }else {
                    txtA.setBackgroundColor(Color.RED);
                    userWrong++;
                    txtWrong.setText("Wrong answer:"+ userWrong);
                    findAnswer();
                }
            }
        });
        txtB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "b";
                if(quizAnswer.equals(userAnswer)){
                    txtB.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    txtCorrect.setText("Correct answer:" + userCorrect);
                }else {
                    txtB.setBackgroundColor(Color.RED);
                    userWrong++;
                    txtWrong.setText("Wrong answer:"+ userWrong);
                    findAnswer();
                }
            }
        });
        txtC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "c";
                if(quizAnswer.equals(userAnswer)){
                    txtC.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    txtCorrect.setText("Correct answer:" + userCorrect);
                }else {
                    txtC.setBackgroundColor(Color.RED);
                    userWrong++;
                    txtWrong.setText("Wrong answer:"+ userWrong);
                    findAnswer();
                }
            }
        });
        txtD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "d";
                if(quizAnswer.equals(userAnswer)){
                    txtD.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    txtCorrect.setText("Correct answer:" + userCorrect);
                }else {
                    txtD.setBackgroundColor(Color.RED);
                    userWrong++;
                    txtWrong.setText("Wrong answer:"+ userWrong);
                    findAnswer();
                }
            }
        });

    }

    public void game(){

        startTimer();
        txtA.setBackgroundColor(Color.WHITE);
        txtB.setBackgroundColor(Color.WHITE);
        txtC.setBackgroundColor(Color.WHITE);
        txtD.setBackgroundColor(Color.WHITE);
        databaseReference.addValueEventListener (new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                questionCount=(int)dataSnapshot.getChildrenCount();

                quizQuestion = dataSnapshot.child(String.valueOf(questionNumber)).child("q").getValue().toString();
                quizA = dataSnapshot.child(String.valueOf(questionNumber)).child("a").getValue().toString();
                quizB = dataSnapshot.child(String.valueOf(questionNumber)).child("b").getValue().toString();
                quizC = dataSnapshot.child(String.valueOf(questionNumber)).child("c").getValue().toString();
                quizD = dataSnapshot.child(String.valueOf(questionNumber)).child("d").getValue().toString();
                quizAnswer = dataSnapshot.child(String.valueOf(questionNumber)).child("answer").getValue().toString();

                txtQuestion.setText(quizQuestion);
                txtA.setText(quizA);
                txtB.setText(quizB);
                txtC.setText(quizC);
                txtD.setText(quizD);


                if(questionNumber < questionCount){
                    questionNumber++;
                }else{
                    Toast.makeText(QuizActivity.this, "You answered all questions!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(QuizActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void findAnswer(){
        if(quizAnswer.equals("a")){
            txtA.setBackgroundColor(Color.GREEN);
        }else if (quizAnswer.equals("b")){
            txtB.setBackgroundColor(Color.GREEN);
        }else if (quizAnswer.equals("c")) {
            txtC.setBackgroundColor(Color.GREEN);
        }else if (quizAnswer.equals("d")) {
            txtD.setBackgroundColor(Color.GREEN);
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timerContinue = false;
                pauseTimer();
                txtQuestion.setText("TIME IS UP");
            }
        }.start();
        timerContinue = true;
    }
    public void resetTimer(){
        timeLeft=TOTAL_TIME;
        updateCountDownText();
    }
    public void updateCountDownText(){
        int second = (int) (timeLeft /1000)% 60;
        txtTime.setText("Time:"+ second);
    }
    public void pauseTimer(){
        countDownTimer.cancel();
        timerContinue = false;
    }
    public void sendScore(){
        String userUID = user.getUid();
        databaseReferenceSecond.child("score").child(userUID).child("correct").setValue(userCorrect)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(QuizActivity.this, "Score send successfully", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferenceSecond.child("score").child(userUID).child("wrong").setValue(userWrong);
    }

}