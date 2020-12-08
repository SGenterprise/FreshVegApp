package com.fv.freshvegapp.Orders;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Order_Adaptor extends RecyclerView.Adapter<Order_Adaptor.ViewHolder> {

private Context context;
private List<Order_pojo> uploads;

public Order_Adaptor(Context context, List<Order_pojo> uploads) {
        this.uploads = uploads;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.order_desgin, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(final ViewHolder holder, final int position) {

    final Order_pojo upload = uploads.get(position);

    holder.orderdate.setText(upload.getOrdertime());
    holder.OrderID.setText(upload.getOrderid());
    holder.OrderStatus.setText(upload.getOrderstatus());
    holder.TotalAmount.setText(upload.getTotalprice());

    if (upload.getOrderstatus().equals("Order Placed")){
        holder.placed.setVisibility(View.VISIBLE);
        holder.no_inprocess.setVisibility(View.VISIBLE);
        holder.no_packed.setVisibility(View.VISIBLE);
        holder.no_ontheway.setVisibility(View.VISIBLE);
        holder.no_delivered.setVisibility(View.VISIBLE);
    }
    else if (upload.getOrderstatus().equals("In Process")){
        holder.placed.setVisibility(View.VISIBLE);
        holder.inprocess.setVisibility(View.VISIBLE);
        holder.no_packed.setVisibility(View.VISIBLE);
        holder.no_ontheway.setVisibility(View.VISIBLE);
        holder.no_delivered.setVisibility(View.VISIBLE);
    }
    else if (upload.getOrderstatus().equals("Packed")){
        holder.placed.setVisibility(View.VISIBLE);
        holder.inprocess.setVisibility(View.VISIBLE);
        holder.packed.setVisibility(View.VISIBLE);
        holder.no_ontheway.setVisibility(View.VISIBLE);
        holder.no_delivered.setVisibility(View.VISIBLE);
    }
    else if (upload.getOrderstatus().equals("On The Way")){
        holder.placed.setVisibility(View.VISIBLE);
        holder.inprocess.setVisibility(View.VISIBLE);
        holder.packed.setVisibility(View.VISIBLE);
        holder.ontheway.setVisibility(View.VISIBLE);
        holder.no_delivered.setVisibility(View.VISIBLE);
    }
    else if (upload.getOrderstatus().equals("Delivered")){
        holder.placed.setVisibility(View.VISIBLE);
        holder.inprocess.setVisibility(View.VISIBLE);
        holder.packed.setVisibility(View.VISIBLE);
        holder.ontheway.setVisibility(View.VISIBLE);
        holder.delivered.setVisibility(View.VISIBLE);
    }
    else if (upload.getOrderstatus().equals("Cancelled")){
        holder.placed.setVisibility(View.VISIBLE);
        holder.inprocess.setVisibility(View.VISIBLE);
        holder.packed.setVisibility(View.VISIBLE);
        holder.ontheway.setVisibility(View.VISIBLE);
        holder.delivered.setVisibility(View.GONE);
        holder.cancel.setVisibility(View.VISIBLE);
    }
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences preferences = context.getSharedPreferences("MYPREFS",MODE_PRIVATE);

            String orderdate = holder.orderdate.getText().toString().trim();
            String OrderID = holder.OrderID.getText().toString().trim();
            String OrderStatus = holder.OrderStatus.getText().toString().trim();
            String TotalAmount = holder.TotalAmount.getText().toString().trim();
            String Tmrp = upload.getProducttotal();
            String dcharge = upload.getDChagre();
            String taxes = upload.getTaxes();
            String coupan = upload.getDiscount();
            String handb = upload.getDhousenoandBuld();
            String fulladdress  = upload.getDaddress();
            String ddate = upload.getDtime();
            String dnum = upload.getDnumber();
            String dlandmark = upload.getDlandmark();
            String dname = upload.getDcxname();
            String dis = upload.getDiscount();
            String coupname = upload.getCoupan();
            String paytype = upload.getPaymentType();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("orderdate",orderdate);
            editor.putString("order_id",OrderID);
            editor.putString("OrderStatus",OrderStatus);
            editor.putString("TotalAmount",TotalAmount);
            editor.putString("dcharge",dcharge);
            editor.putString("taxes",taxes);
            editor.putString("coupan",coupan);
            editor.putString("Tmrp",Tmrp);
            editor.putString("fulladdress",fulladdress);
            editor.putString("handb",handb);
            editor.putString("ddate",ddate);
            editor.putString("dnum",dnum);
            editor.putString("dlandmark",dlandmark);
            editor.putString("dname",dname);
            editor.putString("discount",dis);
            editor.putString("coupname",coupname);
            editor.putString("paytype",paytype);
            editor.apply();

            Intent i = new Intent(context, Order_Products_Recycleview.class);
            context.startActivity(i);
        }
    });
        }

@Override
public int getItemCount() {
        return uploads.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView orderdate,OrderID,TotalAmount,OrderStatus;
    public LinearLayout placed,inprocess,packed,ontheway,delivered,cancel,no_inprocess,no_packed,no_ontheway,no_delivered;

    public ViewHolder(View itemView) {
        super(itemView);

        orderdate = (TextView) itemView.findViewById(R.id.orderdate_id);
        OrderID = (TextView) itemView.findViewById(R.id.order_id);
        TotalAmount = (TextView) itemView.findViewById(R.id.Total_id);
        OrderStatus = (TextView) itemView.findViewById(R.id.OrderStatus_id);
        placed = (LinearLayout) itemView.findViewById(R.id.placed);
        inprocess = (LinearLayout) itemView.findViewById(R.id.inprocress);
        packed = (LinearLayout) itemView.findViewById(R.id.packed);
        ontheway = (LinearLayout) itemView.findViewById(R.id.ontheway);
        delivered = (LinearLayout) itemView.findViewById(R.id.delivered);
        cancel = (LinearLayout) itemView.findViewById(R.id.cancel);
        no_inprocess = (LinearLayout) itemView.findViewById(R.id.no_inprocress);
        no_packed = (LinearLayout) itemView.findViewById(R.id.no_packed);
        no_ontheway = (LinearLayout) itemView.findViewById(R.id.no_ontheway);
        no_delivered = (LinearLayout) itemView.findViewById(R.id.no_delivered);

    }
}
}