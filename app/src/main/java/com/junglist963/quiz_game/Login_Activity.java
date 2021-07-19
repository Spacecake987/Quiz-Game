package com.junglist963.quiz_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

public class Login_Activity extends AppCompatActivity {

    EditText edtTxtEmail, edtTxtPassword;
    TextView txtRegister, textViewForgotPsw;
    Button btnSignIn ;
    SignInButton btnSignInGoogle;
    ProgressBar progressBarLogin;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_Activity.this, com.junglist963.quiz_game.Register_Activity.class);
                startActivity(i);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = edtTxtEmail.getText().toString();
                String userPsw = edtTxtPassword.getText().toString();
                signInWithFireBase(userEmail,userPsw);

            }
        });
        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();

            }
        });
        textViewForgotPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent i = new Intent(Login_Activity.this, com.junglist963.quiz_game.Forgot_psw_activity.class);
                  startActivity(i);
            }
        });
    }

    public void signInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn();
    }

   public void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 ){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            firebaseSignInWithGoogle(task);
    }
    }

    private void firebaseSignInWithGoogle(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login_Activity.this, com.junglist963.quiz_game.MainActivity.class);
            startActivity(i);
            finish();
            assert account != null;
            firebaseGoogleAccount(account);

        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Signed In is not Successful", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseGoogleAccount(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                        }
                    }
                });
    }

    private void initView() {
        edtTxtEmail=findViewById(R.id.editTextEmail);
        edtTxtPassword=findViewById(R.id.editTextPsw);
        txtRegister=findViewById(R.id.textViewRegister);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignInGoogle=findViewById(R.id.btnSignInGoogle);
        progressBarLogin=findViewById(R.id.progressBarLogin);
        textViewForgotPsw=findViewById(R.id.textViewForgotPsw);

    }
    public void signInWithFireBase(String userEmail, String userPsw){
        btnSignIn.setClickable(false);
        progressBarLogin.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(userEmail,userPsw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(Login_Activity.this, com.junglist963.quiz_game.MainActivity.class);
                            startActivity(i);
                            finish();
                            progressBarLogin.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }
                        else {
                            Toast.makeText(Login_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            Intent i = new Intent(Login_Activity.this, com.junglist963.quiz_game.MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}