package com.fv.freshvegapp.Address;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Address_Update extends AppCompatActivity implements OnMapReadyCallback {

    TextView address1,save;
    EditText name,house,buldg,landm,num,pincodeed;
    private DatabaseReference mDatabase;
    String address;
    FusedLocationProviderClient fusedLocationProviderClient;
    CardView select;
    DatabaseReference reference,refere;
    String cat;
    GoogleMap gMap;
    SearchView searchView;
    String n1,ad1,num1,l1,h1,b1,back3,lati,longi,id,pincode,check;
    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_address);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);

        LinearLayout back = findViewById(R.id.back_update);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backpress();

            }
        });

        pincodeed  = findViewById(R.id.pincode_id);
        address1 = findViewById(R.id.ad1);
        name = findViewById(R.id.name);
        landm = findViewById(R.id.landmark);
        num = findViewById(R.id.num);
        house = findViewById(R.id.roomno_id);
        buldg = findViewById(R.id.buldg_id);
        select = findViewById(R.id.select);
        searchView = findViewById(R.id.sv_location);
        save = findViewById(R.id.save_id);

        SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        n1 = preferences.getString( "name","");
        ad1 = preferences.getString( "add1","");
        num1 = preferences.getString( "num","");
        l1 = preferences.getString( "landmark","");
        h1 = preferences.getString( "houseno","");
        b1 = preferences.getString( "bu","");
        lati = preferences.getString("lati",lati);
        longi = preferences.getString("longi",longi);
        pincode  = preferences.getString("pincode",pincode);
        id = preferences.getString("id",id);
        SharedPreferences.Editor editor = preferences.edit();
        editor.apply();

        name.setText(n1);
        address1.setText(ad1);
        num.setText(num1);
        landm.setText(l1);
        house.setText(h1);
        buldg.setText(b1);
        pincodeed.setText(pincode);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Address_Update.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                else {
                    ActivityCompat.requestPermissions(Address_Update.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(Address_Update.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getLocation();
        }
        else {
            ActivityCompat.requestPermissions(Address_Update.this
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
                    Geocoder geocoder = new Geocoder(Address_Update.this);
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
                    pincodeed.setText(pincode);
                 }
                }
                else {
                    Toast.makeText(Address_Update.this, "Enter Something To Search", Toast.LENGTH_SHORT).show();
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

    private void backpress() {
        SharedPreferences preferences = getSharedPreferences("back3",MODE_PRIVATE);
        back3 = preferences.getString( "name","");
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.apply();

        if (back3 != null && back3.equals("1")) {
            Intent i = new Intent(Address_Update.this, Address_Select_Activity.class);
            startActivity(i);
            SharedPreferences preferenc = getSharedPreferences("back3", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferenc.edit();
            back3 = "";
            edit.putString("name",back3);
            edit.apply();
            finish();

        }else {
            SharedPreferences preferenc = getSharedPreferences("address_rec", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferenc.edit();
            String add = "1";
            edit.putString("address",add);
            edit.apply();
            Intent i = new Intent(Address_Update.this, Firstactivity.class);
            startActivity(i);
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
                        Geocoder geocoder = new Geocoder(Address_Update.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String ad = addresses.get(0).getAddressLine(0);

                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        longi = String.valueOf(location.getLongitude());
                        lati = String.valueOf(location.getLatitude());
                        pincode = String.valueOf(addresses.get(0).getPostalCode());
                        pincodeed.setText(pincode);

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
            Toast.makeText(Address_Update.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
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

                                    Toast.makeText(Address_Update.this, "Enter all details", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    update();
                                }


                            }else if (check == null){
                                Toast.makeText(Address_Update.this, "Sorry this pincode is not our service area", Toast.LENGTH_SHORT).show();
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

    private void update() {
        if (name.getText().toString().equals("")
                || landm.getText().toString().equals("") || house.getText().toString().equals("")
                || buldg.getText().toString().equals("") || num.getText().toString().equals("")) {

            Toast.makeText(Address_Update.this, "Enter all details", Toast.LENGTH_SHORT).show();
        } else {
            final String adn = address1.getText().toString().trim();
            final String namen = name.getText().toString().trim();
            final String numn = num.getText().toString().trim();
            final String hou = house.getText().toString().trim();
            final String bul = buldg.getText().toString().trim();
            final String lan = landm.getText().toString().trim();

            reference = FirebaseDatabase.getInstance().getReference("ADDRESS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            Query query = reference.orderByChild("id").equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (!address1.getText().toString().equals("")) {
                            ds.getRef().child("address1").setValue(adn);
                        }
                        if (!name.getText().toString().equals("")) {
                            ds.getRef().child("name").setValue(namen);
                        }
                        if (!num.getText().toString().equals("")) {
                            ds.getRef().child("number").setValue(numn);
                        }
                        if (!house.getText().toString().equals("")) {
                            ds.getRef().child("houseno").setValue(hou);
                        }
                        if (!buldg.getText().toString().equals("")) {
                            ds.getRef().child("building").setValue(bul);
                        }
                        if (!landm.getText().toString().equals("")) {
                            ds.getRef().child("landmark").setValue(lan);
                        }
                        ds.getRef().child("pincode").setValue(pincode);
                        ds.getRef().child("latitude").setValue(lati);
                        ds.getRef().child("longitude").setValue(longi);

                        Toast.makeText(Address_Update.this, "Address Updated Sucessfully", Toast.LENGTH_SHORT).show();
                        backpress();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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

                Geocoder geocoder = new Geocoder(Address_Update.this, Locale.getDefault());
                List<Address>addressList = null;
                try {
                    addressList = geocoder.getFromLocation(Double.parseDouble(lati),Double.parseDouble(longi),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert addressList != null;
                address = addressList.get(0).getAddressLine(0);
                lati = String.valueOf(latitude);
                longi = String.valueOf(longitude);
                pincode = String.valueOf(addressList.get(0).getPostalCode());
                pincodeed.setText(pincode);
                Toast.makeText(Address_Update.this,pincode, Toast.LENGTH_SHORT).show();
                address1.setText(address);
            }
        });
    }


    public void onBackPressed() {

    }
}