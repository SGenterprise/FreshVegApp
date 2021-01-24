package com.fv.freshvegapp.Products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fv.freshvegapp.R;

public class Product_detail_activity extends AppCompatActivity {

    TextView CategoryName,ProductName,Mrp,Price;
    ImageView ProImg,ProImg1,ProImg2,ProImg3;
    String cn,pn,m,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);

        CategoryName = findViewById(R.id.category_name);
        ProductName = findViewById(R.id.Productname);
        Mrp = findViewById(R.id.mrp);
        Price = findViewById(R.id.price);
        ProImg = findViewById(R.id.ProductImg);
        ProImg1 = findViewById(R.id.ProductImg1);
        ProImg2 = findViewById(R.id.ProductImg2);
        ProImg3 = findViewById(R.id.ProductImg3);

        SharedPreferences prefc = getSharedPreferences("Detail", MODE_PRIVATE);
        SharedPreferences.Editor editorr = prefc.edit();
        cn = prefc.getString("Categoryname", "");
        pn = prefc.getString("Productname", "");
        m = prefc.getString("Mrp", "");
        p = prefc.getString("ProductPrice", "");
        editorr.clear();
        editorr.apply();

        CategoryName.setText(cn);
        ProductName.setText(pn);
        Mrp.setText(m);
        Price.setText(p);


    }
}