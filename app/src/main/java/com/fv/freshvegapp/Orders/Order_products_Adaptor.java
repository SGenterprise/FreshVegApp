package com.fv.freshvegapp.Orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fv.freshvegapp.Cart.CartPojo;
import com.fv.freshvegapp.R;

import java.util.List;

public class Order_products_Adaptor extends RecyclerView.Adapter<Order_products_Adaptor.ViewHolder> {

private Context context;
private List<CartPojo> uploads;

public Order_products_Adaptor(Context context, List<CartPojo> uploads) {
        this.uploads = uploads;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.order_pro_recy_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(final ViewHolder holder, final int position) {

    final CartPojo upload = uploads.get(position);

    holder.procat.setText(upload.getCategoryName());
    holder.pro_name.setText(upload.getProductName());
    holder.price.setText(upload.getPrice());
    holder.count.setText(upload.getCount());
    holder.subprice.setText(upload.getSubprice());
    holder.weight.setText(upload.getQuantity());

    Glide.with(context)
            .load(upload.getProduct_img())
            .centerCrop()
            .into(holder.proimage);
        }

@Override
public int getItemCount() {
        return uploads.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView procat,pro_name,price,count,weight,subprice;
    public ImageView proimage;
    public ViewHolder(View itemView) {
        super(itemView);

        procat = (TextView) itemView.findViewById(R.id.txt_cat);
        pro_name = (TextView) itemView.findViewById(R.id.pro_name);
        price = (TextView) itemView.findViewById(R.id.price);
        count = (TextView) itemView.findViewById(R.id.count_id);
        subprice= (TextView) itemView.findViewById(R.id.subtotal);
        weight = (TextView) itemView.findViewById(R.id.quantity);
        proimage = (ImageView) itemView.findViewById(R.id.proimage);

    }
}
}