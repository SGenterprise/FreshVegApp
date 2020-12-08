package com.fv.freshvegapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Upload_Pincodes extends AppCompatActivity {
    EditText edpin;
    Button contin;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__pincodes);

        reference = FirebaseDatabase.getInstance().getReference("pincodes");
        edpin = findViewById(R.id.edpin);
        contin = findViewById(R.id.btn_con);

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPojo upload = new UploadPojo();
                upload.setPincode(edpin.getText().toString());
                String uploadId = reference.push().getKey();
                reference.child(uploadId).setValue(upload);
            }
        });

    }
}