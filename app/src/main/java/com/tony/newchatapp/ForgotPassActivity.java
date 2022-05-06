package com.tony.newchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    EditText forgotpasstextview;
    ConstraintLayout forgotpassbtn;
    FirebaseAuth resetpassAuth;
    ProgressDialog resetpassDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        forgotpasstextview = findViewById(R.id.edtforgotpassemail);
        forgotpassbtn = findViewById(R.id.txtresetpassbtn);
        resetpassAuth = FirebaseAuth.getInstance();
        resetpassDialog = new ProgressDialog(this);

        forgotpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptResetPassword();
            }
        });

    }

    private void AttemptResetPassword() {
        String resetpassemail = forgotpasstextview.getText().toString();
        if (resetpassemail.isEmpty()){
            Toast.makeText(this, "Please Enter A Valid Email", Toast.LENGTH_SHORT).show();
        }else {
            resetpassDialog.setTitle("Password Reset");
            resetpassDialog.setMessage("Sending Reset Password Email");
            resetpassDialog.setCanceledOnTouchOutside(false);
            resetpassDialog.show();
            resetpassAuth.sendPasswordResetEmail(resetpassemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        resetpassDialog.dismiss();
                        Toast.makeText(ForgotPassActivity.this, "Email Sent! Please Check Your Mail Box!!", Toast.LENGTH_SHORT).show();
                    }else{
                        resetpassDialog.dismiss();
                        Toast.makeText(ForgotPassActivity.this, "Email Not Sent! Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ForgotPassActivity.this, "Email Not Sent! Please Try Again", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}