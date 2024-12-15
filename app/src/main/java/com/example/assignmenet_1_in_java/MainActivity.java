package com.example.assignmenet_1_in_java;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.assignmenet_1_in_java.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String nameUser, emailUser ,usernameUser ,passwordUser, ageUser ,genderUser,phoneUser;
    FirebaseAuth auth;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth= FirebaseAuth.getInstance();
        headerView = binding.navView.getHeaderView(0);
        buttons();
        showAllUserData();
        getDatafromDb();
    }
    void getDatafromDb(){
        if(auth.getCurrentUser().getUid()!=null){
            String userId = auth.getCurrentUser().getUid();
            emailUser = auth.getCurrentUser().getEmail();
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("users");
            Query checkUserDb = reference.orderByChild("userId").equalTo(userId);
            checkUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        nameUser = snapshot.child(userId).child("name").getValue(String.class);
                        usernameUser = snapshot.child(userId).child("username").getValue(String.class);
                        ageUser = snapshot.child(userId).child("age").getValue(String.class);
                        genderUser = snapshot.child(userId).child("gender").getValue(String.class);
                        phoneUser = snapshot.child(userId).child("username").getValue(String.class);
                        binding.tvName.setText("Name: "+nameUser);
                        binding.tvEmail.setText("Email: "+emailUser);
                        binding.tvUsername.setText("Username: "+usernameUser);
//                        binding.tvPassword.setText("Password: "+"*********");
                        binding.tvAge.setText("Age: "+ageUser);
                        binding.tvGender.setText("Gender: "+genderUser);
                        binding.tvPhoneNumber.setText("Phone Number: "+phoneUser);
                        TextView username=headerView.findViewById(R.id.tv_username);
                        username.setText(nameUser);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    void buttons(){
        binding.imgDrawerToggle.setOnClickListener(v->{
            binding.maindrawer.openDrawer(GravityCompat.START);
        });
        binding.imageBtn.setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "Select Picture"), 138);
        });
    }
    private void loadStoredImage(ImageView a) {
        SharedPreferences sharedPreferences = getSharedPreferences("Suffian_138", MODE_PRIVATE);
        String uriString = sharedPreferences.getString("image_hehe", null);
        if (uriString != null) {
            Uri uri = Uri.parse(uriString);
            Glide.with(this)
                    .load(uri)
                    .into(a);
        }
    }
    public void showAllUserData(){
        Intent intent = getIntent();
         nameUser = intent.getStringExtra("name");
         emailUser = intent.getStringExtra("email");
         usernameUser = intent.getStringExtra("username");
         passwordUser = intent.getStringExtra("password");
         ageUser = intent.getStringExtra("age");
         genderUser = intent.getStringExtra("gender");
         phoneUser = intent.getStringExtra("phone_number");
        binding.tvName.setText("Name: "+nameUser);
        binding.tvEmail.setText("Email: "+emailUser);
        binding.tvUsername.setText("Username: "+usernameUser);
        binding.tvPassword.setText("Password: "+passwordUser);
        binding.tvAge.setText("Age: "+ageUser);
        binding.tvGender.setText("Gender: "+genderUser);
        binding.tvPhoneNumber.setText("Phone Number: "+phoneUser);
        ImageView img=headerView.findViewById(R.id.img_logo);
        TextView username=headerView.findViewById(R.id.tv_username);
        LinearLayout signout=headerView.findViewById(R.id.ll_signout);
        signout.setOnClickListener(v->{
            auth.getInstance().signOut();
            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent1);
        });
        LinearLayout exit=headerView.findViewById(R.id.ll_exit);
        exit.setOnClickListener(v->{
            finishAndRemoveTask();
            onDestroy();
        });
        username.setText(nameUser);
        loadStoredImage(img);
      }
    private void saveImageUri(Uri uri) {
        SharedPreferences sharedPreferences = getSharedPreferences("Suffian_138",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image_hehe", uri.toString());
        editor.apply();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 138 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                saveImageUri(selectedImageUri);
                View headerView = binding.navView.getHeaderView(0);
                ImageView a=headerView.findViewById(R.id.img_logo);
                Glide.with(this)
                        .load(selectedImageUri)
                        .into(a);
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(binding.maindrawer.isDrawerOpen(GravityCompat.START)){
            binding.maindrawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}
