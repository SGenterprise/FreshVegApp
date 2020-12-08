package com.fv.freshvegapp.Profile_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProFileFragment extends Fragment {
    private TextView name, email, age, gender, number, setpasscode,delpasscode,ed4;
    private DatabaseReference reff;
    private LinearLayout edit, update, cancel;
    private EditText editName, editEmail, editAge, edit_pass1, edit_pass2,ed1,ed2,ed3;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Profile_Pojo profile_pojo;
    private View view;
    private LottieAnimationView progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        edit = view.findViewById(R.id.Edit_profile);
        edit.setVisibility(View.VISIBLE);
        number = view.findViewById(R.id.txt5);
        name = view.findViewById(R.id.txt1);
        email = view.findViewById(R.id.txt2);
//        age = view.findViewById(R.id.txt3);
//        gender = view.findViewById(R.id.txt4);
        progressBar = view.findViewById(R.id.progress_circle);

        editName = view.findViewById(R.id.edit_name);
        editEmail = view.findViewById(R.id.edit_email);
//        editAge = view.findViewById(R.id.edit_Age);
//        radioGroup = view.findViewById(R.id.radioGroup);
        update = view.findViewById(R.id.Update_profile);
        cancel = view.findViewById(R.id.cancel_profile);

        profile_pojo = new Profile_Pojo();

        reff = FirebaseDatabase.getInstance().getReference().child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");
        reff.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String txt_name = dataSnapshot.child("name").getValue().toString();
                String txt_email = dataSnapshot.child("email").getValue().toString();
//                String txt_age = dataSnapshot.child("age").getValue().toString();
//                String txt_gender = dataSnapshot.child("gender").getValue().toString();

                name.setText(txt_name);
                email.setText(txt_email);
//                age.setText(txt_age);
//                gender.setText(txt_gender);

                editName.setText(txt_name);
                editEmail.setText(txt_email);
//                editAge.setText(txt_age);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        reff = FirebaseDatabase.getInstance().getReference().child("Numbers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("number");
        reff.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String txt_num=dataSnapshot.child("number").getValue().toString();
                number.setText(txt_num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
//                age.setVisibility(View.GONE);
//                gender.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);

                editName.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.VISIBLE);
//                editAge.setVisibility(View.VISIBLE);
//                radioGroup.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);


                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
//                age.setVisibility(View.VISIBLE);
//                gender.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);

                editName.setVisibility(View.GONE);
                editEmail.setVisibility(View.GONE);
//                editAge.setVisibility(View.GONE);
//                radioGroup.setVisibility(View.GONE);
                update.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
//                age.setVisibility(View.VISIBLE);
//                gender.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);

                reff = FirebaseDatabase.getInstance().getReference().child("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                profile_pojo.setName(editName.getText().toString().trim());
                profile_pojo.setEmail(editEmail.getText().toString().trim());
//                profile_pojo.setAge(editAge.getText().toString().trim());

//                int radioId = radioGroup.getCheckedRadioButtonId();
//                radioButton = view.findViewById(radioId);

 //               String m1 = radioButton.getText().toString();
//                profile_pojo.setGender(m1);

                reff.child("Profile").setValue(profile_pojo);

                editName.setVisibility(View.GONE);
                editEmail.setVisibility(View.GONE);
//                editAge.setVisibility(View.GONE);
//                radioGroup.setVisibility(View.GONE);
                update.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);

                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }

        });


        return view;
    }

    
}