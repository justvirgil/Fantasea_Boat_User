package com.example.androidintents.fantaseav2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    TextInputEditText usernameSignUp, firstNameSignUp, lastNameSignUp, phoneNumberSignUp, emailSignUp, passwordSignUp, addressSignUp, travelAgencySignUp, seatingCapSignUp;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    FirebaseAuth mAuth;

    String usernameString, phoneString, agencyname;

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        textInputLayout = findViewById(R.id.textInputLayout11);
        autoCompleteTextView = findViewById(R.id.travelAgencySignUp);
        String[] items = {"Melgo's Agency", "Jumawan's Agency"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(SignUp.this, R.layout.list_item, items);
        autoCompleteTextView.setAdapter(itemAdapter);


        usernameSignUp = findViewById(R.id.usernameSignUp);
        firstNameSignUp = findViewById(R.id.firstNameSignUp);
        lastNameSignUp = findViewById(R.id.lastNameSignUp);
        phoneNumberSignUp = findViewById(R.id.phoneNumberSignUp);
        emailSignUp = findViewById(R.id.emailSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        addressSignUp = findViewById(R.id.addressSignUp);
        // travelAgencySignUp = findViewById(R.id.travelAgencySignUp);
        seatingCapSignUp = findViewById(R.id.seatingCapacitySignUp);

        mAuth = FirebaseAuth.getInstance();
        usernameString = usernameSignUp.getText().toString();
        phoneString = phoneNumberSignUp.getText().toString();
    }

    public void backButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnCreateAccount(View view) {

        if(!validateUsername() || !validateFirstName() || !validateLastName() || !validateAddress() || !validatePhoneNumber() || !validateEmailAdd() || !validatePassword() || !validateSeat() || !validateAgency()) {
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }else {
            validationRetrieval();
        }
        // createUser();
    }

    public void createUser() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Pending");
        String email = emailSignUp.getText().toString();
        String password = passwordSignUp.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    agencyname = autoCompleteTextView.getText().toString();
                    String username = usernameSignUp.getText().toString();
                    String seatingcapacity = seatingCapSignUp.getText().toString();
                    String firstname = firstNameSignUp.getText().toString();
                    String lastname = lastNameSignUp.getText().toString();
                    String address = addressSignUp.getText().toString();
                    String phonenumber = phoneNumberSignUp.getText().toString();
                    String email = emailSignUp.getText().toString();
                    String password = passwordSignUp.getText().toString();

                    UserHelperClass helperClass = new UserHelperClass(username, agencyname, seatingcapacity, firstname, lastname, phonenumber, email, password, address);

                    reference.child("BoatID").child(mAuth.getUid()).setValue(helperClass);
                    // reference.child(username).setValue(helperClass);
                    Toast.makeText(SignUp.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                } else {
                    Toast.makeText(SignUp.this, "Registration failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private Boolean validateUsername() {
        String value = usernameSignUp.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (value.isEmpty()) {
            usernameSignUp.setError("Field is empty");
            return false;
        } else if (value.length() >= 15) {
            usernameSignUp.setError("Username too long");
            return false;
        } else if (!value.matches(noWhiteSpace)) {
            usernameSignUp.setError("White Spaces are not allowed");
            return false;
        } else {
            usernameSignUp.setError(null);
            return true;
        }
    }

    private Boolean validateFirstName() {
        String value = firstNameSignUp.getText().toString();

        if (value.isEmpty()) {
            firstNameSignUp.setError("Field is empty");
            return false;
        } else {
            firstNameSignUp.setError(null);
            return true;
        }
    }

    private Boolean validateLastName() {
        String value = lastNameSignUp.getText().toString();

        if (value.isEmpty()) {
            lastNameSignUp.setError("Field is empty");
            return false;
        } else {
            lastNameSignUp.setError(null);
            return true;
        }
    }

    private Boolean validateAddress() {
        String value = addressSignUp.getText().toString();

        if (value.isEmpty()) {
            addressSignUp.setError("Field is empty");
            return false;
        } else {
            addressSignUp.setError(null);
            return true;
        }
    }

    private Boolean validateSeat() {
        String value = seatingCapSignUp.getText().toString();

        if (value.isEmpty()) {
            seatingCapSignUp.setError("Field is empty");
            return false;
        } else if (value.length() > 2) {
            seatingCapSignUp.setError("Invalid Seating Capacity");
            return false;
        } else {
            seatingCapSignUp.setError(null);
            return true;
        }
    }

    private Boolean validateAgency() {
        String value = autoCompleteTextView.getText().toString();

        if (value.isEmpty()) {
            autoCompleteTextView.setError("Field is empty");
            return false;
        } else {
            autoCompleteTextView.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String value = phoneNumberSignUp.getText().toString();

        if (value.isEmpty()) {
            phoneNumberSignUp.setError("Field is empty");
            return false;
        } else if (value.length() > 10) {
            phoneNumberSignUp.setError("Invalid Phone Number");
            return false;
        } else {
            phoneNumberSignUp.setError(null);
            return true;
        }
    }

    private Boolean validateEmailAdd() {
        String value = emailSignUp.getText().toString();
        String emailPat = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (value.isEmpty()) {
            emailSignUp.setError("Field is empty");
            return false;
        } else if (!value.matches(emailPat)) {
            emailSignUp.setError("Invalid Email Address");
            return false;
        } else {
            emailSignUp.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String value = passwordSignUp.getText().toString();
        String passwordVal = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).{4,}$";
        if (value.isEmpty()) {
            passwordSignUp.setError("Field is empty");
            return false;
        } else if (!value.matches(passwordVal)) {
            passwordSignUp.setError("Password is too weak");
            return false;
        } else {
            passwordSignUp.setError(null);
            return true;
        }
    }

    //validating username/phonenumber
    private void validationRetrieval() { //retrieving data from firebase
        String usernameString = usernameSignUp.getText().toString();
        String phoneString = phoneNumberSignUp.getText().toString();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("appusers");

        reference2.child("BoatID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        if (usernameString.equals(dataSnapshot.child("username").getValue())) {

                            if (phoneString.equals(dataSnapshot.child("phonenumber").getValue())) {

                                phoneNumberSignUp.setError("Phone Number Already Exist!");
                                phoneNumberSignUp.requestFocus();
                            } else {
                                usernameSignUp.setError("Username Already Exist!");
                                usernameSignUp.requestFocus();
                            }
                            return;

                        } if (phoneString.equals(dataSnapshot.child("phonenumber").getValue())) {

                            phoneNumberSignUp.setError("Phone Number Already Exist!");
                            phoneNumberSignUp.requestFocus();
                            return;
                        }
                    }
                    phoneNumberSignUp.setError(null);
                    usernameSignUp.setError(null);
                    createUser();
                } else {
                    createUser();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}