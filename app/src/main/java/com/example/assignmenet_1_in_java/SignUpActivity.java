package com.example.assignmenet_1_in_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.assignmenet_1_in_java.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    Dialog dialog;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initlizaion();
    }
    void initlizaion(){
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        spinnersetup();
        auth= FirebaseAuth.getInstance();
//        binding.signupButton.setOnClickListener(v->{
//            if(binding.signupEmail.getText().toString().trim().isEmpty()){
//                Snackbar.make(binding.getRoot(),"Please Enter Email",Snackbar.LENGTH_SHORT).show();
//
//            }
//            else if(binding.signupPassword.getText().toString().trim().isEmpty()){
//                Snackbar.make(binding.getRoot(),"Please Enter Password",Snackbar.LENGTH_SHORT).show();
//            }
//            else if(binding.signupUsername.getText().toString().trim().isEmpty()){
//                Snackbar.make(binding.getRoot(),"Please Enter Username",Snackbar.LENGTH_SHORT).show();
//            }
//            else if(binding.signupName.getText().toString().trim().isEmpty()){
//                Snackbar.make(binding.getRoot(),"Please Enter Name",Snackbar.LENGTH_SHORT).show();
//            }
//            else{
//                onSignupPress();
//                database=FirebaseDatabase.getInstance();
//                reference=database.getReference("users");
//                String name=binding.signupName.getText().toString();
//                String email =binding.signupEmail.getText().toString();
//                String username=binding.signupUsername.getText().toString();
//                String password=binding.signupPassword.getText().toString();
//                String age=binding.signupAge.getText().toString();
//                String phone=binding.signupPhoneNumber.getText().toString();
//                String gender=binding.signupGender.getSelectedItem().toString();
//                HelperClass helperClass=new HelperClass(name,email,username,password,age,phone,gender);
//                auth.createUserWithEmailAndPassword(email,password)
//                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if(task.isSuccessful()){
//                                            reference.child(username).setValue(helperClass)
//                                                    .addOnCompleteListener(saveTask -> {
//                                                        if (saveTask.isSuccessful()) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(SignUpActivity.this,"You have signup successfully!",Toast.LENGTH_SHORT).show();
//                                                            Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
//                                                            startActivity(intent);
//                                                        } else {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(SignUpActivity.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    });
//                                        }
//                                        else{
//                                            Toast.makeText(getApplicationContext(),"Account Created failed",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//            }
//        });

        binding.signupButton.setOnClickListener(v->{
            if(binding.signupEmail.getText().toString().trim().isEmpty()){
                Snackbar.make(binding.getRoot(),"Please Enter Email",Snackbar.LENGTH_SHORT).show();

            }
            else if(binding.signupPassword.getText().toString().trim().isEmpty()){
                Snackbar.make(binding.getRoot(),"Please Enter Password",Snackbar.LENGTH_SHORT).show();
            }
            else if(binding.signupUsername.getText().toString().trim().isEmpty()){
                Snackbar.make(binding.getRoot(),"Please Enter Username",Snackbar.LENGTH_SHORT).show();
            }
            else if(binding.signupPassword.getText().toString().length()<6){
                Toast.makeText(this,"Minimum Length for password is 6",Toast.LENGTH_SHORT).show();
            }
            else if(binding.signupName.getText().toString().trim().isEmpty()){
                Snackbar.make(binding.getRoot(),"Please Enter Name",Snackbar.LENGTH_SHORT).show();
            }
            else{
                onSignupPress();
                database=FirebaseDatabase.getInstance();
                reference=database.getReference("users");
                String name=binding.signupName.getText().toString();
                String email =binding.signupEmail.getText().toString();
                String username=binding.signupUsername.getText().toString();
                String password=binding.signupPassword.getText().toString();
                String age=binding.signupAge.getText().toString();
                String phone=binding.signupPhoneNumber.getText().toString();
                String gender=binding.signupGender.getSelectedItem().toString();
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    user = task.getResult().getUser();
                                    Log.e("FIrebaseUser","user:"+user);
                                    Log.e("FIrebaseUser","user:"+user.getUid());
                                    Toast.makeText(SignUpActivity.this, "user:"+user, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SignUpActivity.this, "Confirmed Signup", Toast.LENGTH_SHORT).show();
                                    HelperClassv2 helperClass=new HelperClassv2(name,user.getUid(),username,age,phone,gender);
                                    reference.child(user.getUid()).setValue(helperClass)
                                            .addOnCompleteListener(saveTask -> {
                                                if (saveTask.isSuccessful()) {
                                                    dialog.dismiss();
                                                    Toast.makeText(SignUpActivity.this,"You have signup successfully!",Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    dialog.dismiss();
                                                    Toast.makeText(SignUpActivity.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SignUpActivity.this, "SignUp Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });;
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Account Created failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });;
            }
        });

        binding.loginRedirectText.setOnClickListener(v->{
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        });
    }
    void spinnersetup(){
        String[] genders={"Male","Female","Non-binary","Other","Prefer not to say"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,genders);
        binding.signupGender.setAdapter(adapter);
    }
    void onSignupPress(){
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setContentView(R.layout.progress_dialog);
        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
        }
        ProgressBar pb = dialog.findViewById(R.id.pb_loading);
        dialog.show();

    }
    void createUser(){
        auth.createUserWithEmailAndPassword(binding.signupEmail.getText().toString().trim(),binding.signupPassword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = task.getResult().getUser();
                            Toast.makeText(SignUpActivity.this, "Confirmed Signup", Toast.LENGTH_SHORT).show();
                            //store to db
                        }
                    }
                });
        }
    }
