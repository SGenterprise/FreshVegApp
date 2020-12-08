package com.fv.freshvegapp.Profile_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

EditText name,email,age;
TextView number;
RelativeLayout submit;
RadioButton male,female;
DatabaseReference reff;
Profile_Pojo profile_pojo;
private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        number = findViewById(R.id.num_id);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        String value = sharedPreferences.getString("Value","Data not found");
        number.setText(value);

        submit = findViewById(R.id.submit_id);
//        male=findViewById(R.id.male_id);
//        female=findViewById(R.id.female_id);
        name = findViewById(R.id.name_id);
        email = findViewById(R.id.email_id);
//        age = findViewById(R.id.age_id);
        progressBar = findViewById(R.id.progress_circle);

        profile_pojo = new Profile_Pojo();

        reff = FirebaseDatabase.getInstance().getReference().child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                profile_pojo.setName(name.getText().toString().trim());
                profile_pojo.setEmail(email.getText().toString().trim());
//                profile_pojo.setAge(age.getText().toString().trim());

                if (TextUtils.isEmpty(name.getText().toString().trim()) || TextUtils.isEmpty(email.getText().toString().trim())
                       )

                {
                    Toast.makeText(Profile.this, "enter all details", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    reff.child("Profile").setValue(profile_pojo);

                    Intent intent = new Intent(Profile.this, Firstactivity.class);
                    startActivity(intent);
                    finish();
                }

                {
                    reff = FirebaseDatabase.getInstance().getReference().child("Numbers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    profile_pojo.setNumber(number.getText().toString().trim());
                    reff.child("number").setValue(profile_pojo);
                }
            }
        });

    }

}
