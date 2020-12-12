package com.fv.freshvegapp.Products;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fv.freshvegapp.Cart.CartPojo;
import com.fv.freshvegapp.R;
import com.fv.freshvegapp.UploadPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class Deal_Adaptor extends RecyclerView.Adapter<Deal_Adaptor.ViewHolder> {

private Context context;
private List<UploadPojo> uploads;
int offint = 0,value =0;

public Deal_Adaptor(Context context, List<UploadPojo> uploads) {
        this.uploads = uploads;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.product_recycleview_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override


public void onBindViewHolder(final ViewHolder holder, final int position) {

    final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Cart_Items").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    final CartPojo cartPojo = new CartPojo();
    final String CategoryName,ProductName,ProductImage,Quantity,Price,Mrp,Count;
    final String Cart_CategoryName,Cart_ProductName,Cart_ProductImage,Cart_Quantity,Cart_Price,Cart_Mrp;
    Cart_CategoryName = cartPojo.getCategoryName();
    Cart_ProductName = cartPojo.getProductName();
    Cart_ProductImage = cartPojo.getProduct_img();
    Cart_Quantity = cartPojo.getQuantity();
    Cart_Price = cartPojo.getPrice();
    Cart_Mrp = cartPojo.getMrp();

    final UploadPojo upload = uploads.get(position);
    CategoryName = upload.getCategory();
    ProductName = upload.getProduct();
    ProductImage = upload.getProduct_img();
    Quantity = upload.getQuantity();
    Price = upload.getPrice();
    Mrp  = upload.getMrp();
    Count = upload.getCount();

        holder.Cat_name.setText(upload.getCategory());
        Glide.with(context)
                .load(upload.getProduct_img())
                .centerCrop()
                .into(holder.imageView);

    holder.pro_name.setText(upload.getProduct());
    holder.price.setText(upload.getPrice());
    holder.mrp.setText(Mrp);
    holder.quantity.setText(upload.getQuantity());

    offint = 100*Integer.parseInt(Price)/Integer.parseInt(Mrp);
    value = 100 - offint;

    holder.off.setText(String.valueOf(value));

    holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    holder.rupsym.setPaintFlags(holder.rupsym.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    DatabaseReference OOSref= FirebaseDatabase.getInstance().getReference().child("Uploads").child(upload.getCode());
    OOSref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String OOS = snapshot.child("oos").getValue().toString();

            if (OOS.equals("outofstock")){

                holder.addtocartlay.setVisibility(View.GONE);
                holder.greyback.setVisibility(View.VISIBLE);
                holder.textoos.setVisibility(View.VISIBLE);



            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


    Query query = reff.orderByChild("productName").equalTo(ProductName);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot ds: snapshot.getChildren()) {
                String C = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    C = Objects.requireNonNull(ds.child("count").getValue()).toString();
                }
                holder.textcount.setText(C);
                holder.add.setVisibility(View.GONE);
                holder.laypm.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    holder.add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int a = 1;
            holder.textcount.setText(String.valueOf(a));
            holder.add.setVisibility(View.GONE);
            holder.laypm.setVisibility(View.VISIBLE);
            // add to user cart Items
            cartPojo.setCategoryName(CategoryName);
            cartPojo.setProduct_img(ProductImage);
            cartPojo.setProductName(ProductName);
            cartPojo.setQuantity(Quantity);
            cartPojo.setMrp(Mrp);
            int result = (Integer.valueOf(Price))*(Integer.valueOf(a));
            String p = (String.valueOf(result));
            cartPojo.setSubprice(p);
            cartPojo.setPrice(Price);
            cartPojo.setCount(String.valueOf(a));
            reff.child(ProductName).setValue(cartPojo);
            notifyDataSetChanged();
        }
    });

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
                                holder.textcount.setText(finalQuantity + "");
                                holder.laypm.setVisibility(View.INVISIBLE);
                                holder.add.setVisibility(View.VISIBLE);
                                reff.child(ProductName).removeValue();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else {
                holder.textcount.setText(String.valueOf(quantity));
                holder.textcount.setVisibility(View.VISIBLE);
                cartPojo.setCategoryName(CategoryName);
                cartPojo.setProductName(ProductName);
                cartPojo.setProduct_img(ProductImage);
                cartPojo.setQuantity(Quantity);
                int result = (Integer.valueOf(Price))*(Integer.valueOf(holder.textcount.getText().toString()));
                String p = (String.valueOf(result));
                cartPojo.setSubprice(p);
                cartPojo.setPrice(Price);
                cartPojo.setMrp(Mrp);
                cartPojo.setCount(holder.textcount.getText().toString());
                reff.child(ProductName).setValue(cartPojo);
                notifyDataSetChanged();
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
            cartPojo.setPrice(Price);
            cartPojo.setMrp(Mrp);
            cartPojo.setCount(holder.textcount.getText().toString());
            reff.child(ProductName).setValue(cartPojo);
            notifyDataSetChanged();
        }
    });
}

@Override
public int getItemCount() {
        return uploads.size();
        }


class ViewHolder extends RecyclerView.ViewHolder {

    public TextView Cat_name,pro_name,mrp,off,price,quantity,add,minus,plus,rupsym;
    public ImageView imageView;
    public TextView textcount,textoos;
    public LinearLayout laypm,addtocartlay,greyback;
    public CardView itemlay2;
    public ViewHolder(View itemView) {
        super(itemView);

        Cat_name = (TextView) itemView.findViewById(R.id.txt_cat);
        imageView = (ImageView) itemView.findViewById(R.id.proimage);
        pro_name = (TextView) itemView.findViewById(R.id.pro_name);
        price = (TextView) itemView.findViewById(R.id.price);
        rupsym = (TextView) itemView.findViewById(R.id.rupeesym);
        mrp  = (TextView) itemView.findViewById(R.id.mrp);
        off  = (TextView) itemView.findViewById(R.id.off);
        quantity = (TextView) itemView.findViewById(R.id.quantity);
        add= (TextView) itemView.findViewById(R.id.Add);
        minus = (TextView) itemView.findViewById(R.id.minus);
        plus = (TextView) itemView.findViewById(R.id.plus);
        textcount = (TextView) itemView.findViewById(R.id.count_text);
        laypm= (LinearLayout) itemView.findViewById(R.id.laypm);
        addtocartlay = (LinearLayout) itemView.findViewById(R.id.addtocartlay);
        greyback = (LinearLayout) itemView.findViewById(R.id.greyback);
        textoos = (TextView) itemView.findViewById(R.id.textoos);
        itemlay2 = (CardView) itemView.findViewById(R.id.itemview2);

    }
}
}