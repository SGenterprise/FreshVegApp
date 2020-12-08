package com.fv.freshvegapp.Orders;

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

import com.fv.freshvegapp.Cart.CartPojo;
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

public class Order_Products_Recycleview extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Order_products_Adaptor adapter;
    private DatabaseReference mDatabase,reff;
    private ProgressDialog progressDialog;
    private List<CartPojo> uploads;
    TextView orderdate,orderid,totalamaount,orderstatus,paytype,deliverycharge,taxes,houseandbuld,fulladd,ddate,appBarTV,t42,tmrp,Dname_id,Dnum_id,Dlandmark_id;
    TextView coupan,dliveryon,asapdelivery,coupanname;
    String Totalprice,Coupan,couponnam,order_id,Ordertime,Orderstatus,paytypes,DChagre,Taxes,Daddress,Dhandb,Dtime,Ddate,Tmrp,dname,dlandmark,dnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_desgin);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        LinearLayout back = findViewById(R.id.back_update);
        appBarTV = findViewById(R.id.appbar_text_view);
        appBarTV.setText("");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("Myorder", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String add = "1";
                editor.putString("order",add);
                editor.apply();

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                Intent i = new Intent(Order_Products_Recycleview.this, Firstactivity.class);
                startActivity(i);
                appBarTV.setText("My Orders");

            }
        });

        orderdate =  findViewById(R.id.orderdate_id);
        orderid =  findViewById(R.id.order_id);
        totalamaount =  findViewById(R.id.Total_id);
        orderstatus =  findViewById(R.id.OrderStatus_id);
        taxes  =  findViewById(R.id.taxes_id);
        deliverycharge  =  findViewById(R.id.deliverycharge);
        t42 = findViewById(R.id.t42);
        tmrp = findViewById(R.id.tmrp);
        coupanname  = findViewById(R.id.coupanname);
        paytype = findViewById(R.id.paytype_id);
        coupan = findViewById(R.id.coudis);
        fulladd  = findViewById(R.id.DeliveryHnandbul_id);
        ddate  = findViewById(R.id.ddate);
        Dname_id = findViewById(R.id.Dname_id);
        Dnum_id = findViewById(R.id.Dnum_id);
        Dlandmark_id  = findViewById(R.id.Dlandmark_id);
        dliveryon = findViewById(R.id.deliveryon);
        asapdelivery = findViewById(R.id.asap);
        recyclerView = (RecyclerView) findViewById(R.id.productsrecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        order_id = preferences.getString( "order_id","");
        Ordertime = preferences.getString( "orderdate","");
        Orderstatus = preferences.getString( "OrderStatus","");
        Totalprice = preferences.getString( "TotalAmount","");
        Tmrp = preferences.getString( "Tmrp","");
        DChagre = preferences.getString( "dcharge","");
        Taxes = preferences.getString( "taxes","");
        Coupan = preferences.getString( "discount","");
        Ddate = preferences.getString( "ddate","");
        Daddress = preferences.getString( "fulladdress","");
        Dhandb = preferences.getString( "handb","");
        dname = preferences.getString( "dname","");
        dnum = preferences.getString( "dnum","");
        dlandmark = preferences.getString( "dlandmark","");
        couponnam = preferences.getString( "coupname","");
        paytypes = preferences.getString( "paytype","");

        paytype.setText(paytypes);
        t42.setText(Totalprice);
        orderid.setText(order_id);
        orderdate.setText(Ordertime);
        orderstatus.setText(Orderstatus);
        totalamaount.setText(Totalprice);
        deliverycharge.setText(DChagre);
        tmrp.setText(Tmrp);
        taxes.setText(Taxes);
        coupan.setText(Coupan);
        coupanname.setText(couponnam);
        fulladd.setText(Dhandb+" "+Daddress);

      if (Ddate.equals(" ")){
          dliveryon.setVisibility(View.GONE);
          ddate.setVisibility(View.GONE);
          asapdelivery.setVisibility(View.VISIBLE);
            }else {
          ddate.setText(Ddate);
          asapdelivery.setVisibility(View.GONE);
          dliveryon.setVisibility(View.VISIBLE);
          ddate.setVisibility(View.VISIBLE);
            }

        Dname_id.setText(dname);
        Dnum_id.setText(dnum);
        Dlandmark_id.setText(dlandmark);

        mDatabase = FirebaseDatabase.getInstance().getReference("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(order_id).child("Order_list");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                uploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    CartPojo upload = postSnapshot.getValue(CartPojo.class);
                    uploads.add(upload);
                }
                adapter = new Order_products_Adaptor(Order_Products_Recycleview.this, uploads);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }

        });

    }
}