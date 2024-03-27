    package com.voting.votingsystem.activities;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.voting.votingsystem.R;

    import de.hdodenhof.circleimageview.CircleImageView;

    public class SignUpActivity extends AppCompatActivity {

        private CircleImageView userprofile;
        private EditText userName, userPassword, userEmail, userCustomerID;
        private Button signUpBtn;
        private FirebaseAuth mAuth;
        private Uri mainUri =null;


        public static final String  PREFERENCES = "prefKey";
        public static final String  Name = "nameKey";
        public static final String  Email = "emailKey";
        public static final String  Password = "passwordKey";
        public static final String  CustomerId = "customerIdKey";
        public static final String  Image = "imageKey";

        SharedPreferences sharedPreferences;
        String name,password,customerId,email;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

            sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES,MODE_PRIVATE);
    //        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES,MODE_PRIVATE);

            findViewById(R.id._have_account).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            Button signUpButton = findViewById(R.id.signup_btn);

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle sign-up logic if needed

                    // Navigate to the login page
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish(); // Optional: Finish the current activity to prevent the user from navigating back to it
                }
            });






            userprofile = findViewById(R.id.profile_image);
            userName = findViewById(R.id.user_name);
            userPassword = findViewById(R.id.user_password);
            userEmail = findViewById(R.id.user_email);
            userCustomerID = findViewById(R.id.customer_id);
            signUpBtn = findViewById(R.id.signup_btn);

            mAuth = FirebaseAuth.getInstance();

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     name = userName.getText().toString().trim();
                     password = userPassword.getText().toString().trim();
                     email = userEmail.getText().toString().trim();
                     customerId = userCustomerID.getText().toString().trim();

                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) &&
                            !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                            !TextUtils.isEmpty(customerId)) {

                        createUser(email, password);

                    } else {
                        Toast.makeText(SignUpActivity.this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void createUser(String email, String password) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();

                                verifyEmail();

                            } else {
                                Toast.makeText(SignUpActivity.this, "Failed to create user. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
               });
    }

        private void verifyEmail() {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user!=null) {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor pref = sharedPreferences.edit();
                            pref.putString(Name, name);
                            pref.putString(Password, password);
                            pref.putString(Email, email);
                            pref.putString(Image, mainUri.toString());
                            pref.putString(CustomerId, customerId);
                            pref.commit();

                            Toast.makeText(SignUpActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();  // FirebaseUser.getInstance() corrected to FirebaseAuth.getInstance()
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            mAuth.signOut();
                            finish();
                        }
                        // Handle the case where email verification sending failed
                        // You might want to show a message or log the error
                    }

                });
            }}}

