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

public class findcustomer2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //navigation view
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    DatabaseReference reference,reference2,reference3;

    TextView activitiesTV, agencyNameTV, basePriceTV, destinationNameTV, destinationProvinceTV, agencyNameTV2, boatNameTV, payIDTV, capacityTV,usernameTV,date_schedTV,date_sentTV,ticketStatusTV;
    String customer_name,activities, agency_name, destination_name, destination_province, boat_name, payID, capacity, username,date_sched,date_sent,ticketStatus,pump_boat_name,seating_capacity,date_booked,date_scheduled;
    int base_price,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findcustomer2);
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

        reference = FirebaseDatabase.getInstance().getReference("/Pending/");
        reference2 = FirebaseDatabase.getInstance().getReference("notifications");

        activitiesTV = findViewById(R.id.destinationActivitesTextView);
        agencyNameTV = findViewById(R.id.agencyNameTextView);
        basePriceTV = findViewById(R.id.basePriceTextView);
        destinationNameTV = findViewById(R.id.destinationNameTextView);
        destinationProvinceTV = findViewById(R.id.destinationProvinceTextView);
        boatNameTV = findViewById(R.id.pumpboatNameTextView);
        capacityTV = findViewById(R.id.seatingCapacityTextView);
        usernameTV = findViewById(R.id.destinationCustomerNameTextView);
        date_schedTV = findViewById(R.id.dateScheduledTextView);
        date_sentTV = findViewById(R.id.dateBookedTextView);
        ticketStatusTV = findViewById(R.id.ticketStatusTextView);
        payIDTV = findViewById(R.id.payIDTextView);



        RetrievingData();
    }

    private void RetrievingData() {
        //getting the data from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            activities = extras.getString("activities");
            agency_name = extras.getString("agency_name");
            destination_name = extras.getString("destination_name");
            destination_province = extras.getString("destination_province");
            base_price = extras.getInt("base_price");
            boat_name = extras.getString("boat_name");
            capacity = extras.getString("capacity");
            date_sched = extras.getString("date_sched");
            date_sent = extras.getString("date_sent");
            ticketStatus = extras.getString("ticketStatus");
            payID = extras.getString("payID");


        } else {
            Toast.makeText(this, "No data received", Toast.LENGTH_LONG).show();
        }
        //putting data on activity

        activitiesTV.setText(activities);
        agencyNameTV.setText(agency_name);
        destinationNameTV.setText(destination_name);
        destinationProvinceTV.setText(destination_province);
        basePriceTV.setText(String.valueOf(base_price));
        boatNameTV.setText(boat_name);
        capacityTV.setText(capacity);
        usernameTV.setText(username);
        date_schedTV.setText(date_sched);
        date_sentTV.setText(date_sent);
        ticketStatusTV.setText(ticketStatus);
        payIDTV.setText(payID);

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

    public void btnDecline(View view) {

        reference3 = FirebaseDatabase.getInstance().getReference("boatbooking");

        activities = activitiesTV.getText().toString();
        agency_name = agencyNameTV.getText().toString();
        destination_name = destinationNameTV.getText().toString();
        destination_province = destinationProvinceTV.getText().toString();
        pump_boat_name = boatNameTV.getText().toString();
        seating_capacity = capacityTV.getText().toString();
        date_scheduled = date_schedTV.getText().toString();
        //sending int to firebase
        String base_price_first = basePriceTV.getText().toString();
        price = Integer.parseInt(base_price_first);
        payID = payIDTV.getText().toString();

        customer_name = usernameTV.getText().toString();
        date_booked = date_sentTV.getText().toString();
        ticketStatus = "Rejected";

        UserAcceptingCustomer userAcceptingCustomer = new UserAcceptingCustomer(date_booked,date_scheduled,agency_name,destination_name,activities
                ,destination_province,pump_boat_name,seating_capacity,payID,ticketStatus,customer_name,price);

        String id = reference3.push().getKey();
        reference3.child(id).setValue(userAcceptingCustomer);

        Toast.makeText(findcustomer2.this, "Booking Declined!", Toast.LENGTH_LONG).show();
        sendDeclineMessage();
        sendDeclineMessageToSelf();
        deleteData();

        //direct to create message to state reason kung ngano ni cancel siyas booking
        String reasonTxt = "State reason for declining the customer booking";
        Intent intent = new Intent(getApplicationContext(), CreateMessage.class);
        intent.putExtra("username", agency_name);
        intent.putExtra("subjectTxt", reasonTxt);
        startActivity(intent);
    }

    private void deleteData() {
        Query checkUser = reference.orderByChild("payID").equalTo(payID);//reference from the username in main activity
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();//to remove data in firebase based from messsageTxt
                   // startActivity(new Intent(getApplicationContext(), findCustomer.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btnAccept(View view) {

        reference3 = FirebaseDatabase.getInstance().getReference("boatbooking");

        activities = activitiesTV.getText().toString();
        agency_name = agencyNameTV.getText().toString();
        destination_name = destinationNameTV.getText().toString();
        destination_province = destinationProvinceTV.getText().toString();
        pump_boat_name = boatNameTV.getText().toString();
        seating_capacity = capacityTV.getText().toString();
        date_scheduled = date_schedTV.getText().toString();
        //sending int to firebase
        String base_price_first = basePriceTV.getText().toString();
        price = Integer.parseInt(base_price_first);
        payID = payIDTV.getText().toString();

        customer_name = usernameTV.getText().toString();
        date_booked = date_sentTV.getText().toString();
        ticketStatus = "Ongoing";

        UserAcceptingCustomer userAcceptingCustomer = new UserAcceptingCustomer(date_booked,date_scheduled,agency_name,destination_name,activities
                ,destination_province,pump_boat_name,seating_capacity,payID,ticketStatus,customer_name,price);

        String id = reference3.push().getKey();
        reference3.child(id).setValue(userAcceptingCustomer);

        Toast.makeText(this, "Booking Accepted!", Toast.LENGTH_SHORT).show();
        sendAcceptMessage();
        sendAcceptMessageToSelf();
        deleteData();
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
    }
    private String getTodaysDate() {
        return new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
    }
    private String getTimeWithAmAndPm() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
    //notifies the customer when you already accept their booking
    private void sendAcceptMessage() {

        String messageToTxt = usernameTV.getText().toString(); // CUSTOMER NAME
        String subjectTxt = "Your Booking Has Been Accepted";
        String messageTxt = "Have a good day!";

        String username = Information.globalUser;
        date_sent = getTodaysDate();
        String time_sent = getTimeWithAmAndPm();

        UserMessage messageClass = new UserMessage(username, messageToTxt, subjectTxt, messageTxt, date_sent, time_sent);

        String id = reference2.push().getKey();
        reference2.child(messageToTxt).child(id).setValue(messageClass);

    }
    //notifies the customer when you decline their booking
    private void sendDeclineMessage() {

        String messageToTxt = usernameTV.getText().toString(); // CUSTOMER NAME
        String subjectTxt = "Your Booking Has Been Declined";
        String messageTxt = "Sorry for the inconvenience. Please Reply this message for more information, Thank you!";

        String username = Information.globalUser;
        date_sent = getTodaysDate();
        String time_sent = getTimeWithAmAndPm();

        UserMessage messageClass = new UserMessage(username, messageToTxt, subjectTxt, messageTxt, date_sent, time_sent);

        String id = reference2.push().getKey();
        reference2.child(messageToTxt).child(id).setValue(messageClass);

    }
    //notifies self after accepting a booking
    private void sendAcceptMessageToSelf() {

        String messageToTxt = Information.globalUser; // SELF
        String subjectTxt = "You have accepted a booking";
        String messageTxt = "Have a good day!";

        String username = Information.globalUser;
        date_sent = getTodaysDate();
        String time_sent = getTimeWithAmAndPm();

        UserMessage messageClass = new UserMessage(username, messageToTxt, subjectTxt, messageTxt, date_sent, time_sent);

        String id = reference2.push().getKey();
        reference2.child(messageToTxt).child(id).setValue(messageClass);
    }
    //notifies self after rejecting a booking
    private void sendDeclineMessageToSelf() {

        String messageToTxt = Information.globalUser; // SELF
        String subjectTxt = "You have declined a booking";
        String messageTxt = "Please message the agency!";

        String username = Information.globalUser;
        date_sent = getTodaysDate();
        String time_sent = getTimeWithAmAndPm();

        UserMessage messageClass = new UserMessage(username, messageToTxt, subjectTxt, messageTxt, date_sent, time_sent);

        String id = reference2.push().getKey();
        reference2.child(messageToTxt).child(id).setValue(messageClass);
    }
}