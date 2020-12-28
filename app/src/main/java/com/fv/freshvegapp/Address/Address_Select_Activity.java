package com.fv.freshvegapp.Address;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Address_Select_Activity extends AppCompatActivity {

    TextView btnadd,appBarTV;
    private RecyclerView recyclerView;
    private Select_Address_Adaptor adapter;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private List<AddressPojo> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_address_recycleview);
        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        LinearLayout back = findViewById(R.id.back_update);

        SharedPreferences delpre = getSharedPreferences("checkk",MODE_PRIVATE);
        SharedPreferences.Editor editorlp = delpre.edit();
        editorlp.putString("key","");
        editorlp.apply();

        SharedPreferences delpree = getSharedPreferences("checkk",MODE_PRIVATE);
        SharedPreferences.Editor editorlpp = delpree.edit();
        editorlpp.putString("keyy","");
        editorlpp.apply();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        SharedPreferences preferences = getSharedPreferences("cart_select", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String add = "1";
                editor.putString("selectedcart",add);
                editor.apply();

            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            Intent i = new Intent(Address_Select_Activity.this, Firstactivity.class);
            startActivity(i);
            }
        });
        appBarTV = findViewById(R.id.appbar_text_view);
        appBarTV.setText("Select Address");
        btnadd = findViewById(R.id.newadd);
        recyclerView = (RecyclerView) findViewById(R.id.recycleaddress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference("ADDRESS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                uploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    AddressPojo upload = postSnapshot.getValue(AddressPojo.class);
                    uploads.add(upload);
                }
                adapter = new Select_Address_Adaptor(Address_Select_Activity.this, uploads);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }

        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("change_address", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String add = "1";
                editor.putString("add",add);
                editor.apply();

                Intent intent = new Intent(Address_Select_Activity.this, Address_Upload.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}