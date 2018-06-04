package com.example.pranavvidhyasagar.mileagetracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

public class LineGraphActivity extends AppCompatActivity {

    private LineChart mChart;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Vehicles");
    List<VehicleCard> VehicleList;
    Dictionary<Float,Float> dc = new Dictionary<Float, Float>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Enumeration<Float> keys() {
            return null;
        }

        @Override
        public Enumeration<Float> elements() {
            return null;
        }

        @Override
        public Float get(Object o) {
            return null;
        }

        @Override
        public Float put(Float aFloat, Float aFloat2) {
            return null;
        }

        @Override
        public Float remove(Object o) {
            return null;
        }
    } ;
    private Thread t1 = null;
    private Thread t2 = null;
    ArrayList<Entry> yValues = new ArrayList<>();


    @Override
    protected void onStart() {
        super.onStart();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        mChart = (LineChart) findViewById(R.id.lineChart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        initThreads();
        try {
            t1.join();
            //t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //yValues.add(new Entry(10,69f));
        mChart = (LineChart) findViewById(R.id.lineChart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(10,69f));
        yValues.add(new Entry(20,79f));
        yValues.add(new Entry(30,89f));
        yValues.add(new Entry());
        yValues.add(new Entry(40,99));
        yValues.add(new Entry(50,109f));
        yValues.add(new Entry(60,119f));
        yValues.add(new Entry(70,129f));
        LineDataSet set1 = new LineDataSet(yValues,"Data Set 1");
        set1.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mChart.setData(data);

    }

    public void initThreads() {


        t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        float i=0;
                        for(DataSnapshot vehicleSnapshot: dataSnapshot.getChildren()){
                            VehicleCard v = vehicleSnapshot.getValue(VehicleCard.class);
                            yValues.add(new Entry(Float.valueOf(i),Float.valueOf(v.mileage)));
                            //float f = Float.valueOf(v.mileage.trim()).floatValue();
                            //Toast.makeText(getApplicationContext(),v.email,Toast.LENGTH_LONG).show();
                            i++;
                            //dc.put(Float.valueOf(i),Float.valueOf(v.mileage));
                            //Toast.makeText(getApplicationContext(),String.valueOf(v.mileage),Toast.LENGTH_LONG).show();
                            //yValues.add(new Entry(i++,v.mileage));

                        }
                        // yValues.add(new Entry(10,69f));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        /*t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                float j=0;
                for(float i=0;i<dc.size();i++){
                    j++;
                    Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
                    yValues.add(new Entry(Float.valueOf(j),dc.get(Float.valueOf(i))));
                    //Toast.makeText(getApplicationContext(),String.valueOf(dc.get(Float.valueOf(i))),Toast.LENGTH_SHORT).show();
                }
            }

        });*/
        t1.start();
        //t2.start();
    }
}
