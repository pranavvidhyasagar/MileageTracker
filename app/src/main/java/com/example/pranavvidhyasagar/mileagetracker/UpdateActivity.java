package com.example.pranavvidhyasagar.mileagetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class UpdateActivity extends AppCompatActivity  {

    private TextView mvehicleManufacturer, mvehicleName, mvehicleMileage, mvehicleDistance,mvehicleRegnum;
    Button mbuttonUpdate;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Vehicles");
    List<VehicleCard> VehicleList;
    String rn, totalDistance, email,manufacturer,model,type;
    float mileage,distance ;
    EditText distanceTravelled;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mvehicleManufacturer = (TextView) findViewById(R.id.vehicleManufacturer);
        mvehicleName = (TextView) findViewById(R.id.vehicleName);
        mvehicleMileage = (TextView) findViewById(R.id.vehicleMileage);
        mvehicleDistance = (TextView) findViewById(R.id.vehicleDistance);
        mvehicleRegnum = (TextView) findViewById(R.id.vehicleRegnum);
            mbuttonUpdate = (Button)findViewById(R.id.buttonUpdate);
        distanceTravelled = (EditText)findViewById(R.id.Odometer);

        mbuttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalDistance = distanceTravelled.getText().toString().trim();
                updateDistanceInDatabse(totalDistance);
            }
        });

        rn = getIntent().getStringExtra("x");
         mvehicleRegnum.setText(rn);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot vehicleSnapshot: dataSnapshot.getChildren()){
                    VehicleCard v = vehicleSnapshot.getValue(VehicleCard.class);
                    email = v.email;
                    if(email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())){
                        if(rn.equals(vehicleSnapshot.getKey())){
                            type = v.type;
                            mvehicleManufacturer.setText(v.manufacturer);
                            mvehicleName.setText(v.model);
                            mvehicleDistance.setText(String.valueOf(v.distance));
                            mvehicleMileage.setText(String.valueOf(v.mileage));
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUpdate:
                totalDistance = distanceTravelled.getText().toString().trim();
                updateDistanceInDatabse(totalDistance);
                break;

            case R.id.buttonGraph:
                break;
        }
    }*/

    private void updateDistanceInDatabse(String totalDistance) {

            manufacturer = mvehicleManufacturer.getText().toString().trim();
            model = mvehicleName.getText().toString().trim();
            mileage = Float.valueOf(mvehicleMileage.getText().toString());
            distance = Float.valueOf(mvehicleDistance.getText().toString());
            distance = distance + Float.valueOf(totalDistance);

            VehicleCard v = new VehicleCard(email,manufacturer,model,type,mileage,distance);

            database.child(rn).setValue(v);

    }
}