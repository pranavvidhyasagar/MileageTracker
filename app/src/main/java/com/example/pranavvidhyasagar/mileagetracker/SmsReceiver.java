package com.example.pranavvidhyasagar.mileagetracker;

import android.telephony.SmsMessage;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Double;

/**
 * Created by h283245 on 6/2/18.
 */

public class SmsReceiver extends BroadcastReceiver {

    //interface
    private static SmsListener mListener;
    public String regnum;
    public String check;
    String rn, totalDistance, email,manufacturer,model,type;
    float mileage,distance,amountGivenByUser;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference("primaryVehicles");
    final DatabaseReference database1 = FirebaseDatabase.getInstance().getReference("Vehicles");
    private Thread t1 = null;
    private Thread t2 = null;
boolean isAmountRec = false;
    @Override
    public void onReceive(final Context context, Intent intent) {

        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;
        String strMessage = "";
        amountGivenByUser=0;


        if (myBundle != null) {
            Object[] pdus = (Object[]) myBundle.get("pdus");

            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = myBundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                //}
                /*else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }*/
                if (messages[i].getMessageBody().contains("DEBITED") && (messages[i].getMessageBody().contains("Fuel")||messages[i].getMessageBody().contains("Petrol")||messages[i].getMessageBody().contains("Bunk"))) {
                    strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                    strMessage += " : ";
                    strMessage += messages[i].getMessageBody();
                    strMessage += "\n";
                    String partOfSms = messages[i].getMessageBody();
                    Pattern regEx
                            = Pattern.compile("(?:INR|Rs)+[\\s]*[0-9+[\\,]*+[0-9]*]+[\\.]*[0-9]+");
                    // Find instance of pattern matches
                    Matcher m = regEx.matcher(partOfSms);
                    if (m.find()) {
                        try {
                            Log.e("amount_value= ", "" + m.group(0));
                            String amount = (m.group(0).replaceAll("inr", ""));
                            amount = amount.replaceAll("Rs", "");
                            amount = amount.replaceAll("INR", "");
                            amount = amount.replaceAll(" ", "");
                            amount = amount.replaceAll(",", "");
                            amountGivenByUser = (Float.valueOf(amount));
                            isAmountRec = true;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    check = ""+amountGivenByUser;
                    mListener.messageReceived(strMessage);
                    if(isAmountRec)
                    initThreads();

                }

            }
        }
    }

    public static void bindListener (SmsListener listener){
        mListener = listener;
    }

    public void initThreads() {

        t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot vehicleSnapshot: dataSnapshot.getChildren()){
                            Email v = vehicleSnapshot.getValue(Email.class);

                            if(v.email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())){
                                regnum = vehicleSnapshot.getKey();
                            }
                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
                database1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot vehicleSnapshot: dataSnapshot.getChildren()){
                            VehicleCard v = vehicleSnapshot.getValue(VehicleCard.class);
                            String email = v.email;
                            if(email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())){

                                VehicleCard v1 = new VehicleCard();
                                v1 = v;
                                if(v1.distance != 0f)
                                    v1.mileage = v1.distance/(amountGivenByUser/80);
                                v1.distance = 0;
                                database1.child(vehicleSnapshot.getKey()).setValue(v1);
                                isAmountRec = false;

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });
        t1.start();
        t2.start();
    }
}

