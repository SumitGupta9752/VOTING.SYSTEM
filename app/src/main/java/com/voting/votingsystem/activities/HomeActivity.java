package com.voting.votingsystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.voting.votingsystem.R;



public class HomeActivity extends AppCompatActivity {
    private TextView profileName, profileEmail, profilePhone, verifyMsg;
    private ImageView profileImage;
    private Button logoutBtn;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Find the "Logout" button by its ID
        Button logoutButton = findViewById(R.id.button);

        // Set OnClickListener to handle button click
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your logout logic here
                logout();
            }
        });







        Button resetPasswordLocal = findViewById(R.id.resetPasswordLocal); // Assuming this is the id of your "Show Result" button
        resetPasswordLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the ShowResult activity
                Intent intent = new Intent(HomeActivity.this, ForgetPasswordActivity.class);
                // Start the activity
                startActivity(intent);
            }
        });


    Button btnNewElec = findViewById(R.id.btnNewElec);
        btnNewElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new_election activity
                Intent intent = new Intent(HomeActivity.this, new_election.class);
                // Start the activity
                startActivity(intent);
            }
        });


        // Find the button by its ID
        Button btnShowResult = findViewById(R.id.btnShowRe); // Assuming this is the id of your "Show Result" button
        btnShowResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the ShowResult activity
                Intent intent = new Intent(HomeActivity.this, ShowResult.class);
                // Start the activity
                startActivity(intent);
            }
        });




        // Find the button by its ID
        Button btnSetting = findViewById(R.id.changeProfile); // Assuming this is the id of your "Setting" button
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the Setting activity
                Intent intent = new Intent(HomeActivity.this, Setting.class);
                // Start the activity
                startActivity(intent);
            }
        });




        // Find the "Add Candidate" button
        Button btnAddCandidate = findViewById(R.id.btnAddCan);

        // Set OnClickListener on the button
        btnAddCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Candidate activity
                startActivity(new Intent(HomeActivity.this, Candidate.class));
            }
        });




        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profilePhone = findViewById(R.id.profilePhone);
        verifyMsg = findViewById(R.id.verifyMsg);
        profileImage = findViewById(R.id.profileImage);
        logoutBtn = findViewById(R.id.button);

        logoutBtn.setOnClickListener(view -> logout());

        // Fetch user data and populate UI
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            firebaseFirestore.collection("Users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name = document.getString("name");
                                String email = document.getString("email");
                                String phone = document.getString("phone");
                                String imageUri = document.getString("image");

                                profileName.setText(name);
                                profileEmail.setText(email);
                                profilePhone.setText(phone);

                                // Load profile image using Picasso or any other library
                                if (imageUri != null && !imageUri.isEmpty()) {
                                    Picasso.get().load(imageUri).into(profileImage);
                                }

                                // Check if email is verified
                                boolean isEmailVerified = currentUser.isEmailVerified();
                                if (!isEmailVerified) {
                                    verifyMsg.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
        }
    }



    private void logout() {
        mAuth.signOut();
        // Navigate back to login screen
        finish();
    }
}
