package com.junglist963.quiz_game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Forgot_psw_activity extends AppCompatActivity {

    EditText edtTxtResetPsw;
    Button btnContinue;
    ProgressBar progressBarForgotPsw;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_psw);

        edtTxtResetPsw = findViewById(R.id.edtTxtResetPsw);
        btnContinue=findViewById(R.id.btnContinue);
        progressBarForgotPsw=findViewById(R.id.progressBarForgotPsw);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = edtTxtResetPsw.getText().toString();
                resetPassword(userEmail);
            }
        });
    }
    public void resetPassword(String userEmail){
        progressBarForgotPsw.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(Forgot_psw_activity.this, "Password reset has been send to your Email", Toast.LENGTH_SHORT).show();
                       btnContinue.setClickable(false);
                       progressBarForgotPsw.setVisibility(View.INVISIBLE);
                       finish();
                   }else{
                       Toast.makeText(Forgot_psw_activity.this, "There was a problem", Toast.LENGTH_SHORT).show();
                   }
                    }
                });
    }
}