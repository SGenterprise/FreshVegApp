package com.fv.freshvegapp.Slider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;
import com.zolad.zoominimageview.ZoomInImageView;

public class Slide_Detail extends AppCompatActivity {

    ZoomInImageView imageView;
    String url,des,title;
    TextView Tdes,Ttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide__detail);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);

        LinearLayout back = findViewById(R.id.back_update);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Slide_Detail.this, Firstactivity.class);
                startActivity(i);
                finish();
            }
        });
        imageView = findViewById(R.id.img123);
        Ttext = findViewById(R.id.stitle);
        Tdes = findViewById(R.id.sdes);

        Intent i = getIntent();
        url = i.getStringExtra("url");
        des = i.getStringExtra("des");
        title = i.getStringExtra("title");

        Ttext.setText(title);
        Tdes.setText(des);

        Glide.with(Slide_Detail.this)
                .load(url)
                .centerCrop()
                .into(imageView);
    }
}
