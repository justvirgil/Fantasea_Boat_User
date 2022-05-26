package com.example.androidintents.fantaseav2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class queueActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //navigation view
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView activityTV,agency_usernameTV,boat_nameTV,capacityTV,statusTV;
    DatabaseReference reference,reference2;
    FirebaseDatabase rootNode;
    String activity,agency_username,boat_name,capacity,date_queued,status,time_queued;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

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


        activityTV = findViewById(R.id.queueActivityText2);
        agency_usernameTV = findViewById(R.id.queueAgencyNameText2);
        boat_nameTV = findViewById(R.id.queueBoatNameText2);
        capacityTV = findViewById(R.id.queueCapacityText2);
        statusTV = findViewById(R.id.queueStatusText2);

        profileBoatData();

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

    public void btnQueue(View view) {
        startQueue();
    }

    private void startQueue() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("queue");

        activity = activityTV.getText().toString();
        agency_username = agency_usernameTV.getText().toString();
        capacity = capacityTV.getText().toString();
        status = statusTV.getText().toString();


        String username = Information.globalUser;
        boat_name = username;
        date_queued = getTodaysDate();
        time_queued = getTimeWithAmAndPm();
        status = "Online";
        activity = "Waiting";

        UserQueue userQueue = new UserQueue(activity,agency_username,boat_name,capacity,date_queued,status,time_queued);

        reference.child(agency_username).child(boat_name).setValue(userQueue);
        startActivity(new Intent(this, Dashboard.class));
        Toast.makeText(this, "Queue Successfully!", Toast.LENGTH_SHORT).show();
        
    }

    private String getTimeWithAmAndPm() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getTodaysDate() {
        return new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
    }
    private void profileBoatData() { //retrieving data from firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("appusers");

        Query checkUser = reference.child("BoatID").orderByChild("username").equalTo(Information.globalUser);//reference from the username in main activity
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    queueGetInfo info = snapshot.getValue(queueGetInfo.class);

                    agency_username = info.getAgencyname();
                    boat_name = info.getUsername();
                    capacity = info.getSeatingcapacity();

                    agency_usernameTV.setText(agency_username);
                    boat_nameTV.setText(boat_name);
                    capacityTV.setText(capacity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btnStopQueue(View view) {
        deleteData();
    }

    private void deleteData() {
        reference2 = FirebaseDatabase.getInstance().getReference("/queue/");

        Query checkUser = reference2.child(agency_username).child(Information.globalUser);//reference from the username in main activity
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();//to remove data in firebase based from messsageTxt
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    Toast.makeText(queueActivity.this, "Queue Stopped", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}