package com.example.pranavvidhyasagar.mileagetracker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseTooManyRequestsException;
import java.util.HashMap;
import java.util.Map;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner vehicleTypeSpinner, vehicleManufacturerSpinner;
    EditText editTextVehicleName, editTextRegistrationNumber;
    ProgressDialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        PopulateVehicleTypeSpinner();
        PopulateVehicleManufacturerSpinner();

        findViewById(R.id.buttonAddVehicle).setOnClickListener(this);
    }

    private void PopulateVehicleTypeSpinner() {

        String[] vehicleTypesArray = new String[]{"Bike", "Car", "Truck"};

        vehicleTypeSpinner = (Spinner) findViewById(R.id.vehicleType);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 android.R.layout.simple_spinner_item, vehicleTypesArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vehicleTypeSpinner.setAdapter(adapter);
    }
    private void PopulateVehicleManufacturerSpinner() {

        String[] vehicleManufacturerArray = new String[]{"Bajaj", "Honda", "Maruti", "Tata"};

        vehicleManufacturerSpinner = (Spinner) findViewById(R.id.vehicleManufacturer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, vehicleManufacturerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vehicleManufacturerSpinner.setAdapter(adapter);
    }



            @Override
            public void onClick(View view) {
                d = ProgressDialog.show(this,"Please Wait..", "Adding Vehicle");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Vehicle v = new Vehicle();
                        v.Type = vehicleTypeSpinner.getSelectedItem().toString();
                        v.Manufacturer = vehicleManufacturerSpinner.getSelectedItem().toString();
                        v.Model = ((EditText)findViewById(R.id.vehicleName)).getText().toString().trim();
                        v.RegistrationNumber = ((EditText)findViewById(R.id.vehicleRegistrationNumber)).getText().toString().trim();
                        v.Email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference vehiclesRef = databaseReference.child("Vehicles");

                        vehiclesRef.child(v.RegistrationNumber).setValue(v);
                    }
                });


                Toast.makeText(this,"Data saved", Toast.LENGTH_LONG).show();
                d.hide();

                /*Map<String, Object> docData = new HashMap<>();
                docData.put("Type", vehicleType);
                docData.put("Manufacturer",vehicleManufacturer);
                docData.put("Name", findViewById(R.id.vehicleName).toString());
                docData.put("RegistrationNumber",findViewById(R.id.vehicleRegistrationNumber).toString());*/


            }
}
