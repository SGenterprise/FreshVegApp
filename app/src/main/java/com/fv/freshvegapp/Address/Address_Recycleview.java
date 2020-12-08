package com.fv.freshvegapp.Address;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Address_Recycleview extends Fragment {

    TextView btnadd;
    private RecyclerView recyclerView;
    private Address_Adaptor adapter;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private List<AddressPojo> uploads;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_recycleview, container, false);

        btnadd = view.findViewById(R.id.newadd);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleaddress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());

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
                adapter = new Address_Adaptor(getActivity(), uploads);
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
                SharedPreferences preferences = getActivity().getSharedPreferences("address_rec", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String add = "1";
                editor.putString("address",add);
                editor.apply();
                Intent intent = new Intent(getContext(), Address_Upload.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}