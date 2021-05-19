package com.example.winestorage112;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.winestorage112.databinding.ActivityProfileBinding;
import com.example.winestorage112.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this,SignIn.class));
            finish();
        }
        else {
            String email = firebaseUser.getEmail();
            binding.emailTv.setText(email);


        }
    }
}