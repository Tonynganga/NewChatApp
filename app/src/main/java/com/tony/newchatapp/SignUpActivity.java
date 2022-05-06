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

public class SignUpActivity extends AppCompatActivity {
    TextView gotosignin;
    EditText signupemail,signuppass,signupconfirmpass;
    ConstraintLayout signupbtn;
    ProgressDialog signupbar;
    FirebaseAuth signupAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signupemail = findViewById(R.id.edtsigupemail);
        signuppass = findViewById(R.id.edtsignuppass);
        signupconfirmpass = findViewById(R.id.edtsignupconfirmpass);
        signupbtn = findViewById(R.id.txtsignupbtn);
        gotosignin = findViewById(R.id.txtgotosigin);
        signupbar = new ProgressDialog(this);
        signupAuth = FirebaseAuth.getInstance();

        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosignin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(gotosignin);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptSignUp();
            }
        });

    }

    private void AttemptSignUp() {
        String email = signupemail.getText().toString();
        String password = signuppass.getText().toString();
        String confirmpassword = signupconfirmpass.getText().toString();

        if (email.isEmpty() || !email.contains("@gmail.com")){
            showError(signupemail, "Enter A Valid Email");
        }else  if (password.isEmpty() || password.length()<5){
            showError(signuppass, "Password must be 5 chars and above");
        }else if (!confirmpassword.equals(password)){
            showError(signupconfirmpass, "Password Does Not Match!!");
        }else {
            signupbar.setTitle("Sign Up");
            signupbar.setMessage("Please Wait A Moment");
            signupbar.setCanceledOnTouchOutside(false);
            signupbar.show();
            signupAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        signupbar.dismiss();
                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }else {
                        signupbar.dismiss();
                        Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
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