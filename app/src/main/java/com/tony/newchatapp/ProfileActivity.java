package com.tony.newchatapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {
    AlertDialog.Builder profilebuilder;
    AlertDialog profiledialog;
    Button editprofile;
    ProgressDialog saveprofileBar;

    EditText profilesavephone, profilesavetell, profilesaveaddress, profilesavecode;
    ConstraintLayout profilesavebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editprofile = findViewById(R.id.editprofilebtn);
        saveprofileBar = new ProgressDialog(this);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateProfileDialog();
            }
        });
    }

    private void CreateProfileDialog() {
        profilebuilder = new AlertDialog.Builder(this);
        View profileView = getLayoutInflater().inflate(R.layout.profile_setup, null);
        profilesavephone  = profileView.findViewById(R.id.edtphoneprofilesave);
        profilesavetell = profileView.findViewById(R.id.edttellprofilesave);
        profilesaveaddress = profileView.findViewById(R.id.edtaddressprofilesave);
        profilesavecode = profileView.findViewById(R.id.edtcodeprofilesave);
        profilesavebtn = profileView.findViewById(R.id.txtprofilesave);
        profilebuilder.setView(profileView);
        profiledialog = profilebuilder.create();
        profiledialog.show();

        profilesavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptProfileSave();
            }
        });

    }

    private void AttemptProfileSave() {
        String profilephone = profilesavephone.getText().toString().trim();
        String profiletell = profilesavetell.getText().toString().trim();
        String profileaddress = profilesaveaddress.getText().toString().trim();
        String profilecode = profilesavecode.getText().toString().trim();

        if (profilephone.isEmpty() || profilephone.length()<10){
            showError(profilesavephone, "Enter Valid Phone Number");
        }else if (profiletell.isEmpty()){
            showError(profilesavetell, "Enter Valid Tell");
        }else if (profileaddress.isEmpty()){
            showError(profilesavecode, "Enter Valid Address");
        }else if (profilecode.isEmpty()){
            showError(profilesavecode, "Enter Valid Postal Code");
        }else{
            saveprofileBar.setTitle("Setup Profile");
            saveprofileBar.setMessage("Please Wait Ab Moment");

        }
    }

    private void showError(EditText field, String text){
        field.setError(text);
        field.requestFocus();
    }
}