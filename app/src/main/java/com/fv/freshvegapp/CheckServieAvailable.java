package com.fv.freshvegapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CheckServieAvailable extends AppCompatActivity {

    EditText edpin;
    Button contin;
    DatabaseReference reference;
    String key;
    ImageView select;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_servie_available);

        reference = FirebaseDatabase.getInstance().getReference("pincodes");
        edpin = findViewById(R.id.pincode);
        contin = findViewById(R.id.btn_con);
        select = findViewById(R.id.select);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        selectlo();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectlo();
            }
        });

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contini();
            }
        });

    }

    private void contini() {
        if (edpin.getText().toString().equals("")){
            Toast.makeText(CheckServieAvailable.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
        }
        else {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (snapshot.exists()) {
                            UploadPojo upload = postSnapshot.getValue(UploadPojo.class);
                            if (edpin.getText().toString().toUpperCase().toUpperCase().contains(upload.getPincode())) {
                                SharedPreferences preferences = getSharedPreferences("Back",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                String pincode;
                                pincode = edpin.getText().toString().trim();
                                key = "1";
                                editor.putString("key",key);
                                editor.putString("pincode",pincode);
                                editor.apply();
                                Intent intent = new Intent(CheckServieAvailable.this, Sign_up.class);
                                Toast.makeText(CheckServieAvailable.this, "Welcome To FreshVeg", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }else if (key == null){
                                Toast.makeText(CheckServieAvailable.this, "Sorry this pincode is not our service area", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void selectlo() {
        if (ActivityCompat.checkSelfPermission(CheckServieAvailable.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            getLocation();
        }
        else {
            ActivityCompat.requestPermissions(CheckServieAvailable.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            selectlo();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){
                    try {
                        Geocoder geocoder = new Geocoder(CheckServieAvailable.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        edpin.setText(addresses.get(0).getPostalCode());
                        SharedPreferences preferences = getSharedPreferences("Data",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        key = (edpin.getText().toString());
                        editor.putString("key",key);
                        editor.apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}