package com.fv.freshvegapp.Products;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fv.freshvegapp.Cart.CartPojo;
import com.fv.freshvegapp.Cart.Thankyou;
import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Product_detail_activity extends AppCompatActivity {

    TextView CategoryName,ProductName,Mrp,Price,quantity;
    ImageView ProImg,ProImg1,ProImg2,ProImg3,ProImg4;
    String cn,pn,m,p,pi,pi1,pi2,pi3,count,q;
    LinearLayout glay1,glay2,glay3,glay4,pmlay;
    TextView add,counttext,minus,plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        LinearLayout back = findViewById(R.id.back_update);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                Onbackpress();

            }
        });

        CategoryName = findViewById(R.id.category_name);
        ProductName = findViewById(R.id.Productname);
        Mrp = findViewById(R.id.mrp);
        Price = findViewById(R.id.price);
        quantity  = findViewById(R.id.quantity);
        ProImg = findViewById(R.id.ProductImg);
        ProImg1 = findViewById(R.id.ProductImg1);
        ProImg2 = findViewById(R.id.ProductImg2);
        ProImg3 = findViewById(R.id.ProductImg3);
        ProImg4  = findViewById(R.id.ProductImg4);

        glay1  = findViewById(R.id.greenlay1);
        glay2  = findViewById(R.id.greenlay2);
        glay3  = findViewById(R.id.greenlay3);
        glay4   = findViewById(R.id.greenlay4);

        pmlay = findViewById(R.id.laypm);
        add   = findViewById(R.id.Add);
        counttext = findViewById(R.id.count_text);
        minus = findViewById(R.id.minus);
        plus  =findViewById(R.id.plus);

        SharedPreferences prefc = getSharedPreferences("Detail", MODE_PRIVATE);
        SharedPreferences.Editor editorr = prefc.edit();
        cn = prefc.getString("Categoryname", "");
        pn = prefc.getString("Productname", "");
        m = prefc.getString("Mrp", "");
        p = prefc.getString("ProductPrice", "");
        q = prefc.getString("quantity", "");
        pi = prefc.getString("ProductImage", "");
        pi1 = prefc.getString("ProductImage1", "");
        pi2 = prefc.getString("ProductImage2", "");
        pi3 = prefc.getString("ProductImage3", "");
        count = prefc.getString("Count", "");

        editorr.clear();
        editorr.apply();

        CategoryName.setText(cn);
        ProductName.setText(pn);
        Mrp.setText(m);
        Price.setText(p);
        quantity.setText(q);

        Glide.with(this)
                .load(pi)
                .centerCrop()
                .into(ProImg);
        Glide.with(this)
                .load(pi)
                .centerCrop()
                .into(ProImg1);

        if(pi1 == null || pi1.equals("")) {
            glay2.setVisibility(View.GONE);
        }
        else {
            glay2.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(pi1)
                    .centerCrop()
                    .into(ProImg2);
        }
        if (pi2 == null || pi2.equals("")) {
            glay3.setVisibility(View.GONE);
        }
        else{
            glay3.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(pi2)
                    .centerCrop()
                    .into(ProImg3);
        }
        if (pi3 == null || pi3.equals("")) {
            glay4.setVisibility(View.GONE);
        }
        else {
            glay4.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(pi3)
                    .centerCrop()
                    .into(ProImg4);
        }

        glay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Glide.with(getApplicationContext())
                        .load(pi)
                        .centerCrop()
                        .into(ProImg);
            }
        });
        glay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Glide.with(getApplicationContext())
                        .load(pi1)
                        .centerCrop()
                        .into(ProImg);
            }
        });
        glay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Glide.with(getApplicationContext())
                        .load(pi2)
                        .centerCrop()
                        .into(ProImg);
            }
        });
        glay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Glide.with(getApplicationContext())
                        .load(pi3)
                        .centerCrop()
                        .into(ProImg);
            }
        });

        if (count == null || count.equals("")) {
            add.setVisibility(View.VISIBLE);
            pmlay.setVisibility(View.GONE);
        }
        else {
            add.setVisibility(View.GONE);
            pmlay.setVisibility(View.VISIBLE);
            counttext.setText(count);
        }

        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        final CartPojo cartPojo = new CartPojo();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 1;
                counttext.setText(String.valueOf(a));
                add.setVisibility(View.GONE);
                pmlay.setVisibility(View.VISIBLE);
                // add to user cart Items
                cartPojo.setCategoryName(cn);
                cartPojo.setProduct_img(pi);
                cartPojo.setProductName(pn);
                cartPojo.setQuantity(q);
                cartPojo.setMrp(m);
                int result = (Integer.valueOf(p))*(Integer.valueOf(a));
                String subprice = (String.valueOf(result));
                cartPojo.setSubprice(subprice);
                cartPojo.setPrice(p);
                cartPojo.setCount(String.valueOf(a));
                reff.child(pn).setValue(cartPojo);

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(counttext.getText().toString().trim());

                quantity--;
                if (quantity == 0) {
                    final int finalQuantity = quantity;
                    new AlertDialog.Builder(Product_detail_activity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Do you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    counttext.setText(finalQuantity + "");
                                    pmlay.setVisibility(View.INVISIBLE);
                                    add.setVisibility(View.VISIBLE);
                                    reff.child(pn).removeValue();

                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
                else {
                    counttext.setText(String.valueOf(quantity));
                    counttext.setVisibility(View.VISIBLE);
                    cartPojo.setCategoryName(cn);
                    cartPojo.setProductName(pn);
                    cartPojo.setProduct_img(pi);
                    cartPojo.setQuantity(q);
                    int result = (Integer.valueOf(p))*(Integer.valueOf(counttext.getText().toString()));
                    String subprice = (String.valueOf(result));
                    cartPojo.setSubprice(subprice);
                    cartPojo.setPrice(p);
                    cartPojo.setMrp(m);
                    cartPojo.setCount(counttext.getText().toString());
                    reff.child(pn).setValue(cartPojo);
                    //   notifyDataSetChanged();
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = Integer.parseInt(counttext.getText().toString());

                if(!(quantity == 10)){
                    quantity++;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Max Quantity is 10", Toast.LENGTH_SHORT).show();
                }

                counttext.setText(String.valueOf(quantity));
                counttext.setVisibility(View.VISIBLE);

                cartPojo.setCategoryName(cn);
                cartPojo.setProductName(pn);
                cartPojo.setProduct_img(pi);
                cartPojo.setQuantity(q);
                int result = (Integer.valueOf(p))*(Integer.valueOf(counttext.getText().toString()));
                String subprice = (String.valueOf(result));
                cartPojo.setSubprice(subprice);
                cartPojo.setPrice(p);
                cartPojo.setMrp(m);
                cartPojo.setCount(counttext.getText().toString());
                reff.child(pn).setValue(cartPojo);

            }
        });

    }

    private void Onbackpress() {
        super.onBackPressed();

        SharedPreferences prefffj = getSharedPreferences("Detail", MODE_PRIVATE);
        SharedPreferences.Editor editorr = prefffj.edit();
        editorr.clear();
        editorr.apply();

    }
}