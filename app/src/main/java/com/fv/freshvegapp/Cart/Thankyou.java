package com.fv.freshvegapp.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;

public class Thankyou extends AppCompatActivity {

    TextView order_id;
    CardView vieworder;
    String orderid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        order_id = findViewById(R.id.orderid);
        vieworder  = findViewById(R.id.vieworder);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        LinearLayout back = findViewById(R.id.back_update);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                Intent i = new Intent(Thankyou.this, Firstactivity.class);
                startActivity(i);
                finish();

            }
        });
        SharedPreferences preferences = getSharedPreferences("Thankyou",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        orderid = preferences.getString("OD_ID", "");
        editor.apply();

            order_id.setText(orderid);


        vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prr = getSharedPreferences("Thankyou", Context.MODE_PRIVATE);
                SharedPreferences.Editor editord = prr.edit();
                String key = "9";
                editord.putString("thankkey",key);
                editord.apply();

        Intent i = new Intent(Thankyou.this, Firstactivity.class);
        startActivity(i);
        finish();

            }
        });
    }
}