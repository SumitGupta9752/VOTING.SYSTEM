package com.voting.votingsystem.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.voting.votingsystem.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText emailEdit;
    private Button reset;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEdit = findViewById(R.id.email_edit);
        reset = findViewById(R.id.button);
        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String email = emailEdit.getText().toString().trim();
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailEdit.getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if( task.isSuccessful()) {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "Password reset email sent to " + email, Toast.LENGTH_SHORT).show();
                                finish();  // Close the activity after sending reset email
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "Failed to send password reset email. Please check your email address.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter your registered email address", Toast.LENGTH_SHORT).show();
        }
    }
}
