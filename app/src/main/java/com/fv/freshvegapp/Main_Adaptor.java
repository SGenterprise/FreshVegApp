package com.fv.freshvegapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fv.freshvegapp.Products.Product_recycleview;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Main_Adaptor extends RecyclerView.Adapter<Main_Adaptor.ViewHolder> {

private Context context;
private List<UploadPojo> uploads;

public Main_Adaptor(Context context, List<UploadPojo> uploads) {
        this.uploads = uploads;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.main_recycleview_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(final ViewHolder holder, final int position) {


    final UploadPojo upload = uploads.get(position);

        holder.Cat_name.setText(upload.getCategory());

        Glide.with(context)
                .load(upload.getCategory_img())
                .centerCrop()
                .into(holder.imageView);

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(final View v) {
//
//                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                String[] options={"Update","Delete"};
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which ){
//                        DatabaseReference reff;
//
//                        if (which==0){
//                            SharedPreferences preferences = context.getSharedPreferences("MYPREFS",MODE_PRIVATE);
//                            SharedPreferences.Editor editor = preferences.edit();
//
//                            String st = holder.Cat_name.getText().toString().trim();
//                            editor.putString("upCatname",st);
//
//                            String image_product = (upload.getImageURL());
//                            editor.putString("image_code_key",image_product);
//
//                            editor.apply();
//
//                            Intent i = new Intent(context, Category_Update.class);
//                            context.startActivity(i);
//
//                        }
//                        if (which==1){
//                            reff= FirebaseDatabase.getInstance().getReference().child("Category");
//                            reff.child(upload.getImageName()).removeValue();
//                            Toast.makeText(v.getContext(),upload.getImageName() + position, Toast.LENGTH_LONG).show();
//                            notifyDataSetChanged();
//                        }
//                    }
//                });
//                builder.create().show();
//                return true;
//            }
//        });
//
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SharedPreferences preferences = context.getSharedPreferences("MYPREFS",MODE_PRIVATE);

            String cat123 = holder.Cat_name.getText().toString().trim();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("cat123",cat123);
            editor.apply();

            AppCompatActivity activity = (AppCompatActivity) context;
            Product_recycleview myFragment = new Product_recycleview();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.f_container, myFragment).addToBackStack(null).commit();
        }
    });

        }

@Override
public int getItemCount() {
        return uploads.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView Cat_name;
    public ImageView imageView;
    public ViewHolder(View itemView) {
        super(itemView);

        Cat_name = (TextView) itemView.findViewById(R.id.txt_cat);
        imageView = (ImageView) itemView.findViewById(R.id.image_cat);
    }
}
}