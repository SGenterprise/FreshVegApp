package com.fv.freshvegapp.Address;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Address_Adaptor extends RecyclerView.Adapter<Address_Adaptor.ViewHolder> {

private Context context;
private List<AddressPojo> uploads;

public Address_Adaptor(Context context, List<AddressPojo> uploads) {
        this.uploads = uploads;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.address_desgin, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(final ViewHolder holder, final int position) {

    final AddressPojo upload = uploads.get(position);

    holder.name.setText(upload.getName());
    holder.add1.setText(upload.getAddress1());
    holder.num.setText(upload.getNumber());
    holder.landmark.setText(upload.getLandmark());
    holder.hou.setText(upload.getHouseno());
    holder.bul.setText(upload.getBuilding());

    holder.edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferences preferences = context.getSharedPreferences("Updateaddress",MODE_PRIVATE);
            String name = holder.name.getText().toString().trim();
            String add1 = holder.add1.getText().toString().trim();
            String num = holder.num.getText().toString().trim();
            String landmark = holder.landmark.getText().toString().trim();
            String house = holder.hou.getText().toString().trim();
            String bu = holder.bul.getText().toString().trim();
            String lati = upload.getLatitude();
            String longi = upload.getLongitude();
            String id = upload.getId();
            String pin = upload.getPin();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name",name);
            editor.putString("add1",add1);
            editor.putString("num",num);
            editor.putString("landmark",landmark);
            editor.putString("houseno",house);
            editor.putString("bu",bu);
            editor.putString("lati",lati);
            editor.putString("longi",longi);
            editor.putString("id",id);
            editor.putString("pincode",pin);
            editor.apply();

            Intent i = new Intent(context, Address_Update.class);
            context.startActivity(i);

            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
    });
    holder.remove.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("Click Confirm to Delete Address.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            String id1 = upload.getId();
                            DatabaseReference mDatabase;
                            mDatabase = FirebaseDatabase.getInstance().getReference("ADDRESS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            mDatabase.child(id1).removeValue();
                            Toast.makeText(view.getContext(),"Address Removed", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();


        }
    });
        }

@Override
public int getItemCount() {
        return uploads.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView name,hou,bul,add1,num,landmark,edit,remove;

    public ViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.name);
        add1 = (TextView) itemView.findViewById(R.id.addline1);
        num = (TextView) itemView.findViewById(R.id.numberoftime);
        landmark = (TextView) itemView.findViewById(R.id.landmark);
        hou= (TextView) itemView.findViewById(R.id.houseno);
        bul = (TextView) itemView.findViewById(R.id.build);
        edit = (TextView) itemView.findViewById(R.id.edit_add);
        remove = (TextView) itemView.findViewById(R.id.remove_add);
    }
}
}