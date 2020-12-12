package com.fv.freshvegapp.Cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fv.freshvegapp.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CalenderApater extends RecyclerView.Adapter<CalenderApater.MyViewHolder> {
    List<CalenderModel> CalData;
    GetFullDateListener getFullDateListener;
    private Context context;

    public CalenderApater(Context context,List<CalenderModel> calData) {
        CalData = calData;
        this.context = context;
    }

    public void getFullDateListener(GetFullDateListener getFullDateListener) {
        this.getFullDateListener = getFullDateListener;
    }


    public String fulldate = "";
    int rowIndex;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calender_itemview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final CalenderModel calenderModel = CalData.get(position);
        holder.day.setText(calenderModel.getCalenderDay());
        holder.date.setText(calenderModel.getCalenderDate());

        holder.setIsRecyclable(false);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.bindData(position, CalData);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowIndex = position;

                SharedPreferences preferences = context.getSharedPreferences("date",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String a = calenderModel.getFulldate();
                editor.putString("fulldate",a);
                editor.apply();

                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return CalData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView day, date, MonthId;
        LinearLayout DateLinearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.dayId);
            date = (TextView) itemView.findViewById(R.id.dateId);
            DateLinearLayout = (LinearLayout) itemView.findViewById(R.id.DateLinearLayout);

        }

        public void bindData(final int position, final List<CalenderModel> calender) {

            date.setText(calender.get(position).getCalenderDate());
            day.setText(calender.get(position).getCalenderDay());



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rowIndex = position;
                    Log.i("fullDate", fulldate + "activit");
                    notifyDataSetChanged();
                }
            });

            if (rowIndex == position) {
                fulldate = CalData.get(position).getFulldate();
                getFullDateListener.getFullDateListener(fulldate);
                date.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
                day.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
                DateLinearLayout.setBackground(itemView.getContext().getResources().getDrawable(R.color.red));

                SharedPreferences preferences = context.getSharedPreferences("date",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
 //               String a = day.getText().toString()+" "+date.getText().toString();
                String a = fulldate;
                editor.putString("fulldate",a);
                editor.apply();

            } else {
                date.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                day.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                DateLinearLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.bstroke_rectengle));

            }
        }

    }

    public interface GetFullDateListener {
        public void getFullDateListener(String date);

    }

}

