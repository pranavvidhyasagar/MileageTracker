package com.example.pranavvidhyasagar.mileagetracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class VehicleDetails extends AppCompatActivity {

    private TextView mvehicleManufacturer, mvehicleName, mvehicleMileage, mvehicleDistance;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_dash:

                    mvehicleManufacturer.setText("Hello");
                return true;

                case R.id.navigation_graph:
                    mvehicleManufacturer.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        mvehicleManufacturer = (TextView) findViewById(R.id.vehicleManufacturer);
        mvehicleName = (TextView) findViewById(R.id.vehicleName);
        mvehicleMileage = (TextView) findViewById(R.id.vehicleMileage);
        mvehicleDistance = (TextView) findViewById(R.id.vehicleDistance);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
