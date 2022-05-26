package com.example.androidintents.fantaseav2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.squareup.picasso.Picasso;

public class customerProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //navigation view
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextInputLayout firstname, lastname, address, phonenumber, email;
    TextView profilenamelabel, usernamelabel, lastnamelabel;
    String userTxt, firstnameTxt, lastnameTxt, addressTxt, phoneTxt, emailTxt,customerNameTxt,destination_name;
    DatabaseReference reference;
    ImageView profileImage;
    Button btnProfilePicture;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

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

        //retrieving data from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customerNameTxt = extras.getString("username");
            destination_name = extras.getString("subjectTxt");
        } else {
            Toast.makeText(this, "No data received", Toast.LENGTH_LONG).show();
        }

        //displaying picture
        StorageReference profileRef = storageReference.child("username/" + customerNameTxt + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        profileData();//method that will retrieve pump boat profile to activity

    }

    private void profileData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("appusers");

        Query checkUser = reference.child("ClientID").orderByChild("username").equalTo(customerNameTxt);//reference from the username in main activity
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Information info = snapshot.getValue(Information.class);

                    userTxt = info.getUsername();
                    firstnameTxt = info.getFirstname();
                    lastnameTxt = info.getLastname();
                    addressTxt = info.getAddress();
                    phoneTxt = info.getPhonenumber();
                    emailTxt = info.getEmail();

                    profilenamelabel.setText(firstnameTxt);
                    lastnamelabel.setText(lastnameTxt);
                    usernamelabel.setText(userTxt);
                    firstname.getEditText().setText(firstnameTxt);
                    lastname.getEditText().setText(lastnameTxt);
                    address.getEditText().setText(addressTxt);
                    phonenumber.getEditText().setText(phoneTxt);
                    email.getEditText().setText(emailTxt);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btnSendCustomerMessage(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateMessage.class);
        intent.putExtra("username", customerNameTxt);
        intent.putExtra("subjectTxt", destination_name);
        startActivity(intent);
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
}