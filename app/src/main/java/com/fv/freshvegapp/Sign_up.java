package com.fv.freshvegapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;


public class Sign_up extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private long backPressedTime;
    private Toast backToast;
    private DatabaseReference reff;
    private Button btn,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        btn = findViewById(R.id.buttnContinue);
        btn2 = findViewById(R.id.buttonContinue);
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
            }
        });

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }
                String phonenumber = "+" + code + number;
                Intent intent = new Intent(Sign_up.this, Otp.class);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Spannable spannable = editText.getText();
                if (!TextUtils.isEmpty(s) && s.length() > 9){

//                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(Sign_up.this, R.color.red)),
//                            0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    btn.setVisibility(View.GONE);
                    btn2.setVisibility(View.VISIBLE);
                }
                else {
                    btn.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

        @Override
        public void onBackPressed() {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                super.onBackPressed();
                return;
            } else {
                backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }

            backPressedTime = System.currentTimeMillis();
        }
    }