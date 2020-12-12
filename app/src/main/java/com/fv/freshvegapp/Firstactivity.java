package com.fv.freshvegapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fv.freshvegapp.Address.Address_Recycleview;
import com.fv.freshvegapp.Cart.Cart_Fragment;
import com.fv.freshvegapp.ContactUs.Contact_fragment;
import com.fv.freshvegapp.Orders.Order_Recycleview;
import com.fv.freshvegapp.Products.Product_recycleview;
import com.fv.freshvegapp.Profile_fragment.ProFileFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class Firstactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "";
    private DrawerLayout drawer;
    private TextView appBarTV,name;
    private long backPressedTime;
    private Toast backToast;
    String key, k,cart,select_cart,orderkey,couponkey;
    ImageView imageView;
    String url,img;
    RelativeLayout cartlay;
    CardView cardred;
    DatabaseReference reff,reffaddr;
    String C = null,D = null,number = null;
    TextView addresstext;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstactivity);
        appBarTV = findViewById(R.id.appbar_text_view);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.setSubscription(false);

        Intent intent = getIntent();
        number = intent.getStringExtra("phone");
        SharedPreferences preferences = getSharedPreferences("address_rec",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        key = preferences.getString("address", "");
        editor.apply();

        SharedPreferences preffx = getSharedPreferences("coupon",MODE_PRIVATE);
        SharedPreferences.Editor editorrfr = preffx.edit();
        couponkey = preffx.getString("couponcode", "");
        editorrfr.apply();

        SharedPreferences preff = getSharedPreferences("Cart_Items",MODE_PRIVATE);
        SharedPreferences.Editor editorrr = preff.edit();
        cart = preff.getString("cart", "");
        editorrr.apply();

        SharedPreferences prefselectcart = getSharedPreferences("cart_select",MODE_PRIVATE);
        SharedPreferences.Editor editorrrcart = prefselectcart.edit();
        select_cart = prefselectcart.getString("selectedcart", "");
        editorrrcart.apply();

        SharedPreferences preferencesorder = getSharedPreferences("Myorder",MODE_PRIVATE);
        SharedPreferences.Editor editordorder = preferencesorder.edit();
        orderkey = preferencesorder.getString("order", "");
        editordorder.apply();

        if (key.equals("1")){
            address();
            preferences = getSharedPreferences("address_rec", MODE_PRIVATE);
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.clear();
            editorr.apply();
        }
        else if (couponkey.equals("1")){
            cart_frag();
            SharedPreferences prefffj = getSharedPreferences("coupon", MODE_PRIVATE);
            SharedPreferences.Editor editorr = prefffj.edit();
            editorr.clear();
            editorr.apply();
        }else if (cart.equals("1")){
            cart_frag();
            SharedPreferences prefc = getSharedPreferences("Cart_Items", MODE_PRIVATE);
            SharedPreferences.Editor editorr = prefc.edit();
            editorr.clear();
            editorr.apply();
        }
        else if (select_cart.equals("1")){
            cart_frag();
            SharedPreferences prefselectcartr = getSharedPreferences("cart_select", MODE_PRIVATE);
            SharedPreferences.Editor editorrdd = prefselectcartr.edit();
            appBarTV.setText("Cart");
            editorrdd.clear();
            editorrdd.apply();
        }
        else if (orderkey.equals("1")){
            order_frag();
            SharedPreferences preferencesorders = getSharedPreferences("Myorder", MODE_PRIVATE);
            SharedPreferences.Editor editororderss = preferencesorders.edit();
            appBarTV.setText("My Orders");
            editororderss.clear();
            editororderss.apply();
        }
        else if (number != null){
            if (number.equals("2")){
                intent.putExtra("phone","");
                order_frag();
            }
        }else{
            showhome();
        }

        reff = FirebaseDatabase.getInstance().getReference("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reffaddr = FirebaseDatabase.getInstance().getReference("Address").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        imageView = findViewById(R.id.img_id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel("m","m", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager =  getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartlay = findViewById(R.id.cart_lay_id);
        cardred =(CardView) findViewById(R.id.noti_red_cart);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        cartlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Cart_Fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
                appBarTV.setText("Cart");
            }
        });

        ImageButton menuRight = findViewById(R.id.leftRight);
        ImageButton cart = findViewById(R.id.Cart_id);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    cartlay.setVisibility(View.GONE);
                    fragment = new Cart_Fragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
                appBarTV.setText("Cart");

            }
        });

        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

//        showhome();
        cartlay.setVisibility(View.VISIBLE);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.name__id);
        final ImageView currentlocation = (ImageView) headerView.findViewById(R.id.currentlocation);
        final ImageView editcurrentlocation = (ImageView) headerView.findViewById(R.id.editcurrentlocation);
        addresstext = (TextView) headerView.findViewById(R.id.address);
        preferences = getSharedPreferences("Data",MODE_PRIVATE);
        String cat = preferences.getString( "key","");
        editor = preferences.edit();
        editor.apply();
        addresstext.setText(cat);

