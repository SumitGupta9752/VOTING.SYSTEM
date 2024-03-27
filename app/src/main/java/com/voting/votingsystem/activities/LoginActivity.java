package com.voting.votingsystem.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.voting.votingsystem.R;


public class LoginActivity extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private Button loginBtn;
    private TextView forgetPassword;
    private FirebaseAuth mAuth;
    private  Uri mainUri = null;

    public static final String PREFERENCES = "prefKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    public static final String CustomerId = "customerIdKey";
    public static final String Image = "imageKey";

    SharedPreferences sharedPreferences;

    StorageReference reference;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        reference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();


//        its of extra part
        String imageUriString = sharedPreferences.getString(Image, null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);

            // Rest of your code...
        }


        findViewById(R.id.dont_have_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        TextView forgetPasswordTextView = findViewById(R.id.forget_password);
        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });


        loginBtn = findViewById(R.id.login_btn);
        userPassword = findViewById(R.id.user_password);
        userEmail = findViewById(R.id.user_email);
        forgetPassword = findViewById(R.id.forget_password);
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the HomeActivity directly
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Finish LoginActivity to prevent going back when pressing back button
            }
        });


//
//            private void verifyEmail() {
//                FirebaseUser user = mAuth.getCurrentUser();
//                assert user != null;
//                if (user.isEmailVerified()) {
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//
//                    finish();
//                } else {
//                    mAuth.signOut();
//                    Toast.makeText(LoginActivity.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
//
//
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    String name = sharedPreferences.getString(Name, null);
//                    String password = sharedPreferences.getString(Password, null);
//                    String email = sharedPreferences.getString(Email, null);
//                    String customerId = sharedPreferences.getString(CustomerId, null);
//                    String image = sharedPreferences.getString(Image, null);
//
//                    if (name != null && password != null && email != null && customerId != null && image != null) {
//
//                        String uid = mAuth.getUid();
//
//                        StorageReference imagePath = reference.child("image_profile").child(uid + ".jpg");
//                        imagePath.putFile(Uri.parse(image)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//
//                            @Override
//                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                                if (task.isSuccessful()) {
//                                    imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//
//                                            Map<String, String> map = new HashMap<>();
//                                            map.put("name", name);
//                                            map.put("email", email);
//                                            map.put("password", password);
//                                            map.put("customerId", customerId);
//                                            map.put("image", uri.toString());
//                                            map.put("uid", uid);
//
//                                            firebaseFirestore.collection("Users")
//                                                    .document(uid)
//                                                    .set(map)
//                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                                            if (task.isSuccessful()) {
//
//                                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//
//
//                                                                finish();
//
//
//                                                            } else {
//                                                                Toast.makeText(LoginActivity.this, "Data not store", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        }
//
//                                                    });
//
//
//                                        }
//                                    });
//
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Upload failed" + task.getException(), Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//
//                        });
//
//
//                    } else {
//                        mAuth.signOut();
//                        Toast.makeText(LoginActivity.this, "Please verify your Email", Toast.LENGTH_SHORT).show();
//                    }
//                }}

        }


    }



