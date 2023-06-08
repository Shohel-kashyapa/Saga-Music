package com.example.sagamusic;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;

import com.example.sagamusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class accountFragment extends Fragment {

    private EditText turstnedEdit;
    private EditText trustpneditEditText;

    private Button deleteButton;
    private TextView emailTextView;
    private EditText nameEditText;
    private Button updateButton;
    private Button forgetButton;
    private EditText addressEditText;


    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public accountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        addressEditText = view.findViewById(R.id.address);
        emailTextView = view.findViewById(R.id.email1);
        nameEditText = view.findViewById(R.id.editTextTextPersonName);
        updateButton = view.findViewById(R.id.updatebutton);
        forgetButton = view.findViewById(R.id.fgbutton);
        deleteButton = view.findViewById(R.id.deteacc); // Add deleteButton

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new values from EditText fields
                String newname =  nameEditText.getText().toString().trim();
                String newFullAddress = addressEditText.getText().toString().trim();

                // Check if the fields are not empty
                if (!newname.isEmpty()) {
                    // Update the "name" value in the userRef
                    userRef.child("name").setValue(newname);
                }

                if (!newFullAddress.isEmpty()) {
                    // Update the "address" value in the userRef
                    userRef.child("address").setValue(newFullAddress);
                }

                // Show a toast message
                Toast.makeText(getActivity(), "Update successful", Toast.LENGTH_SHORT).show();
            }
        });

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete user account and log out
                if (currentUser != null) {
                    currentUser.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // User account deleted successfully, log out
                                        firebaseAuth.signOut();
                                        // Redirect to login or home screen
                                        // For example:
                                        Intent intent = new Intent(getActivity(), login.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        // Error occurred while deleting user account
                                        // Handle the error or display a message to the user
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (userRef != null) {
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve values from snapshot
                        String name = snapshot.child("name").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);


                        // Set the retrieved values to the respective fields
                        emailTextView.setText(email);
                        nameEditText.setText(name);
                        addressEditText.setText(address);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }
}
