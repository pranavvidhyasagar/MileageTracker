package com.example.pranavvidhyasagar.mileagetracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class VehicleDetails extends AppCompatActivity {

    private TextView mvehicleManufacturer, mvehicleName, mvehicleMileage, mvehicleDistance,mvehicleRegnum;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Vehicles");
    List<VehicleCard> VehicleList;
    String rn;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_dash:
                    rn = getIntent().getStringExtra("x");
                    mvehicleManufacturer.setText(rn);
                    /*database.child(rn).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            VehicleCard v = dataSnapshot.getValue(VehicleCard.class);
                            mvehicleMileage.setText(v.getEmail());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                    database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot vehicleSnapshot: dataSnapshot.getChildren()){
                                VehicleCard v = vehicleSnapshot.getValue(VehicleCard.class);
                                String email = v.email;
                                if(email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())){
                                    if(rn.equals(vehicleSnapshot.getKey())){
                                        mvehicleMileage.setText(v.manufacturer);
                                    }
                                }
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    /*mvehicleName.setText(v.getModel());
                    mvehicleRegnum.setText(v.getRegnum());*/
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
        mvehicleRegnum = (TextView) findViewById(R.id.vehicleRegnum);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
