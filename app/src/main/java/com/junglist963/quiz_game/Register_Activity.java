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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register_Activity extends AppCompatActivity {

    EditText edtTxtNewEmail, edtTxtNewPsw;
    Button btnRegister;
    ProgressBar progressBar;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setClickable(false);
                String userEmail = edtTxtNewEmail.getText().toString();
                String userPsw = edtTxtNewPsw.getText().toString();
                signUpFireBase(userEmail,userPsw);
            }
        });
    }

    private void initViews() {
        edtTxtNewEmail=findViewById(R.id.edtTxtNewEmail);
        edtTxtNewPsw=findViewById(R.id.edtTxtNewPsw);
        btnRegister=findViewById(R.id.btnRegister);
        progressBar=findViewById(R.id.progressBar);
    }

    public void signUpFireBase(String userEmail, String userPsw){
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail, userPsw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(Register_Activity.this, "Your Account Created,", Toast.LENGTH_SHORT).show();
                       finish();
                       progressBar.setVisibility(View.INVISIBLE);
                   }else {
                       Toast.makeText(Register_Activity.this, "There was a problem", Toast.LENGTH_SHORT).show();

                   }
                    }
                });
    }
}