package com.example.pranavvidhyasagar.mileagetracker;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView UserName, distanceTravelled;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Vehicles");
    RecyclerView recyclerView;
    VehicleAdapter adapter;


    List<CardDisplayDetails> VehicleList;

    boolean bound;
    private static final String TAG = "AccountFragment";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    MyMainService MainServiceObj;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyMainService.DistanceTravelBinder distanceTravelBinder = (MyMainService.DistanceTravelBinder)service;
            MainServiceObj = distanceTravelBinder.getBinder();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        //distanceTravelled = findViewById(R.id.textView2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        VehicleList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //displayDistance();
    }

    private void displayDistance() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0;
                if(MainServiceObj != null){
                    distance = MainServiceObj.getDistanceTraveled();
                }
                Toast.makeText(getApplicationContext(),String.valueOf(distance),Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 5000);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                VehicleList.clear();
                for(DataSnapshot vehicleSnapshot: dataSnapshot.getChildren()){
                    VehicleCard v = vehicleSnapshot.getValue(VehicleCard.class);
                    String email = v.email;
                    if(email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())){
                        CardDisplayDetails cd = new CardDisplayDetails();
                        cd.manufacturer = v.manufacturer;
                        cd.model = v.model;
                        cd.regnum = vehicleSnapshot.getKey();
                        VehicleList.add(cd);

                    }
                }
                adapter = new VehicleAdapter(getApplicationContext(),VehicleList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        String x = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        UserName = (TextView) findViewById(R.id.UserId);
        UserName.setText(x);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addVehicle) {
            // Handle the add vehicle action
            startActivity(new Intent(MainNavigationActivity.this,AddVehicleActivity.class));
        } else if (id == R.id.nav_analyzeVehicle) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if(id == R.id.buttonService){
            Intent intent = new Intent(getApplicationContext(),MyMainService.class);
            bindService(intent, mServiceConnection,BIND_AUTO_CREATE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getData() {

    }
}
