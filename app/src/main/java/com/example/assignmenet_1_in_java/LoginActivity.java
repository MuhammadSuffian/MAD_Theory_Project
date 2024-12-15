package com.example.assignmenet_1_in_java;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.assignmenet_1_in_java.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityLoginBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
       auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) { // if user is already logined in then it will redirect to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding.btnLogin.setOnClickListener(v->{
            if(binding.loginEmail.getText().toString().trim().isEmpty()){
                Toast.makeText(this,"Email Field Empty",Toast.LENGTH_SHORT).show();
            }
            else if(binding.loginPassword.getText().toString().trim().isEmpty()){
                Toast.makeText(this,"Password Field Empty",Toast.LENGTH_SHORT).show();
            }
            else if(binding.loginPassword.getText().toString().length()<6){
                Toast.makeText(this,"Minimum Length for password is 6",Toast.LENGTH_SHORT).show();
            }
            else{
                onLoginPress();
                auth.signInWithEmailAndPassword(binding.loginEmail.getText().toString(),binding.loginPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String userId = task.getResult().getUser().getUid();
                                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                                    DatabaseReference reference = database.getReference("users");
                                    Query checkUserDb = reference.orderByChild("userId").equalTo(userId);
                                    checkUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                String nameFromDB = snapshot.child(userId).child("name").getValue(String.class);
                                                String usernameFromDB = snapshot.child(userId).child("username").getValue(String.class);
                                                String ageFromDB = snapshot.child(userId).child("age").getValue(String.class);
                                                String genderFromDB = snapshot.child(userId).child("gender").getValue(String.class);
                                                String phoneFromDB = snapshot.child(userId).child("username").getValue(String.class);
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("name", nameFromDB);
                                                intent.putExtra("email",binding.loginEmail.getText().toString().trim());
                                                intent.putExtra("username", usernameFromDB);
                                                intent.putExtra("password", binding.loginPassword.getText().toString().trim());
                                                intent.putExtra("age", ageFromDB);
                                                intent.putExtra("gender", genderFromDB);
                                                intent.putExtra("phone_number", phoneFromDB);
                                                dialog.dismiss();
                                                startActivity(intent);
                                            } else {
                                                binding.loginEmail.setError("User does not exist");
                                                binding.loginEmail.requestFocus();
                                                dialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                                else{
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                dialog.dismiss();
            }
        });
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    void onLoginPress(){
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
}