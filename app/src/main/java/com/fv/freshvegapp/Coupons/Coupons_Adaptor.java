package com.fv.freshvegapp.Coupons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.Firstactivity;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Coupons_Adaptor extends RecyclerView.Adapter<Coupons_Adaptor.ViewHolder> {

private Context context;
private List<coupanpojo> uploads;
    String coupon = "";

public Coupons_Adaptor(Context context, List<coupanpojo> uploads) {
        this.uploads = uploads;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.coupon_recycleview_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override


public void onBindViewHolder(final ViewHolder holder, final int position) {

    final String couponid,valid,min,per,upto;

    final coupanpojo upload = uploads.get(position);
    couponid = upload.getCouponid();
    valid = upload.getValid();
    min = upload.getMin();
    per = upload.getPer();
    upto = upload.getUpto();

    holder.coupan.setText(couponid);
    holder.per.setText(per);
    holder.upto.setText(upto);
    holder.min.setText(min);
    holder.valid.setText(valid);

    if (min.equals("")){
        holder.minlay.setVisibility(View.GONE);
        holder.nomin.setVisibility(View.VISIBLE);
    }
    else {
        holder.minlay.setVisibility(View.VISIBLE);
        holder.nomin.setVisibility(View.GONE);
    }

    holder.Apply.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            Query query = reff.orderByChild("coupan").equalTo(couponid);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (!snapshot.exists()) {
                                                    appycoupon();
                                                }
                        if (snapshot.exists()){
                            Toast.makeText(context, "Sorry Coupon Already Used", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        private void appycoupon() {
            SharedPreferences preferences = context.getSharedPreferences("MYPREFS", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String mrp = preferences.getString("mrp", "");
            editor.apply();

            if (Integer.parseInt(mrp) >= Integer.parseInt(min)) {

                editor.putString("per", per);
                editor.putString("upto", upto);
                editor.putString("min", min);
                editor.putString("couponname", couponid);
                editor.apply();

                preferences = context.getSharedPreferences("coupon", MODE_PRIVATE);
                SharedPreferences.Editor edito = preferences.edit();
                String add = "1";
                edito.putString("couponcode", add);
                edito.apply();

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                Intent i = new Intent(context, Firstactivity.class);
                context.startActivity(i);

                Toast.makeText(activity, "Coupon Applied Sucessfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Total Product Mrp should be greater or equal to " + min, Toast.LENGTH_SHORT).show();
            }
        }


    });


}

@Override
public int getItemCount() {
        return uploads.size();
        }


class ViewHolder extends RecyclerView.ViewHolder {

    public TextView coupan,min,valid,nomin,per,upto;
    public CardView Apply;
    public LinearLayout minlay;

    public ViewHolder(View itemView) {
        super(itemView);

        coupan = (TextView) itemView.findViewById(R.id.text1);
        per = (TextView) itemView.findViewById(R.id.percent);
        upto = (TextView) itemView.findViewById(R.id.txtupto);
        min = (TextView) itemView.findViewById(R.id.minprice);
        nomin = (TextView) itemView.findViewById(R.id.nomin);
        minlay = itemView.findViewById(R.id.minlay);
        valid = (TextView) itemView.findViewById(R.id.expdate);
        Apply = (CardView) itemView.findViewById(R.id.cardapply);
    }
}
}