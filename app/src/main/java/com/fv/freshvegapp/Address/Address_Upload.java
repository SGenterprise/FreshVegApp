package com.fv.freshvegapp.Address;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;
import com.fv.freshvegapp.UploadPojo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Address_Upload extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    TextView address1,save;
    EditText name,house,buldg,landm,num,pincodeed;
    SearchView searchView;
    String address,longi,lati,pincode;
    FusedLocationProviderClient fusedLocationProviderClient;
    CardView select;
    DatabaseReference reference,refere;
    String key,key2,check;
    String n = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_address);

        reference = FirebaseDatabase.getInstance().getReference("ADDRESS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        address1 = findViewById(R.id.ad1);
        name = findViewById(R.id.name);
        landm = findViewById(R.id.landmark);
        num = findViewById(R.id.num);
        house = findViewById(R.id.roomno_id);
        buldg = findViewById(R.id.buldg_id);
        searchView = findViewById(R.id.sv_location);
        select = findViewById(R.id.select);
        save = findViewById(R.id.save_id);
        pincodeed = findViewById(R.id.pincode_id);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        LinearLayout back = findViewById(R.id.back_update);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              backk();
            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Address_Upload.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                else {
                    ActivityCompat.requestPermissions(Address_Upload.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(Address_Upload.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getLocation();
        }
        else {
            ActivityCompat.requestPermissions(Address_Upload.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

        name.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        name.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
              house.findFocus();
                    return false;
                }

                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchView != null){
                    gMap.clear();
                String location;
                 location = searchView.getQuery().toString();
                List<Address>addressList = null;

                if (!location.equals("")||location != null) {
                    Geocoder geocoder = new Geocoder(Address_Upload.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    assert addressList != null;
                    Address addresses = addressList.get(0);
                    address = addressList.get(0).getAddressLine(0);
                    address1.setText(address);
                    LatLng latLng = new LatLng(addresses.getLatitude(),addresses.getLongitude());
                    gMap.addMarker(new MarkerOptions().position(latLng));
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    longi = String.valueOf(addresses.getLongitude());
                    lati = String.valueOf(addresses.getLatitude());
                    pincode = String.valueOf(addresses.getPostalCode());

                    if (pincode.equals(n)){
                        pincodeed.setText("");
                    }
                    else{
                        pincodeed.setText(pincode);
                    }
                 }
                }
                else {
                    Toast.makeText(Address_Upload.this, "Enter Something To Search", Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
    }

    private void backk() {
        SharedPreferences preferences = getSharedPreferences("change_address",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        key = preferences.getString("add", "");

        preferences = getSharedPreferences("address_rec",MODE_PRIVATE);
        editor = preferences.edit();
        key2 = preferences.getString("address", "");
        editor.apply();

        if (key.equals("1")){
            SharedPreferences preff = getSharedPreferences("change_address", MODE_PRIVATE);
            SharedPreferences.Editor editorr = preff.edit();
            editorr.clear();
            editorr.apply();

            Intent intent = new Intent(Address_Upload.this, Address_Select_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }else if(key2.equals("1")){
            SharedPreferences preff = getSharedPreferences("address_rec", MODE_PRIVATE);
            SharedPreferences.Editor editorr = preff.edit();
            editorr.apply();

            Intent intent = new Intent(Address_Upload.this, Firstactivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

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
                        Geocoder geocoder = new Geocoder(Address_Upload.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String ad = addresses.get(0).getAddressLine(0);
                        longi = String.valueOf(location.getLongitude());
                        lati = String.valueOf(location.getLatitude());
                        pincode = String.valueOf(addresses.get(0).getPostalCode());
                        if (pincode.equals(n)){
                            pincodeed.setText("");
                        }
                        else{
                            pincodeed.setText(pincode);
                        }

                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                        gMap.clear();
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                        gMap.addMarker(new MarkerOptions().position(latLng));
                        address1.setText(ad);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinfilter();
            }
        });
    }

    private void pinfilter() {

        if (pincodeed.getText().toString().equals("")){
            Toast.makeText(Address_Upload.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
        }
        else {
            refere = FirebaseDatabase.getInstance().getReference("pincodes");
            refere.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (snapshot.exists()) {
                            UploadPojo upload = postSnapshot.getValue(UploadPojo.class);
                            if (pincodeed.getText().toString().toUpperCase().toUpperCase().contains(upload.getPincode())) {

                                SharedPreferences preferences = getSharedPreferences("check",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                String pincode;
                                pincode = pincodeed.getText().toString().trim();
                                check = "1";
                                editor.putString("key",check);
                                editor.putString("pincode",pincode);
                                editor.apply();

                                if (
                                name.getText().toString().equals("")
                                        || landm.getText().toString().equals("") || house.getText().toString().equals("")
                                        || buldg.getText().toString().equals("") || num.getText().toString().equals("")){

                                    Toast.makeText(Address_Upload.this, "Enter all details", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    savebtn();
                                }


                            }else if (check == null){
                                Toast.makeText(Address_Upload.this, "Sorry this pincode is not our service area", Toast.LENGTH_SHORT).show();
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

    private void savebtn() {
            AddressPojo addressPojo = new AddressPojo();
            addressPojo.setName(name.getText().toString());
            addressPojo.setAddress1(address1.getText().toString());
            addressPojo.setNumber(num.getText().toString());
            addressPojo.setLandmark(landm.getText().toString());
            addressPojo.setHouseno(house.getText().toString());
            addressPojo.setBuilding(buldg.getText().toString());
            addressPojo.setLongitude(longi);
            addressPojo.setLatitude(lati);
            addressPojo.setPincode(pincodeed.getText().toString().trim());
            String uploadId = reference.push().getKey();
            addressPojo.setId(uploadId);
            reference.child(uploadId).setValue(addressPojo);

            Toast.makeText(Address_Upload.this, "Address Added", Toast.LENGTH_SHORT).show();

            backk();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                double latitude = latLng.latitude;
                double longitude = latLng.longitude;

                gMap.clear();
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                gMap.addMarker(markerOptions);

                Geocoder geocoder = new Geocoder(Address_Upload.this, Locale.getDefault());
                List<Address>addressList = null;
                try {
                    addressList = geocoder.getFromLocation(latitude,longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!(addressList.get(0).getAddressLine(0) == null)){
                    address = addressList.get(0).getAddressLine(0);
                    address1.setText(address);
                }else {
                    address1.setText("");
                }

                longi = String.valueOf(latitude);
                longi = String.valueOf(longitude);
                pincode = String.valueOf(addressList.get(0).getPostalCode());
                if (pincode.equals(n)){
                    pincodeed.setText("");
                }
                else{
                    pincodeed.setText(pincode);
                }

            }
        });
    }
}