package com.tony.newchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView goTosignup, forgotpass;
    EditText loginemail, loginpass;
    FirebaseAuth loginAuth;
    ProgressDialog loginBar;
    ConstraintLayout loginbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goTosignup = findViewById(R.id.txtgotosignup);
        loginbtn = findViewById(R.id.txtloginbtn);
        loginemail = findViewById(R.id.edtloginemail);
        loginpass = findViewById(R.id.edtloginpass);
        loginAuth = FirebaseAuth.getInstance();
        loginBar = new ProgressDialog(this);
        forgotpass = findViewById(R.id.txtforgotpass);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class));
            }
        });


        goTosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosignup = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(gotosignup);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptLogin();
            }
        });
    }

    private void AttemptLogin() {
        String emaillogin = loginemail.getText().toString();
        String passlogin = loginpass.getText().toString();

        if (emaillogin.isEmpty() || !emaillogin.contains("@gmail.com")){
            showError(loginemail, "Enter valid Email");
        }else if (passlogin.isEmpty() || passlogin.length()< 5){
            showError(loginpass, "Enter valid Password");
        }else{
            loginBar.setTitle("Log In");
            loginBar.setMessage("Please Wait A Moment!!");
            loginBar.setCanceledOnTouchOutside(false);
            loginBar.show();
            loginAuth.signInWithEmailAndPassword(emaillogin, passlogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        loginBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }else {
                        loginBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Log In Failed...Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }
    }
    private void showError(EditText field, String text){
        field.setError(text);
        field.requestFocus();
    }
}