package com.example.iotcp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView textViewTemp, textViewHumidity, textViewLR, textViewLRFanStatus, textViewGas, textViewKitchenAlarm, textViewKitchen;
    TextView textViewGarden, textViewAQI, textViewLEDstate;
    Button btnLRON, btnLROFF, btnKitchenON, btnKitchenOFF, btnMQon, btnMQoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTemp = findViewById(R.id.textViewTemp);
        textViewHumidity = findViewById(R.id.textViewHum);
        textViewLR = findViewById(R.id.textViewLR);
        textViewLRFanStatus = findViewById(R.id.textViewLRFanStatus);
        btnLRON = findViewById(R.id.btnLRON);
        btnLROFF = findViewById(R.id.btnLROFF);

        textViewKitchen = findViewById(R.id.textViewKitchen);
        textViewGas = findViewById(R.id.textViewGas);
        textViewKitchenAlarm = findViewById(R.id.textViewKitchenAlarm);
        btnKitchenON = findViewById(R.id.btnKitchenON);
        btnKitchenOFF = findViewById(R.id.btnKitchenOFF);

        textViewGarden = findViewById(R.id.textViewMQ2);
        textViewAQI = findViewById(R.id.textViewMQ4);
        textViewLEDstate = findViewById(R.id.textViewMQ6);
        btnMQon = findViewById(R.id.btnMQ1);
        btnMQoff = findViewById(R.id.btnMQ2);

        //*********************************************Living Room************************************************
        btnLRON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("DHT11");

                myRef.child("LED").setValue("1");
            }
        });

        btnLROFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("DHT11");

                myRef.child("LED").setValue("0");
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("DHT11");

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp = snapshot.child("Temperature").getValue().toString();
                String hum = snapshot.child("Humidity").getValue().toString();
                String LED = snapshot.child("LED").getValue().toString();

                textViewTemp.setText(temp);
                textViewHumidity.setText(hum);

                if (Float.parseFloat(temp) > 40.0 || Float.parseFloat(hum) > 35.0){
                    textViewLR.setText("Exceeded");
                }
                else {
                    textViewLR.setText("Normal");
                }

                if (LED.equals("1")){
                    textViewLRFanStatus.setText("ON");
                }
                else {
                    textViewLRFanStatus.setText("OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //*********************************************Living Room************************************************

        //*********************************************Kitchen************************************************
        btnKitchenON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("MQ3");

                myRef.child("Alarm").setValue("1");
            }
        });

        btnKitchenOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("MQ3");

                myRef.child("Alarm").setValue("0");
            }
        });

        DatabaseReference KRef = database.getReference("MQ3");

        KRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String gas = snapshot.child("Alcohol").getValue().toString();
                String Alarm = snapshot.child("Alarm").getValue().toString();

                textViewGas.setText(gas);

                if (Float.parseFloat(Alarm) > 200.0){
                    textViewKitchen.setText("Exceeded");
                }
                else {
                    textViewLR.setText("Normal");
                }

                if (Alarm.equals("1")){
                    textViewKitchenAlarm.setText("ON");
                }
                else {
                    textViewKitchenAlarm.setText("OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //*********************************************Kitchen************************************************

        //*********************************************Garden************************************************
        btnMQon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("MQ135");

                myRef.child("LED").setValue("1");
            }
        });

        btnMQoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("MQ135");

                myRef.child("LED").setValue("0");
            }
        });

        DatabaseReference GardenRef = database.getReference("MQ135");

        GardenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String AQI = snapshot.child("Air Quality").getValue().toString();
                String LED = snapshot.child("LED").getValue().toString();

                textViewAQI.setText(AQI);

                if (Float.parseFloat(AQI) > 100.0){
                    textViewGarden.setText("Exceeded");
                }
                else {
                    textViewGarden.setText("Normal");
                }

                if (LED.equals("1")){
                    textViewLEDstate.setText("ON");
                }
                else {
                    textViewLEDstate.setText("OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //*********************************************Garden************************************************
    }
}