//        currentlocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (ActivityCompat.checkSelfPermission(Firstactivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//                    getLocation();
//                }
//                else {
//                    ActivityCompat.requestPermissions(Firstactivity.this
//                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
//                }
//
//            }
//        });

        editcurrentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        reffaddr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        D = Objects.requireNonNull(ds.child("currentAddress").getValue()).toString();
                        if (D.equals("")){
                            addresstext.setText("Get Current location ");
                        }
                        else {
                            addresstext.setText(D);
                        }

                    }

                    // Add address also here
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        C = Objects.requireNonNull(ds.child("name").getValue()).toString();
                        if (C.equals("")){
                            navUsername.setText("No Name");
                        }
                        else {
                            navUsername.setText(C);
                        }

                    }

                    // Add address also here
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
    }

    private void cart_frag() {
        fragment = new Cart_Fragment();
        cart();
        if (fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();

        }
    }
    private void order_frag() {
        fragment = new Order_Recycleview();
        cart();
        if (fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
        }
    }

    private void address() {

            fragment = new Address_Recycleview();
            cart();
            if (fragment != null){
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
            }
    }


//    @SuppressLint("MissingPermission")
//    private void getLocation() {
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                Location location = task.getResult();
//                if(location != null){
//                    try {
//                        Geocoder geocoder = new Geocoder(Firstactivity.this, Locale.getDefault());
//                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                        addresstext.setText(addresses.get(0).getSubLocality()+","+addresses.get(0).getLocality()+".");
//                        reffaddr.child("Address").child("currentAddress").setValue(addresses.get(0).getSubLocality()+","+addresses.get(0).getLocality()+".");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });
//    }

//Category
    private void showhome() {
        fragment = new Main_recycleview();
        cart();
        if (fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
            appBarTV.setText("FreshVeg");
        }
    }

    // direct products screen
//    private void showhome() {
//        fragment = new Product_recycleview();
//        cart();
//        if (fragment != null){
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
//            appBarTV.setText("FreshVeg");

//        }
//    }
    private void cart() {
        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    cardred.setVisibility(View.VISIBLE);
                }
                else{
                    cardred.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    Fragment fragment = null;

    @SuppressLint("SetWorldReadable")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawers();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        int id = item.getItemId();

        if (id == R.id.home) {
            appBarTV.setText("FreshVeg");
            fragment = new Main_recycleview();
            cart();
            cartlay.setVisibility(View.VISIBLE);

        } else if (id == R.id.Profile) {
            appBarTV.setText("Profile");
            fragment = new ProFileFragment();
            cartlay.setVisibility(View.GONE);

        } else if (id == R.id.cart) {
            appBarTV.setText("Cart");
            fragment = new Cart_Fragment();
      //      cartlay.setVisibility(View.GONE);
        } else if (id == R.id.orders) {
            appBarTV.setText("My Orders");
            fragment = new Order_Recycleview();

            //  cartlay.setVisibility(View.GONE);
        }
        else if (id == R.id.address) {
            appBarTV.setText("Address");
            fragment = new Address_Recycleview();

            //  cartlay.setVisibility(View.GONE);
        }
        else if (id == R.id.contactus) {
            appBarTV.setText("FreshVeg");
            fragment = new Contact_fragment();
            //   cartlay.setVisibility(View.GONE);

        }
        else if (id == R.id.privacy) {
            appBarTV.setText("Private Policy");
            fragment = new private_policy();
            //   cartlay.setVisibility(View.GONE);

        }
        else if (id == R.id.share) {
            Drawable drawable = imageView.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            try {
                File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + "logo.png");
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                file.setReadable(true, false);
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("bbbback/png");
                intent.setType("text/plain");
                String shareBody = "Download Our App now :- https://play.google.com/store/apps/details?id=com.fv.freshvegapp&hl=en";
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(intent, "Share bbbback via"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (id == R.id.logout) {
            new AlertDialog.Builder(Firstactivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Do you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth fAuth = FirebaseAuth.getInstance();
                            fAuth.signOut();
                            Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(Firstactivity.this, Sign_up.class);
                            startActivity(intent1);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.f_container, fragment, fragment.getTag()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
        @Override
        public void onBackPressed() {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (fragment instanceof Product_recycleview) {

                    cartlay.setVisibility(View.VISIBLE);
                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        backToast.cancel();
                        super.onBackPressed();

                    } else {
                        backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
                        backToast.show();
                    }
                    backPressedTime = System.currentTimeMillis();
                } else {

//                    if (key.equals("1")) {
//                        showproject();
//                    } else {
                        showhome();
//                    }
                }
            }
    }
}