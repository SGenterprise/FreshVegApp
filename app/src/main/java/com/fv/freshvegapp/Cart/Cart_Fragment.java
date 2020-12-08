package com.fv.freshvegapp.Cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.Address.Address_Select_Activity;
import com.fv.freshvegapp.Coupons.Coupon_Select_Activity;
import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Cart_Fragment extends Fragment
//        implements PaymentResultListener
{

    private RecyclerView recyclerView;
    private Cart_Adaptor adapter;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private List<CartPojo> uploads;
    private RelativeLayout lo,address2;
    DatabaseReference reff,refff;
    String amPm,txt_email,txt_num;
    CardView lay1,lay2,lay3,addprofreedel;
    private TextView houseno,buildname,address1,delivery_time,Select_address_text,deliverytill,delivery_charge,t42,chosedelivery,coupondiscount;
    String TAG = "Payment Error";
    private TextView total,btn_order,tmrp,btn_change,dateTextView, timeTextView,textshu,tax,add_id,coupanname,amtforfreedeli;
    CardView btn;
    RelativeLayout orderlay;
    int totaltext = 0, percent = 0,uptodis = 0,mini = 0;
    double taxestext ;
    String email;
    int finaltotal = 0;
    String ad1="",h1="",b1="",dnumber="",name="",landmark="";
    String lati ,longi,coupondis;
    String per="", upto="",min="";
    String userid = FirebaseAuth.getInstance().getUid();
    RecyclerView CalenderRecycleView;
    CalenderApater calenderApater;
    List<CalenderModel> CalModel = new ArrayList<>();
    RadioGroup Radiogroup;
    RadioButton radioButtonOne ,radioButtonThree;
    String SelectTime = "";
    String ap_selected_date, HeaderPincode,couponnamee;
    String dated="";
    String orderid="";
    SharedPreferences preferences;
    long maxid= 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        Checkout.preload(getActivity());

        addprofreedel = view.findViewById(R.id.addprofreedel);
        lay1 = view.findViewById(R.id.lay1);
        lay2 = view.findViewById(R.id.lay2);
        lay3 = view.findViewById(R.id.lay3);
        tax = view.findViewById(R.id.taxes_id);
        add_id = view.findViewById(R.id.add_id);
        coupondiscount = view.findViewById(R.id.coudis);
        coupanname = view.findViewById(R.id.coupanname);
        btn = view.findViewById(R.id.id_startshoping);
        btn_order = view.findViewById(R.id.orderplace);
        delivery_charge = view.findViewById(R.id.deliverycharge);
        amtforfreedeli = view.findViewById(R.id.amtforfreedeli);
        textshu = view.findViewById(R.id.Schedule);
        deliverytill = view.findViewById(R.id.textdelivertill);
         total= view.findViewById(R.id.amounttotal);
         orderlay = view.findViewById(R.id.orderlay);
         dateTextView = view.findViewById(R.id.date_text);
         timeTextView = view.findViewById(R.id.time_text);
         btn_change = view.findViewById(R.id.changeaddress);
         tmrp = view.findViewById(R.id.tmrp);
        address2 = view.findViewById(R.id.address2);
        delivery_time = view.findViewById(R.id.delivery_time);
        Select_address_text = view.findViewById(R.id.Select_address_text);
        houseno = view.findViewById(R.id.houseno);
        address1 = view.findViewById(R.id.addline1);
        buildname = view.findViewById(R.id.buldg_id);
        t42 = view.findViewById(R.id.t42);
        chosedelivery = view.findViewById(R.id.chooseDelivery);

        Radiogroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioButtonOne = (RadioButton) view.findViewById(R.id.RadioTimeOne);
        radioButtonThree = (RadioButton) view.findViewById(R.id.RadioTimeThree);
        CalenderRecycleView = (RecyclerView) view.findViewById(R.id.dateRecycleView);

        delivery_time.setVisibility(View.GONE);
        textshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Radiogroup.clearCheck();
                Radiogroup.setVisibility(View.VISIBLE);
                CalenderRecycleView.setVisibility(View.VISIBLE);
                textshu.setVisibility(View.GONE);
                chosedelivery.setVisibility(View.GONE);
                delivery_time.setVisibility(View.GONE);
                dateTextView.setVisibility(View.GONE);
                deliverytill.setVisibility(View.GONE);
                SelectTime = "";
                dated="";

            }
        });

                    btn_change.setVisibility(View.VISIBLE);
                    Select_address_text.setVisibility(View.VISIBLE);
                    preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MYPREFS",MODE_PRIVATE);
                    ad1 = preferences.getString( "add1","");
                    h1 = preferences.getString( "houseno","");
                    b1 = preferences.getString( "bu","");
                    dnumber = preferences.getString( "num","");
                    name = preferences.getString( "name","");
                    landmark = preferences.getString( "landmark","");
                    lati = preferences.getString( "lati","");
                    longi = preferences.getString( "longi","");
                    per = preferences.getString("per", "");
                    upto = preferences.getString("upto", "");
                    min = preferences.getString("min", "");
                    couponnamee = preferences.getString("couponname", "");

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.apply();

                    if (ad1.equals("")){
                        address2.setVisibility(View.GONE);
                        delivery_time.setVisibility(View.GONE);
                        address1.setVisibility(View.GONE);
                        Select_address_text.setVisibility(View.VISIBLE);
                    }else {
                        address2.setVisibility(View.VISIBLE);
                        delivery_time.setVisibility(View.VISIBLE);
                        address1.setVisibility(View.VISIBLE);
                        Select_address_text.setVisibility(View.GONE);
                    }
                    address1.setText(ad1);
                    houseno.setText(h1);
                    buildname.setText(b1);

      count();
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference("ADDRESS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    btn_change.setVisibility(View.VISIBLE);
                    Select_address_text.setVisibility(View.GONE);
                }
                else {
                    address1.setText("");
                    ad1 = "";
                    address2.setVisibility(View.GONE);
                    delivery_time.setVisibility(View.GONE);
                    address1.setVisibility(View.GONE);
                    btn_change.setVisibility(View.GONE);
                    Select_address_text.setVisibility(View.VISIBLE);
                }
            }
                @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RadioTimeOne:
                        SelectTime = "10 AM to 02 PM";
                        Gone();
                        break;

                    case R.id.RadioTimeThree:
                        SelectTime = "05 PM to 09 PM";
                        Gone();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        CalenderRecycleView.setLayoutManager(linearLayoutManager);
        //calenderApater = new CalenderApater(CalModel);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM d");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy"); //12-7-2020

        for (int i = 0; i < 7; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            String date = sdf1.format(calendar.getTime());
            String fullDate = sdf2.format(calendar.getTime());
            Log.i("Days", day);
            Log.i("Days", date);
            Log.i("Days", fullDate);
            CalModel.add(new CalenderModel(day, date, fullDate));

        }

        calenderApater = new CalenderApater(getContext(),CalModel);
        CalenderRecycleView.setAdapter(calenderApater);
        calenderApater.getFullDateListener(new CalenderApater.GetFullDateListener() {
            @Override
            public void getFullDateListener(String date) {
                ap_selected_date = calenderApater.fulldate;

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.product_recycleview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lo =  view.findViewById(R.id.emptycart_lay);
        progressDialog = new ProgressDialog(getContext());
        uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //adding an event listener to fetch values

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        progressDialog.dismiss();
                        lo.setVisibility(View.GONE);
                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.VISIBLE);
                        lay3.setVisibility(View.VISIBLE);
                        orderlay.setVisibility(View.VISIBLE);
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //dismissing the progress dialog
                                progressDialog.dismiss();
                                //iterating through all the values in database
                                uploads.clear();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    CartPojo cartPojo = postSnapshot.getValue(CartPojo.class);
                                    uploads.add(cartPojo);
                                }
                                //creating adapter
                                adapter = new Cart_Adaptor(getActivity(), uploads);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                progressDialog.dismiss();
                            }

                        });

                    }else {
                    progressDialog.dismiss();
                    lo.setVisibility(View.VISIBLE);
                    orderlay.setVisibility(View.GONE);
                        lay1.setVisibility(View.GONE);
                        lay2.setVisibility(View.GONE);
                        lay3.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    int sum = 0;
                    int  a = 0;
                    int k = 0;

                    for (DataSnapshot ds : snapshot.getChildren()){
                        Map<String,Object> map = (Map<String, Object>)ds.getValue();
                        Object price = map.get("subprice");
                        int pValue = Integer.parseInt(String.valueOf(price));
                        if (per.equals("")){

                        }else {
                             percent = Integer.parseInt(per);
                             uptodis = Integer.parseInt(upto);
                             mini = Integer.parseInt(min);
                        }

                        sum += pValue;

                        Log.d("Sum",String.valueOf(sum));

                        tmrp.setText(String.valueOf(sum));

                         if(sum >= 100 ){
                             addprofreedel.setVisibility(View.GONE);
                          a = 0;
                            if (sum >= mini && mini != 0){
                                if (!per.equals("")) {
                                    k = sum * percent / 100;
                                    if (k >= uptodis) {
                                        coupondiscount.setText(upto);
                                        coupanname.setText(couponnamee);
                                        totaltext = sum+a-uptodis;
                                        totalvalue();

                                    } else {
                                        coupondiscount.setText(String.valueOf(k));
                                        coupanname.setText(couponnamee);
                                        totaltext = sum+a-k;
                                        totalvalue();
                                    }
                                }
                                else {
                                    coupondiscount.setText("0");
                                    couponnamee = "";
                                    coupanname.setText(couponnamee);
                                    totaltext =sum+a;
                                    totalvalue();
                                }
                            }
                            else {
                                coupondiscount.setText("0");
                                couponnamee = "";
                                coupanname.setText(couponnamee);
                                totaltext =sum+a;
                            }
                            totalvalue();
                            delivery_charge.setText(String.valueOf(a));
                        }  // 30 delivery
                        else if (sum >= 0){
                             addprofreedel.setVisibility(View.VISIBLE);
                            //  add ** amount to get free delivery
                             int b = 100 - sum;
                             amtforfreedeli.setText(String.valueOf(b));
                        a = 30;
                            if (sum >= mini && mini != 0){
                                if (!per.equals("")) {
                                    k = sum * percent / 100;
                                    if (k >= uptodis) {
                                        coupondiscount.setText(upto);
                                        coupanname.setText(couponnamee);
                                        totaltext = sum+a-uptodis;
                                        totalvalue();

                                    } else {
                                        coupondiscount.setText(String.valueOf(k));
                                        coupanname.setText(couponnamee);
                                        totaltext = sum+a-k;
                                        totalvalue();
                                    }
                                }
                                else {
                                    coupondiscount.setText("0");
                                    couponnamee = "";
                                    coupanname.setText(couponnamee);
                                    totaltext =sum+a;
                                    totalvalue();
                                }
                            }
                            else {
                                coupondiscount.setText("0");
                                couponnamee = "";
                                coupanname.setText(couponnamee);
                                totaltext =sum+a;
                            }
                            totalvalue();
                            delivery_charge.setText(String.valueOf(a));
                    }   // 50 delivery

                        totalvalue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        add_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MYPREFS",MODE_PRIVATE);
                String mrp = tmrp.getText().toString().trim();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mrp",mrp);
                editor.apply();

                Intent i = new Intent(getContext(), Coupon_Select_Activity.class);
                startActivity(i);
                getActivity();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Firstactivity.class);
                startActivity(i);
            }
        });

        Select_address_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Address_Select_Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Address_Select_Activity.class);
                startActivity(i);
                getActivity().finish();

            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((ad1 != null) && !(ad1.equals(""))){

                    if ((SelectTime != null) && !(SelectTime.equals(""))){
                        order();
                    }
                    else {
                        Toast.makeText(getContext(),"Schedule delivery" , Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(getContext(), "Add Address", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    private void totalvalue() {
          total.setText(String.valueOf(totaltext));
          t42.setText(String.valueOf(totaltext));
    }

    private void count() {
            DatabaseReference r555 = FirebaseDatabase.getInstance().getReference("All_Orders");

            r555.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){
                        maxid = snapshot.getChildrenCount();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    private void Gone() {

        Radiogroup.setVisibility(View.GONE);
        CalenderRecycleView.setVisibility(View.GONE);
        chosedelivery.setVisibility(View.GONE);
        textshu.setVisibility(View.VISIBLE);
        deliverytill.setVisibility(View.VISIBLE);

        SharedPreferences preferences = getContext().getSharedPreferences("date",MODE_PRIVATE);
        dated = preferences.getString( "fulldate","");
        SharedPreferences.Editor editor = preferences.edit();
        editor.apply();

        dateTextView.setText(dated+" "+SelectTime);
        dateTextView.setVisibility(View.VISIBLE);

    }

    private void order() {

        payoption();

    }

    private void payoption() {

        Date currentTime = Calendar.getInstance().getTime();
        SharedPreferences preferences = getContext().getSharedPreferences("MYPREFS",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("TotalAmount",String.valueOf(totaltext));
        editor.putString("Ordertime",String.valueOf(currentTime));
        editor.putString("Orderstatus","Order Placed");
        editor.putString("Producttotal",tmrp.getText().toString().trim());
        editor.putString("DChagre",delivery_charge.getText().toString().trim());
        editor.putString("discount",coupondiscount.getText().toString().trim());
        editor.putString("Couponname",coupanname.getText().toString().trim());
        editor.putString("Taxes",tax.getText().toString().trim());
        editor.putString("Daddress",ad1);
        editor.putString("DhousenoandBuld",h1+","+b1);
        editor.putString("Dnumber",dnumber);
        editor.putString("Dcxname",name);
        editor.putString("Dlandmark",landmark);
        editor.putString("Latitude",lati);
        editor.putString("Longitude",longi);
        editor.putString("Dtime",dated+" "+SelectTime);
        editor.putString("Userid",userid);
        editor.putString("maxid", String.valueOf(maxid));

        editor.apply();

        Intent i = new Intent(getContext(), PaytmentOptions.class);
        startActivity(i);

    }


}