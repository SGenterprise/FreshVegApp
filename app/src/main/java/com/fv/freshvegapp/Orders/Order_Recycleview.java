package com.fv.freshvegapp.Orders;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

public class Order_Recycleview extends Fragment {

    private RecyclerView recyclerView;
    private Order_Adaptor adapter;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private List<Order_pojo> uploads;
    private EditText searchedit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_recycleview, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleorder);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(getContext());

        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                uploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Order_pojo upload = postSnapshot.getValue(Order_pojo.class);
                    uploads.add(upload);
                }
                adapter = new Order_Adaptor(getActivity(), uploads);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }

        });

        return view;
    }
}