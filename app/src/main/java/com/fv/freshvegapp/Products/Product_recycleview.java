package com.fv.freshvegapp.Products;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.Cart.Cart_Fragment;
import com.fv.freshvegapp.R;
import com.fv.freshvegapp.UploadPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Product_recycleview extends Fragment {

    private RecyclerView recyclerView;
    private Product_Adaptor adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference reff;
    private ProgressDialog progressDialog;
    private List<UploadPojo> uploads;
    private EditText searchedit;
    RelativeLayout checkout;
    String a = "Fresh Vegetables";
    Query queryss;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_recycleview, container, false);

        checkout = (RelativeLayout) view.findViewById(R.id.checkoutlay);
        recyclerView = (RecyclerView) view.findViewById(R.id.product_recycleview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchedit = view.findViewById(R.id.search_edit_box);
        progressDialog = new ProgressDialog(getContext());

        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Uploads");
        reff = FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

//        SharedPreferences preferences = getActivity().getSharedPreferences("MYPREFS",MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        a = preferences.getString("cat123", "");
//        editor.apply();

//for oos downlist
        queryss = mDatabase.orderByChild("oos").startAt("instock");

        queryss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dismissing the progress dialog
                uploads.clear();
                progressDialog.dismiss();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadPojo upload = postSnapshot.getValue(UploadPojo.class);
                    if (upload.getCategory().toUpperCase().contains(a.toUpperCase()))
                    {
                        uploads.add(upload);
                    }
                }
                adapter = new Product_Adaptor(getActivity(), uploads);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });

        searchedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getAllPass(s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
//                filter(s.toString());
                if (s.toString().equals("")){
                    getAllPass(s.toString());
                }
                else {
                    searchPass(s.toString());
                }
            }
        });

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    checkout.setVisibility(View.VISIBLE);
                }
                else {
                    checkout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Cart_Fragment cart_fragment = new Cart_Fragment();
                ft.replace(R.id.f_container, cart_fragment);
                ft.commit();
            }
        });


        return view;

    }

    private void searchPass(final String text) {
        queryss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dismissing the progress dialog
                uploads.clear();
                progressDialog.dismiss();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadPojo upload = postSnapshot.getValue(UploadPojo.class);
                    if (
//                            for category
                            upload.getCategory().toUpperCase().contains(a.toUpperCase()) &&
                            upload.getProduct().toUpperCase().contains(text.toUpperCase())
                    )
                    {
                        uploads.add(upload);
                    }
                }
                adapter = new Product_Adaptor(getActivity(), uploads);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });
    }

    private void getAllPass(CharSequence s) {

        queryss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dismissing the progress dialog
                uploads.clear();
                progressDialog.dismiss();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadPojo upload = postSnapshot.getValue(UploadPojo.class);
                    if (upload.getCategory().toUpperCase().contains(a.toUpperCase()))
                    {
                        uploads.add(upload);
                    }
                }
                adapter = new Product_Adaptor(getActivity(), uploads);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });
    }

//    private void filter(String text) {
//
//        ArrayList<UploadPojo> filteredList = new ArrayList<>();
//        for (UploadPojo item : uploads) {
//            if (item.getProduct().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//        adapter.filterList(filteredList);
//    }
}