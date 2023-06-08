package com.example.sagamusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;

            //signupbutton(register user)


            Button button5 = findViewById(R.id.button5);
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerUser();
                /*
                Intent intent = new Intent(SignUp.this,MainActivity .class);
                startActivity(intent);*/


                }
            });
            TextView textViewSwitchtologin = findViewById(R.id.haveACC);
            textViewSwitchToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(view view) {
                    Switchtologin
                }
            });

        }
        private void registerUser() {
            EditText etfullname = findViewById(R.id.fullname);
            EditText etemail = findViewById(R.id.email);
            EditText etpassword = findViewById(R.id.password);

            String fullname = etfullname.getText().toString();
            String email = etemail.getText().toString();
            String password = etpassword.getText().toString();

            if (fullname.isEmpty() ||  email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullname, email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            showMainActivity();
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }


    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToLogin() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }
}