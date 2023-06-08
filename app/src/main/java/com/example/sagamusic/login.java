package com.example.sagamusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get references to UI elements
        TextInputLayout mEmailTextInputLayout = findViewById(R.id.email);
        TextInputLayout mPasswordTextInputLayout = findViewById(R.id.pass);
        mLoginButton = findViewById(R.id.pwbtn);

        // Get references to EditText within TextInputLayout
        mEmailEditText = mEmailTextInputLayout.getEditText();
        mPasswordEditText = mPasswordTextInputLayout.getEditText();

        mAuth = FirebaseAuth.getInstance();

        // Set up click listener for login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                // Validate user input
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the user is an admin
                    if (email.equals("admin") && password.equals("admin")) {
                        // Navigate to the admin page
                        Intent adminIntent = new Intent(login.this, adminpage.class);
                        startActivity(adminIntent);
                    } else {
                        // Sign in user with Firebase authentication
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful() && task.getResult() != null) {
                                            // Display a success message and start the home activity
                                            Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(login.this, home.class);
                                            startActivity(intent);
                                        } else {
                                            // Display an error message if the authentication fails
                                            Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        // Set up click listener for create account button
        Button createAccountButton = findViewById(R.id.button4);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the sign-up activity
                Intent intent = new Intent(login.this, SignUp.class);
                startActivity(intent);
            }
        });

        Button forgetPasswordButton = findViewById(R.id.button3);
        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.activity_forget_password, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(login.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(login.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }
}
