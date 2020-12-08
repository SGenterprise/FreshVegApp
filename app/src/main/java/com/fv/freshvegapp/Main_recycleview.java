package com.fv.freshvegapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.Slider.SlideAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main_recycleview extends Fragment {

    private RecyclerView recyclerView;

    //adapter object
    private Main_Adaptor adapter;

    //database reference
    private DatabaseReference mDatabase;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<UploadPojo> uploads;

    private SliderView sliderView;
    int setcount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_recycleview, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.category_recycleview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());

        uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Uploads");

        FirebaseDatabase.getInstance().getReference("Slider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    sliderView.setVisibility(View.VISIBLE);
                    Long counts = snapshot.getChildrenCount();
                    setcount = counts.intValue();
                    sliderView.setSliderAdapter(new SlideAdapter(getContext(),setcount));
                }
                else {
                    sliderView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        sliderView = view.findViewById(R.id.imageSlider);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadPojo upload = postSnapshot.getValue(UploadPojo.class);

                    // Get an iterator.
                    Iterator<UploadPojo> ite = uploads.iterator();
                    while(ite.hasNext()) {
                        UploadPojo iteValue = ite.next();
                        if (upload != null && iteValue.getCategory_img().equals(upload.getCategory_img()) && iteValue.getCategory().equals(upload.getCategory())

                        ){
                                ite.remove();

                        }
                    }
                    uploads.add(upload);

                }
                //creating adapter
                adapter = new Main_Adaptor(getActivity(), uploads);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }

        });

      DatabaseReference  ref = FirebaseDatabase.getInstance().getReference("category");

        return view;

    }
}