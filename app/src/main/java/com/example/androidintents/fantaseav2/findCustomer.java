package com.example.androidintents.fantaseav2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class findCustomer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //navigation view
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    DatabaseReference reference;
    MyPendingAdapter myPendingAdapter;//name of adapter
    RecyclerView recyclerView;
    ArrayList<UserPending> list;
    MyPendingAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_customer);
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

        recyclerView = findViewById(R.id.findCustomerRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        cardViewRetrieval();
    }

    private void cardViewRetrieval() {
        list = new ArrayList<>();
        myPendingAdapter = new MyPendingAdapter(this, list, listener);
        recyclerView.setAdapter(myPendingAdapter);

        //if equal siya sa username then ma detect iyang mga messages

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/Pending/"); //retrieving data way apil ang key

        Query checkUser2 = reference.orderByChild("boat_name").equalTo(Information.globalUser);//reference from the username in main activity

        checkUser2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    UserPending user = dataSnapshot.getValue(UserPending.class);
                    list.add(user);

                }
                myPendingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setOnClickListener() {

        listener = new MyPendingAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int Position) {
                UserPending user = list.get(Position);
                Intent intent = new Intent(getApplicationContext(), findcustomer2.class);
                intent.putExtra("username", user.getUsername());
                intent.putExtra("activities", user.getActivities());
                intent.putExtra("agency_name", user.getAgency_name());
                intent.putExtra("destination_name", user.getDestination_name());
                intent.putExtra("destination_province", user.getDestination_province());
                intent.putExtra("boat_name", user.getBoat_name());
                intent.putExtra("capacity", user.getCapacity());
                intent.putExtra("date_sched", user.getDate_sched());
                intent.putExtra("date_sent", user.getDate_sent());
                intent.putExtra("ticketStatus", user.getTicketStatus());
                intent.putExtra("payID", user.getPayID());
                intent.putExtra("base_price", user.getBase_price());
                startActivity(intent);

            }
        };
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