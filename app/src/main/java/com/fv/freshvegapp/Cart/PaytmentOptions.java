package com.fv.freshvegapp.Cart;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.Orders.Order_pojo;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class PaytmentOptions extends AppCompatActivity implements PaymentResultListener {

    RadioGroup Radiogroup;
    RadioButton Rgpay, Rphonepe ,Rpaytm,Rbhim,Rrozarpay,Rcod;
    LinearLayout Lgpay, Lphonepe ,Lpaytm,Lbhim,Lrozarpay,Lcod;
    String Package;
    String order_idd,txt_email,txt_num,finaltotal,discount,couponname,old,upiId = "8779100717-1@okbizaxis",name ="Freshveg",note="";
    RelativeLayout pay;
    DatabaseReference reff,refff;
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    String PAYTM_PACKAGE_NAME = "net.one97.paytm";
    String PHONEPE_PACKAGE_NAME = "com.phonepe.app";
    final int UPI_PAYMENT = 0;
    String ad1="",h1="",dnumber="",dname="",landmark="";
    String lati ,longi;
    String Ordertime;
    String Orderstatus;
    String Producttotal;
    String DCharge;
    String Taxes;
    String oldTaxes;
    String uid;
    String maxid;
    String dtime;
    String paytype;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytment_options);
        Checkout.preload(getApplicationContext());
        Package = getIntent().getStringExtra("Package");

        Rgpay = findViewById(R.id.radiogpay);
        Rphonepe = findViewById(R.id.radiophonepe);
        Rpaytm = findViewById(R.id.radiopaytmpe);
        Rbhim = findViewById(R.id.radiobhimupi);
        Rrozarpay  = findViewById(R.id.radiorozarpay);
        Rcod  = findViewById(R.id.radiocod);
        Radiogroup = findViewById(R.id.radioGroup);

        Lgpay = findViewById(R.id.gpaylay);
        Lphonepe = findViewById(R.id.phonepaylay);
        Lpaytm = findViewById(R.id.paytmlay);
        Lrozarpay  = findViewById(R.id.Rozarpaylay);
        Lcod  = findViewById(R.id.codlay);
        pay = findViewById(R.id.pay);

        SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        finaltotal = preferences.getString( "TotalAmount","");
        old = preferences.getString( "TotalAmount","");
        ad1 = preferences.getString( "Daddress","");
        h1 = preferences.getString( "DhousenoandBuld","");
        dnumber = preferences.getString( "Dnumber","");
        dname = preferences.getString( "Dcxname","");
        dtime  = preferences.getString( "Dtime","");
        landmark = preferences.getString( "Dlandmark","");
        lati = preferences.getString( "Latitude","");
        longi = preferences.getString( "Longitude","");
        Ordertime = preferences.getString( "Ordertime","");
        Orderstatus = preferences.getString( "Orderstatus","");
        Producttotal = preferences.getString( "Producttotal","");
        DCharge = preferences.getString( "DChagre","");
        Taxes = preferences.getString( "Taxes","");
        oldTaxes = preferences.getString( "Taxes","");
        uid = preferences.getString( "Userid","");
        maxid = preferences.getString( "maxid","");
        discount  = preferences.getString( "discount","");
        couponname = preferences.getString( "couponname","");

        Rgpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (Google Pay)";
                Package = "Gpay";
                newtotal();
                falsee();
                Rgpay.setChecked(true);

            }
        });
        Rphonepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (Phonepe)";
                Package = "Pp";
                newtotal();
                falsee();
                Rphonepe.setChecked(true);
            }
        });
        Rpaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (PaytmUPI)";
                Package = "Paytm";
                newtotal();
                falsee();
                Rpaytm.setChecked(true);
            }
        });
        Rrozarpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (Rzorpay)";
                Package = "Rzorpay";
                oldtotal();
                falsee();
                Rrozarpay.setChecked(true);
            }
        });
        Rcod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="CASH ON DELIVERY";
                Package = "COD";
                newtotal();
                falsee();
                Rcod.setChecked(true);
            }
        });

        Lgpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (Google Pay)";
                Package = "Gpay";
                newtotal();
                falsee();
                Rgpay.setChecked(true);

            }
        });
        Lphonepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (Phonepe)";
                Package = "Pp";
                newtotal();
                falsee();
                Rphonepe.setChecked(true);
            }
        });
        Lpaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (PaytmUPI)";
                Package = "Paytm";
                newtotal();
                falsee();
                Rpaytm.setChecked(true);
            }
        });
        Lrozarpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="PREPAID (Rzorpay)";
                Package = "Rzorpay";
                oldtotal();
                falsee();
                Rrozarpay.setChecked(true);
            }
        });
        Lcod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytype ="CASH ON DELIVERY";
                Package = "COD";
                newtotal();
                falsee();
                Rcod.setChecked(true);
            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Numbers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("number");
        reff.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                txt_email = dataSnapshot.child("email").getValue().toString();
                txt_num = dataSnapshot.child("number").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaytmentOptions.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Package.equals("COD")){
                    placeorder();
                    sendNotification();
                    ordernoti();
                }else {
                    payUsingUpi(finaltotal, upiId, name, note);
                }

            }
        });

    }

    private void falsee() {
        Rgpay.setChecked(false);
        Rphonepe.setChecked(false);
        Rpaytm.setChecked(false);
        Rbhim.setChecked(false);
        Rrozarpay.setChecked(false);
        Rcod.setChecked(false);
    }

    private void ordernoti(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("ordersucess","ordersucess",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, Firstactivity.class);
        intent.putExtra("phone", "2");
         pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"ordersucess")
                .setContentTitle("Order Sucessfully Placed")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText("this the description");

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());

    }

    private void newtotal() {
        finaltotal = old;
        Taxes = oldTaxes;
    }
    private void oldtotal() {

        Taxes = String.valueOf(Double.parseDouble(finaltotal)*2 /100);
        int aa = 0;
        aa = Integer.parseInt(finaltotal);
        finaltotal = String.valueOf((int) ((double) aa*2 /100+aa));
        finaltotal = String.valueOf(Integer.parseInt(finaltotal)*100);
    }
    private void payUsingUpi(String finaltotal, String upiId,String name,String note) {
//        Uri uri = Uri.parse("upi://pay").buildUpon()
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
//
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("mc","5411")
                .appendQueryParameter("tn",note)
                .appendQueryParameter("tr","78773873073")
                .appendQueryParameter("am",finaltotal)
                .appendQueryParameter("cu","INR")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        if(!Package.isEmpty())
            if(Package.equals("Paytm"))
                upiPayIntent.setPackage(PAYTM_PACKAGE_NAME);
            else if(Package.equals("Gpay"))
                upiPayIntent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            else if(Package.equals("Pp"))
                upiPayIntent.setPackage(PHONEPE_PACKAGE_NAME);
            else if(Package.equals("Rzorpay")){
                rozarpay();
            }

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        }
        else {
            Toast.makeText(PaytmentOptions.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String text = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + text);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(text);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PaytmentOptions.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";

                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                placeorder();
                sendNotification();
                ordernoti();

                Toast.makeText(PaytmentOptions.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaytmentOptions.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PaytmentOptions.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PaytmentOptions.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void rozarpay() {
                    Checkout checkout = new Checkout();
        final Activity activity = this;
                try {
                    JSONObject options = new JSONObject();
                    options.put("name", "VEG FRESH");
                    options.put("theme.color", "#F33B51");
                    options.put("currency", "INR");
                    options.put("amount",finaltotal);
                    options.put("prefill.contact",txt_num);
                    options.put("prefill.email", txt_email);
                    checkout.open(activity, options);

                } catch(Exception e) {
                    Log.e("TAG", "Error in starting Razorpay Checkout", e);

                }
    }

    @Override
    public void onPaymentSuccess(String s) {
        placeorder();
        ordernoti();
        sendNotification();
    }
    @Override
    public void onPaymentError(int i, String s) {

    }

    private void sendNotification() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email = "vf@g.com";

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic ZTQ4YWIxMzItNWUxNS00YTY4LTlmOGMtYjIzZDI4YTA0YjQ5");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"625f1a5f-2c38-4d90-937b-05f0c6c3045d\","
                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" +send_email+ "\"}],"
                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"New Order Received\"}"
                                + "}";
//                        English Message
                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }
    private void placeorder() {
        Order_pojo order_pojo = new Order_pojo();
        reff = FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        order_idd = reff.push().getKey();

        FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseDatabase.getInstance().getReference("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(order_idd).child("Order_list").setValue(dataSnapshot.getValue());
                        FirebaseDatabase.getInstance().getReference("All_Orders").child(order_idd).child("Order_list").setValue(dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        reff = FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refff = FirebaseDatabase.getInstance().getReference().child("All_Orders");
        order_pojo.setOrderid(order_idd);
        order_pojo.setOrdertime(currentDateTimeString);
        order_pojo.setOrderstatus("Order Placed");
        order_pojo.setTotalprice(finaltotal);
        if (!discount.equals("0")){
            order_pojo.setDiscount(discount);
            order_pojo.setCoupan(couponname);
        }
        else {
            order_pojo.setCoupan("");
            order_pojo.setDiscount("0");
        }

        order_pojo.setProducttotal(Producttotal);
        order_pojo.setDChagre(DCharge);
        order_pojo.setTaxes(Taxes);
        order_pojo.setDaddress(ad1);
        order_pojo.setDhousenoandBuld(h1);
        order_pojo.setDnumber(dnumber);
        order_pojo.setDcxname(dname);
        order_pojo.setPaymentType(paytype);
        order_pojo.setDlandmark(landmark);
        order_pojo.setLatitude(lati);
        order_pojo.setLongitude(longi);
        order_pojo.setUpcount(String.valueOf(maxid));
        order_pojo.setDtime(dtime);
        order_pojo.setUserid(uid);
        reff.child(order_idd).setValue(order_pojo);
        refff.child(order_idd).setValue(order_pojo);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String coupondata = "";
        editor.putString("per",coupondata);
        editor.putString("upto",coupondata);
        editor.putString("min",coupondata);
        editor.apply();

        DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refer.removeValue();

        SharedPreferences prr = getSharedPreferences("Thankyou", Context.MODE_PRIVATE);
        SharedPreferences.Editor editord = prr.edit();
        editord.putString("OD_ID",order_idd);
        editord.apply();

        Intent i = new Intent(PaytmentOptions.this, Thankyou.class);
        startActivity(i);
        finish();

    }
}