package com.fv.freshvegapp.Cart;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Cart_Adaptor extends RecyclerView.Adapter<Cart_Adaptor.ViewHolder> {

private Context context;
private List<CartPojo> uploads;

public Cart_Adaptor(Context context, List<CartPojo> uploads) {
        this.uploads = uploads;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.cart_recycleview_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(final ViewHolder holder, final int position) {

    final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    final CartPojo cartPojo = new CartPojo();
    final String CategoryName,ProductImage,Price,Quantity,ProductName,Count,Subtotal;
    final CartPojo upload = uploads.get(position);

    CategoryName = upload.getCategoryName();
    ProductImage = upload.getProduct_img();
    ProductName = upload.getProductName();
    Quantity = upload.getQuantity();
    Price = upload.getPrice();
    Count = upload.getCount();
    Subtotal = upload.getSubprice();

    holder.Cat_name.setText(CategoryName);
    holder.pro_name.setText(ProductName);
    holder.price.setText(Price);
    holder.quantity.setText(Quantity);
    holder.subprice.setText(Subtotal);
    holder.textcount.setText(Count);
    Glide.with(context)
            .load(ProductImage)
            .centerCrop()
            .into(holder.imageView);

    holder.add.setVisibility(View.GONE);
    holder.laypm.setVisibility(View.VISIBLE);

    if (uploads==null){
        Toast.makeText(context, "no items in cart", Toast.LENGTH_SHORT).show();
    }

    holder.minus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int quantity = Integer.parseInt(holder.textcount.getText().toString());

            quantity--;
            if (quantity == 0) {
                final int finalQuantity = quantity;
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reff.child(ProductName).removeValue();
//                                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.f_container, new Cart_Fragment())
//                                        .commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            } else {
                holder.textcount.setText(String.valueOf(quantity));
                holder.textcount.setVisibility(View.VISIBLE);
                cartPojo.setCategoryName(CategoryName);
                cartPojo.setProduct_img(ProductImage);
                cartPojo.setProductName(ProductName);
                cartPojo.setQuantity(Quantity);

                int result = (Integer.valueOf(Price))*(Integer.valueOf(holder.textcount.getText().toString()));
                String p = (String.valueOf(result));
                cartPojo.setSubprice(p);
                holder.subprice.setText(Subtotal);
                cartPojo.setPrice(Price);
                cartPojo.setCount(holder.textcount.getText().toString());
                reff.child(ProductName).setValue(cartPojo);
                notifyDataSetChanged();
                Cart_Adaptor adaptor = new Cart_Adaptor(context,uploads);

            }
        }
    });
    holder.plus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int quantity = Integer.parseInt(holder.textcount.getText().toString());
            if(!(quantity == 10)){
                quantity++;
            }
            else {
                Toast.makeText(context, "Max Quantity is 10", Toast.LENGTH_SHORT).show();
            }
            holder.textcount.setText(String.valueOf(quantity));
            holder.textcount.setVisibility(View.VISIBLE);
            cartPojo.setCategoryName(CategoryName);
            cartPojo.setProductName(ProductName);
            cartPojo.setProduct_img(ProductImage);
            cartPojo.setQuantity(Quantity);
            int result = (Integer.valueOf(Price))*(Integer.valueOf(holder.textcount.getText().toString()));
            String p = (String.valueOf(result));
            cartPojo.setSubprice(p);
            holder.subprice.setText(Subtotal);
            cartPojo.setPrice(Price);
            cartPojo.setCount(holder.textcount.getText().toString());
            reff.child(upload.getProductName()).setValue(cartPojo);

//            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.f_container, new Cart_Fragment())
//                    .commit();
        }
    });
}

    @Override
public int getItemCount() {
        return uploads.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView Cat_name,pro_name,price,quantity,add,minus,plus,subprice,couponAmt;
    public ImageView imageView;
    public TextView textcount;
    public LinearLayout laypm;
    public ViewHolder(View itemView) {
        super(itemView);

        Cat_name = (TextView) itemView.findViewById(R.id.txt_cat);
        imageView = (ImageView) itemView.findViewById(R.id.proimage);
        pro_name = (TextView) itemView.findViewById(R.id.pro_name);
        price = (TextView) itemView.findViewById(R.id.price);
        quantity = (TextView) itemView.findViewById(R.id.quantity);
        add= (TextView) itemView.findViewById(R.id.Add);
        minus = (TextView) itemView.findViewById(R.id.minus);
        subprice= (TextView) itemView.findViewById(R.id.subtotal);
        plus = (TextView) itemView.findViewById(R.id.plus);
        textcount = (TextView) itemView.findViewById(R.id.count_text);
        laypm = (LinearLayout) itemView.findViewById(R.id.laypm);


    }
}
}