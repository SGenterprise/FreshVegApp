package com.fv.freshvegapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fv.freshvegapp.Profile_fragment.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Screen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash__screen);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            new Handler().postDelayed(new Runnable() {
                                          @Override
                                          public void run() {

                                              reff = FirebaseDatabase.getInstance().getReference().child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");
                                              reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                      if (dataSnapshot.exists()) {
                                                          Intent intent = new Intent(Splash_Screen.this, Firstactivity.class);
                                                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                          startActivity(intent);
                                                          finish();

                                                      } else {

                                                          Intent intent = new Intent(Splash_Screen.this, Profile.class);
                                                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                          startActivity(intent);
                                                          finish();
//                                                          progressBar.setVisibility(View.INVISIBLE);

                                                      }
                                                  }
                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                  }
                                              });

//                                              Intent intent = new Intent(Splash_Screen.this, Sign_up.class);
//                                              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                              startActivity(intent);
//                                              finish();

                                          }
                                      }
                    , SPLASH_SCREEN_TIME_OUT);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(Splash_Screen.this,
                            CheckServieAvailable.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                    //the current activity will get finished.
                }
            }
            , SPLASH_SCREEN_TIME_OUT);

        }

    }
}