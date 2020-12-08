package com.fv.freshvegapp.Coupons;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Coupon_Select_Activity extends AppCompatActivity {

    TextView btnadd,appBarTV;
    private RecyclerView recyclerView;
    private Coupons_Adaptor adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference reff;
    private ProgressDialog progressDialog;
    private List<coupanpojo> uploads;
    private EditText searchedit;
    RelativeLayout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_recycleview);
        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        LinearLayout back = findViewById(R.id.back_update);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnBackPressed();
            }
        });

        appBarTV = findViewById(R.id.appbar_text_view);
        appBarTV.setText("Coupons");
        checkout = (RelativeLayout) findViewById(R.id.checkoutlay);
        recyclerView = (RecyclerView) findViewById(R.id.product_recycleview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchedit = findViewById(R.id.search_edit_box);
        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Uploads");
        reff = FirebaseDatabase.getInstance().getReference("Coupons");

//for oos downlist

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dismissing the progress dialog
                uploads.clear();
                progressDialog.dismiss();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    coupanpojo upload = postSnapshot.getValue(coupanpojo.class);

                    uploads.add(upload);

                }
                adapter = new Coupons_Adaptor(Coupon_Select_Activity.this, uploads);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
    private void OnBackPressed() {
        super.onBackPressed();
    }
}