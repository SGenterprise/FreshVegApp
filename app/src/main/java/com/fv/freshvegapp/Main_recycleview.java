package com.fv.freshvegapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.Products.Deal_Adaptor;
import com.fv.freshvegapp.Products.Dryfruit_Adaptor;
import com.fv.freshvegapp.Products.Fruit_Adaptor;
import com.fv.freshvegapp.Products.Product_recycleview;
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

import static android.content.Context.MODE_PRIVATE;

public class Main_recycleview extends Fragment {

    private RecyclerView recyclerView,deal_recyclerView,fruit_recyclerView,dry_fruit_recyclerView;

    //adapter object
    private Vege_Adaptor vege_adaptor;
    private Deal_Adaptor deal_adaptor;
    private Fruit_Adaptor fruit_adaptor;
    private Dryfruit_Adaptor dryfruit_adaptor;

    TextView vegtit,dealtit,fruittit,drytit;

    //database reference
    private DatabaseReference ref;
    private DatabaseReference dealref;
    private DatabaseReference fruitref;
    private DatabaseReference dryref;
    SharedPreferences preferences;
    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<UploadPojo> uploads, dealuploads,fruituploads,dryfruituploads;

    private SliderView sliderView;
    int setcount;
    CardView vege,dealcard,fruitcard,dryfruitcard,vegeviewall,fruitviewall;
    String a = "Fresh Vegetables",b = "Deals",c ="Fruits",d = "Dry Fruits";
    Query dealquery,vegquery,fruitquery,dryfruquery;

    public Main_recycleview() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_recycleview, container, false);

        vegeviewall  = (CardView) view.findViewById(R.id.freshvege_card);
        fruitviewall  = (CardView) view.findViewById(R.id.freshfruit_card);
        recyclerView = (RecyclerView) view.findViewById(R.id.category_recycleview1);
        deal_recyclerView = (RecyclerView) view.findViewById(R.id.deal_recycleview1);
        fruit_recyclerView = (RecyclerView) view.findViewById(R.id.fruit_recycleview);
        dry_fruit_recyclerView = (RecyclerView) view.findViewById(R.id.dryfruit_recycleview1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        deal_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fruit_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dry_fruit_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        vegtit = (TextView) view.findViewById(R.id.vegtit);
        dealtit = (TextView) view.findViewById(R.id.dealtit);
        fruittit = (TextView) view.findViewById(R.id.fruittit);
        drytit = (TextView) view.findViewById(R.id.drytit);

        progressDialog = new ProgressDialog(getContext());
        vege =view.findViewById(R.id.cardveg);
        dealcard=view.findViewById(R.id.dealcard);
        fruitcard=view.findViewById(R.id.fruitcard);
        dryfruitcard=view.findViewById(R.id.dryfruitcard);
        uploads = new ArrayList<>();
        dealuploads = new ArrayList<>();
        fruituploads = new ArrayList<>();
        dryfruituploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ref = FirebaseDatabase.getInstance().getReference("Uploads");
        dealref = FirebaseDatabase.getInstance().getReference("Deals");

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

        vegquery = ref.orderByChild("oos").startAt("instock").limitToFirst(8);
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
                if (vege_adaptor.getItemCount() == 0){
                    recyclerView.setVisibility(View.GONE);
                    vegeviewall.setVisibility(View.GONE);
                    vege.setVisibility(View.GONE);
                    vegtit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });
        dealquery = dealref.orderByChild("oos").startAt("instock").limitToFirst(5);
        dealquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //dismissing the progress dialog
                dealuploads.clear();
                progressDialog.dismiss();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadPojo dealupload = postSnapshot.getValue(UploadPojo.class);
                        dealuploads.add(dealupload);
                }
                deal_adaptor = new Deal_Adaptor(getActivity(), dealuploads);
                deal_adaptor.notifyDataSetChanged();
                deal_recyclerView.setAdapter(deal_adaptor);
                if (deal_adaptor.getItemCount() == 0){
                    deal_recyclerView.setVisibility(View.GONE);
                    dealcard.setVisibility(View.GONE);
                    dealtit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });

        fruitquery = ref.orderByChild("oos").startAt("instock").limitToFirst(5);
        fruitquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dismissing the progress dialog
                fruituploads.clear();
                progressDialog.dismiss();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadPojo upload = postSnapshot.getValue(UploadPojo.class);
                    if (upload.getCategory().toUpperCase().contains(c.toUpperCase()))
                    {
                        fruituploads.add(upload);
                    }
                }
                fruit_adaptor = new Fruit_Adaptor(getActivity(), fruituploads);
                fruit_adaptor.notifyDataSetChanged();
                fruit_recyclerView.setAdapter(fruit_adaptor);
                if (fruit_adaptor.getItemCount() == 0){
                    fruit_recyclerView.setVisibility(View.GONE);
                    fruitviewall.setVisibility(View.GONE);
                    fruitcard.setVisibility(View.GONE);
                    fruittit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });

        dryfruquery = ref.orderByChild("oos").startAt("instock");
        dryfruquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //dismissing the progress dialog
                dryfruituploads.clear();
                progressDialog.dismiss();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    UploadPojo dryupload = postSnapshot.getValue(UploadPojo.class);

                    if (dryupload.getCategory().toUpperCase().contains(d.toUpperCase()))
                    {
                        dryfruituploads.add(dryupload);
                    }

                }
                dryfruit_adaptor = new Dryfruit_Adaptor(getActivity(), dryfruituploads);
                dryfruit_adaptor.notifyDataSetChanged();
                dry_fruit_recyclerView.setAdapter(dryfruit_adaptor);
                if (dryfruit_adaptor.getItemCount() == 0){
                    dry_fruit_recyclerView.setVisibility(View.GONE);
                    dryfruitcard.setVisibility(View.GONE);
                    drytit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });
     preferences =  getActivity().getSharedPreferences("Main",MODE_PRIVATE);

        vegeviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewvege();
            }
        });
      vege.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

               viewvege();

          }
      });


        dealcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("data",b);
                editor.apply();
                Fragment fragment = new Product_recycleview();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();

            }
        });
        fruitviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruitview();
            }
        });
        fruitcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fruitview();

            }
        });
        dryfruitcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("data",d);
                editor.apply();
                Fragment fragment = new Product_recycleview();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();


            }
        });
        return view;

    }

    private void fruitview() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data",c);
        editor.apply();
        Fragment fragment = new Product_recycleview();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
    }

    private void viewvege() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data",a);
        editor.apply();
        Fragment fragment = new Product_recycleview();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.f_container,fragment,fragment.getTag()).commit();
    }

}