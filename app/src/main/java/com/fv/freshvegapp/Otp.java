package com.fv.freshvegapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.view.PinView;
import com.fv.freshvegapp.Profile_fragment.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {

    private String verificationid;
    private FirebaseAuth mAuth;
    private LottieAnimationView progressBar;
    private EditText editText;
    private RelativeLayout relativeLayout,hide;
    private DatabaseReference reff;
    private PinView pinview;
    private long backPressedTime;
    private Toast backToast;
    CardView resendOtp;
    TextView otpCounter,textresend;
    CountDownTimer cTimer = null;
    PhoneAuthCredential credential;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        textresend = findViewById(R.id.textresend);
        otpCounter = findViewById(R.id.otpCounter);
        resendOtp = findViewById(R.id.resendOtp);
        startTimer();
        Intent intent = getIntent();
        String message = intent.getStringExtra("phonenumber");
        final TextView textView = findViewById(R.id.text4);
        TextView back = findViewById(R.id.back_id);
        pinview = findViewById(R.id.pinView);
        textView.setText(message);
        hide = findViewById(R.id.hide);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Value",textView.getText().toString());
        editor.apply();

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_circle);
        editText = findViewById(R.id.editTextCode);
        relativeLayout = findViewById(R.id.buttonSignIn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Otp.this, Sign_up.class);
                startActivity(intent);
                finish();
            }
        });

        pinview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 6) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    hide.setVisibility(View.GONE);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    hide.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);

                String code = pinview.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)) {

                    pinview.setError("Enter code...");
                    pinview.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    return;
                }
                verifyCode(code);

            }
        });
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTimer();
                startTimer();
                resendOTPMethod();
            }
        });
    }

    private void resendOTPMethod() {

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);

                String code = pinview.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)) {

                    pinview.setError("Enter code...");
                    pinview.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    return;
                }
                verifyCode(code);

            }
        });
    }


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//Daata exits or not check code..
                            reff = FirebaseDatabase.getInstance().getReference().child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");
                            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Intent intent = new Intent(Otp.this, Firstactivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Intent intent = new Intent(Otp.this, Profile.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                        progressBar.setVisibility(View.INVISIBLE);

                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                          //  Toast.makeText(Otp.this,"Incorrect OTP", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                });
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                //        progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Otp.this,"Incorrect OTP", Toast.LENGTH_SHORT).show();

        }
    };

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
    void startTimer() {
        //  otpCounter,resendOtp;
        otpCounter.setVisibility(View.VISIBLE);
        resendOtp.setVisibility(View.GONE);
        textresend.setVisibility(View.GONE);
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                otpCounter.setText("00 : " + millisUntilFinished / 1000 + "");
            }

            public void onFinish() {
                otpCounter.setVisibility(View.INVISIBLE);
                resendOtp.setVisibility(View.VISIBLE);
                textresend.setVisibility(View.VISIBLE);
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }
}