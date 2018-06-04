package com.example.pranavvidhyasagar.mileagetracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseTooManyRequestsException;
import java.util.HashMap;
import java.util.Map;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner vehicleTypeSpinner, vehicleManufacturerSpinner;
    EditText editTextVehicleModel, editTextRegistrationNumber;
    CheckBox primaryCheck;

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


    private Thread t1 = null;
    private Thread t2 = null;
    final int[] temp = new int[1];
    final String[] RegistrationNum = new String[1];
    final int[] pc = new int[1];
            @Override
            public void onClick(View view) {

                initThreads();

                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(temp[0] == 1) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setTitle("Vehicle Added")
                            .setMessage("Do you want to add another vehicle?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editTextRegistrationNumber.setText("");
                                    editTextVehicleModel.setText("");
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(AddVehicleActivity.this,MainNavigationActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                    Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
                }

            }
    public void initThreads() {

        t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                int flag = 1;
                editTextVehicleModel = ((EditText)findViewById(R.id.vehicleName));
                editTextRegistrationNumber = ((EditText)findViewById(R.id.vehicleRegistrationNumber));
                primaryCheck = (CheckBox)findViewById(R.id.primaryVehicle);

                String Type = vehicleTypeSpinner.getSelectedItem().toString();
                String Manufacturer = vehicleManufacturerSpinner.getSelectedItem().toString();
                String Model = editTextVehicleModel.getText().toString().trim();
                String RegistrationNumber =editTextRegistrationNumber.getText().toString().trim();
                String Email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                float Mileage = 0;
                float Distance = 0;

                VehicleCard v = new VehicleCard(Email,Manufacturer,Model,Type,Mileage,Distance);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference vehiclesRef = databaseReference.child("Vehicles");
                if(v.model.isEmpty()){
                    editTextVehicleModel.setError("Required");
                    editTextVehicleModel.requestFocus();
                    flag =0;
                }
                if(RegistrationNumber.isEmpty()){
                    editTextRegistrationNumber.setError("Required");
                    editTextRegistrationNumber.requestFocus();
                    flag=0;
                }
                if(!Pattern.matches("^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{4}$",RegistrationNumber)){
                    editTextRegistrationNumber.setError("Eg:TN38AB1234");
                    editTextRegistrationNumber.requestFocus();
                    flag=0;
                }
                if(primaryCheck.isChecked()){
                    pc[0] = 1;
                }
                else
                    pc[0]=0;
                if(flag==1){
                    RegistrationNum[0] = RegistrationNumber;
                    vehiclesRef.child(RegistrationNumber).setValue(v);
                    temp[0] =flag;
                }
            }
        });

        t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference pvRef = databaseReference.child("primaryVehicles");
                primaryVehicle pv = new primaryVehicle();
                if(pc[0]==1 && temp[0]==1){
                    pv.email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    pvRef.child(RegistrationNum[0]).setValue(pv);
                }
            }

        });
        t1.start();
        t2.start();
    }
}
