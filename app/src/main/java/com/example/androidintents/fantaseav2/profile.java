package com.example.androidintents.fantaseav2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //navigation view
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextInputLayout firstname, lastname, address, phonenumber, email, agencyname, seatingcapacity;
    TextView profilenamelabel, usernamelabel, lastnamelabel;
    String userTxt, firstnameTxt, lastnameTxt, addressTxt, phoneTxt, emailTxt, seatingTxt, agencyNameTxt;
    DatabaseReference reference;
    ImageView profileImage;
    Button btnProfilePicture;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //navigation view
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);//
        navigationView.setCheckedItem(R.id.nav_home);
        //navigation view

        reference = FirebaseDatabase.getInstance().getReference("appusers");
        storageReference = FirebaseStorage.getInstance().getReference("appusers");

        firstname = findViewById(R.id.firstNameProfile);
        lastname = findViewById(R.id.lastNameProfile);
        address = findViewById(R.id.addressProfile);
        phonenumber = findViewById(R.id.phoneNumberProfile);
        email = findViewById(R.id.emailProfile);
        profilenamelabel = findViewById(R.id.profileNameLabel);
        usernamelabel = findViewById(R.id.profileUsernameLabel);
        lastnamelabel = findViewById(R.id.profileLastNameLabel);
        profileImage = findViewById(R.id.profilePicture);
        agencyname = findViewById(R.id.AgencyNameProfile);
        seatingcapacity = findViewById(R.id.seatingCapacityProfile);

        profileData();
        //profilepic
        btnProfilePicture = findViewById(R.id.btnChangePic);
        //displaying picture
        StorageReference profileRef = storageReference.child("username/" + Information.globalUser + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
        changePicture();
       // usernameRetrieval();
    }

    private void profileData() { //retrieving data from firebase
        reference = FirebaseDatabase.getInstance().getReference("appusers");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("appusers");

        Query checkUser = reference.child("BoatID").orderByChild("username").equalTo(Information.globalUser);//reference from the username in main activity
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Information info = snapshot.getValue(Information.class);
                    //para ma retrieve kuha tag string para ma storan then equal to the information class nga getter
                    //string para storan sa data
                    userTxt = info.getUsername();
                    firstnameTxt = info.getFirstname();
                    lastnameTxt = info.getLastname();
                    addressTxt = info.getAddress();
                    phoneTxt = info.getPhonenumber();
                    emailTxt = info.getEmail();
                    seatingTxt = info.getSeatingcapacity();
                    agencyNameTxt = info.getAgencyname();
                    // ari e store ang string sa label
                    //text boxes
                    profilenamelabel.setText(firstnameTxt);
                    lastnamelabel.setText(lastnameTxt);
                    usernamelabel.setText(userTxt);
                    firstname.getEditText().setText(firstnameTxt);
                    lastname.getEditText().setText(lastnameTxt);
                    address.getEditText().setText(addressTxt);
                    phonenumber.getEditText().setText(phoneTxt);
                    email.getEditText().setText(emailTxt);
                    seatingcapacity.getEditText().setText(seatingTxt);
                    agencyname.getEditText().setText(agencyNameTxt);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //updating firebase information
    public void btnUpdateProfile(View view) {

        if (isNameChanged() || isLastNameChanged() || isAddressChanged() || isSeatingCapacityChanged()) {
            profilenamelabel.setText(firstname.getEditText().getText().toString());
            lastnamelabel.setText(lastname.getEditText().getText().toString());
        }
    }

    private boolean isNameChanged() {

        String value = firstname.getEditText().getText().toString();

        if (value.isEmpty()) {
            firstname.setError("Field is empty");
            return false;
        } else if (!firstnameTxt.equals(firstname.getEditText().getText().toString())) {
            reference.child("BoatID").child(Information.globalID).child("firstname").setValue(firstname.getEditText().getText().toString());
            firstnameTxt = firstname.getEditText().getText().toString();
            Toast.makeText(this, "First Name Updated!", Toast.LENGTH_SHORT).show();
            isLastNameChanged();
            isAddressChanged();
            isSeatingCapacityChanged();
            firstname.setError(null);
            return true;
        } else {
            firstname.setError(null);
            return false;
        }
    }

    private boolean isLastNameChanged() {

        String value = lastname.getEditText().getText().toString();

        if (value.isEmpty()) {
            lastname.setError("Field is empty");
            return false;
        } else if (!lastnameTxt.equals(lastname.getEditText().getText().toString())) {
            reference.child("BoatID").child(Information.globalID).child("lastname").setValue(lastname.getEditText().getText().toString());
            lastnameTxt = lastname.getEditText().getText().toString();
            Toast.makeText(this, "Last Name Updated!", Toast.LENGTH_SHORT).show();
            isNameChanged();
            isAddressChanged();
            isSeatingCapacityChanged();
            lastname.setError(null);
            return true;
        } else {
            lastname.setError(null);
            return false;
        }
    }

    private boolean isAddressChanged() {
        String value = address.getEditText().getText().toString();

        if (value.isEmpty()) {
            address.setError("Field is empty");
            return false;
        } else if (!addressTxt.equals(address.getEditText().getText().toString())) {
            reference.child("BoatID").child(Information.globalID).child("address").setValue(address.getEditText().getText().toString());
            addressTxt = address.getEditText().getText().toString();
            Toast.makeText(this, "Address Updated!", Toast.LENGTH_SHORT).show();
            isNameChanged();
            isLastNameChanged();
            isSeatingCapacityChanged();
            address.setError(null);
            return true;
        } else {
            address.setError(null);
            return false;
        }
    }

    private boolean isSeatingCapacityChanged() {
        String value = seatingcapacity.getEditText().getText().toString();

        if (value.isEmpty()) {
            seatingcapacity.setError("Field is empty");
            return false;
        } else if (value.length() > 2) {
            seatingcapacity.setError("Invalid Seating Capacity");
            return false;
        } else if (!seatingTxt.equals(seatingcapacity.getEditText().getText().toString())) {
            reference.child("BoatID").child(Information.globalID).child("seatingcapacity").setValue(seatingcapacity.getEditText().getText().toString());
            seatingTxt = seatingcapacity.getEditText().getText().toString();
            Toast.makeText(this, "Seating Capacity Updated!", Toast.LENGTH_SHORT).show();
            isNameChanged();
            isLastNameChanged();
            isAddressChanged();
            seatingcapacity.setError(null);
            return true;
        } else {
            seatingcapacity.setError(null);
            return false;
        }
    }

    //for profile picture
    private void changePicture() {

        btnProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                // profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);

            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //uploading image to firebase storage
        StorageReference fileRef = storageReference.child("username/" + Information.globalUser + "/profile.jpg"); // kung asa naka store ang picture
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);// ari mo gawas ang picture
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profile.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent6 = new Intent(this, Dashboard.class);
                startActivity(intent6);
                break;
            case R.id.nav_fCustomer:
                Intent intent = new Intent(this, findCustomer.class);
                startActivity(intent);
                break;
            case R.id.nav_transaction:
                Intent intent2 = new Intent(this, Transaction.class);
                startActivity(intent2);
                break;
            case R.id.nav_profile:
                Intent intent3 = new Intent(this, profile.class);
                startActivity(intent3);
                break;
            case R.id.nav_logout:
                Intent intent4 = new Intent(this, MainActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_about:
                Intent intent5 = new Intent(this, aboutus.class);
                startActivity(intent5);
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_report:
                Intent intent7 = new Intent(this, reportMessage.class);
                startActivity(intent7);
                break;
            case R.id.nav_notification:
                Intent intent8 = new Intent(this, Notification.class);
                startActivity(intent8);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //retrieving username
    private void usernameRetrieval() { //retrieving data from firebase
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("appusers");

        reference2.child("BoatID").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
             //       Information.globalID = dataSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}