package com.fv.freshvegapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.Products.Vege_Adaptor;
import com.fv.freshvegapp.Slider.SlideAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Main_recycleview extends Fragment {

    private RecyclerView recyclerView;

    //adapter object
    private Vege_Adaptor vege_adaptor;
//    private Deal_Adaptor deal_adaptor;
//    private Dryfruit_Adaptor dryfruit_adaptor;

    //database reference
    private DatabaseReference vegref;
    private DatabaseReference dealref;
    private DatabaseReference fruitref;
    private DatabaseReference dryref;
    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<UploadPojo> uploads;

    private SliderView sliderView;
    int setcount;
    String a = "Fresh Vegetables";
    Query dealquery,vegquery,fruitquery,dryfruquery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_recycleview, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.category_recycleview1);
      //  recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        progressDialog = new ProgressDialog(getContext());

        uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        vegref = FirebaseDatabase.getInstance().getReference("Uploads");

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

        vegquery = vegref.orderByChild("oos").startAt("instock").limitToFirst(5);

        vegquery.addValueEventListener(new ValueEventListener() {
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
                vege_adaptor = new Vege_Adaptor(getActivity(), uploads);
                vege_adaptor.notifyDataSetChanged();
                recyclerView.setAdapter(vege_adaptor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });
      DatabaseReference  ref = FirebaseDatabase.getInstance().getReference("category");
        return view;
    }
}