package com.fv.freshvegapp.ContactUs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fv.freshvegapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Contact_fragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fagment, container, false);

        initializeMap();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        LatLng maharashtra = new LatLng(20.9046268,73.0385759);
//        mMap.addMarker(new MarkerOptions().position(maharashtra).title("sendstone Head office"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(maharashtra));

        LatLng mumbai = new LatLng(19.1464186,72.9950583);
        mMap.addMarker(new MarkerOptions().position(mumbai).title("FreshVeg Airoli"));
     //   mMap.moveCamera(CameraUpdateFactory.newLatLng(mumbai));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mumbai,6));

    }

    private void initializeMap() {
        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }
    }

}
