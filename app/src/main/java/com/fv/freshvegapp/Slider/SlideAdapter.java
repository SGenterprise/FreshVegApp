package com.fv.freshvegapp.Slider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fv.freshvegapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderViewAdapter;


public class SlideAdapter extends SliderViewAdapter<SlideAdapter.ViewHolder> {
    private Context context;
    int setTotalCount;
    String imageurl,des,title,count;


    public SlideAdapter(Context context, int setTotalCount) {
        this.context = context;
        this.setTotalCount = setTotalCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_items_layout,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        FirebaseDatabase.getInstance().getReference("Slider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                switch (position){
                    case 0:
                        RequestOptions options = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();

                        imageurl = snapshot.child("1").child("imageURL").getValue().toString();

                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 1:
                        RequestOptions options1 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("2").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options1)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 2:
                        RequestOptions options2 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("3").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options2)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 3:
                        RequestOptions options3 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("4").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options3)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 4:
                        RequestOptions options4 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("5").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options4)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 5:
                        RequestOptions options5 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("6").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options5)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 6:
                        RequestOptions options6 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("7").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options6)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 7:
                        RequestOptions options7 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("8").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options7)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 8:
                        RequestOptions options8 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("9").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options8)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                    case 9:
                        RequestOptions options9 = new RequestOptions()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.no_wifi)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        imageurl = snapshot.child("10").child("imageURL").getValue().toString();
                        Glide.with(viewHolder.itemView)
                                .load(imageurl)
                                .apply(options9)
                                .centerCrop()
                                .into(viewHolder.sliderImageView);
                        break;
                }

                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        String[] options={"Update","Delete"};
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which ){

                                DatabaseReference reff;

//                                if (which==0){
//                                    switch (position) {
//                                        case 0:
//                                            imageurl = snapshot.child("1").child("imageURL").getValue().toString();
//                                            title = snapshot.child("1").child("imageName").getValue().toString();
//                                            des = snapshot.child("1").child("description").getValue().toString();
//                                            Intent i = new Intent(context, Slider_Update.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",1);
//                                            context.startActivity(i);
//                                            break;
//                                        case 1:
//                                            imageurl = snapshot.child("2").child("imageURL").getValue().toString();
//                                            title = snapshot.child("2").child("imageName").getValue().toString();
//                                            des = snapshot.child("2").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",2);
//                                            context.startActivity(i);
//                                            break;
//                                        case 2:
//                                            imageurl = snapshot.child("3").child("imageURL").getValue().toString();
//                                            title = snapshot.child("3").child("imageName").getValue().toString();
//                                            des = snapshot.child("3").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",3);
//                                            context.startActivity(i);
//                                            break;
//                                        case 3:
//                                            imageurl = snapshot.child("4").child("imageURL").getValue().toString();
//                                            title = snapshot.child("4").child("imageName").getValue().toString();
//                                            des = snapshot.child("4").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",4);
//                                            context.startActivity(i);
//                                            break;
//                                        case 4:
//                                            imageurl = snapshot.child("5").child("imageURL").getValue().toString();
//                                            title = snapshot.child("5").child("imageName").getValue().toString();
//                                            des = snapshot.child("5").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",5);
//                                            context.startActivity(i);
//                                            break;
//                                        case 5:
//                                            imageurl = snapshot.child("6").child("imageURL").getValue().toString();
//                                            title = snapshot.child("6").child("imageName").getValue().toString();
//                                            des = snapshot.child("6").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",6);
//                                            context.startActivity(i);
//                                            break;
//                                        case 6:
//                                            imageurl = snapshot.child("7").child("imageURL").getValue().toString();
//                                            title = snapshot.child("7").child("imageName").getValue().toString();
//                                            des = snapshot.child("7").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",7);
//                                            context.startActivity(i);
//                                            break;
//                                        case 7:
//                                            imageurl = snapshot.child("8").child("imageURL").getValue().toString();
//                                            title = snapshot.child("8").child("imageName").getValue().toString();
//                                            des = snapshot.child("8").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",8);
//                                            context.startActivity(i);
//                                            break;
//                                        case 8:
//                                            imageurl = snapshot.child("9").child("imageURL").getValue().toString();
//                                            title = snapshot.child("9").child("imageName").getValue().toString();
//                                            des = snapshot.child("9").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",9);
//                                            context.startActivity(i);
//                                            break;
//                                        case 9:
//                                            imageurl = snapshot.child("10").child("imageURL").getValue().toString();
//                                            title = snapshot.child("10").child("imageName").getValue().toString();
//                                            des = snapshot.child("10").child("description").getValue().toString();
//                                            i = new Intent(context, Slide_Detail.class);
//                                            i.putExtra("url",imageurl);
//                                            i.putExtra("title",title);
//                                            i.putExtra("des",des);
//                                            i.putExtra("count",10);
//                                            context.startActivity(i);
//                                            break;
//                                    }
//                                }

//                                if (which==1){
//                                    switch (position) {
//                                        case 0:
//                                            snapshot.getRef().child("1").removeValue();
//                                            break;
//                                        case 1:
//                                            snapshot.getRef().child("2").removeValue();
//                                            break;
//                                        case 2:
//                                            snapshot.getRef().child("3").removeValue();
//                                            break;
//                                        case 3:
//                                            snapshot.getRef().child("4").removeValue();
//                                            break;
//                                        case 4:
//                                            snapshot.getRef().child("5").removeValue();
//                                            break;
//                                        case 5:
//                                            snapshot.getRef().child("6").removeValue();
//                                            break;
//                                        case 6:
//                                            snapshot.getRef().child("7").removeValue();
//                                            break;
//                                        case 7:
//                                            snapshot.getRef().child("8").removeValue();
//                                            break;
//                                        case 8:
//                                            snapshot.getRef().child("9").removeValue();
//                                            break;
//                                        case 9:
//                                            snapshot.getRef().child("10").removeValue();
//                                            break;
//                                    }
//                                }
                            }
                        });
                        builder.create().show();
                        return true;

                    }
                });

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                imageurl = snapshot.child("1").child("imageURL").getValue().toString();
                                title = snapshot.child("1").child("imageName").getValue().toString();
                                des = snapshot.child("1").child("description").getValue().toString();
                                Intent i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 1:
                                imageurl = snapshot.child("2").child("imageURL").getValue().toString();
                                title = snapshot.child("2").child("imageName").getValue().toString();
                                des = snapshot.child("2").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 2:
                                imageurl = snapshot.child("3").child("imageURL").getValue().toString();
                                title = snapshot.child("3").child("imageName").getValue().toString();
                                des = snapshot.child("3").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 3:
                                imageurl = snapshot.child("4").child("imageURL").getValue().toString();
                                title = snapshot.child("4").child("imageName").getValue().toString();
                                des = snapshot.child("4").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 4:
                                imageurl = snapshot.child("5").child("imageURL").getValue().toString();
                                title = snapshot.child("5").child("imageName").getValue().toString();
                                des = snapshot.child("5").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 5:
                                imageurl = snapshot.child("6").child("imageURL").getValue().toString();
                                title = snapshot.child("6").child("imageName").getValue().toString();
                                des = snapshot.child("6").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 6:
                                imageurl = snapshot.child("7").child("imageURL").getValue().toString();
                                title = snapshot.child("7").child("imageName").getValue().toString();
                                des = snapshot.child("7").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 7:
                                imageurl = snapshot.child("8").child("imageURL").getValue().toString();
                                title = snapshot.child("8").child("imageName").getValue().toString();
                                des = snapshot.child("8").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 8:
                                imageurl = snapshot.child("9").child("imageURL").getValue().toString();
                                title = snapshot.child("9").child("imageName").getValue().toString();
                                des = snapshot.child("9").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                            case 9:
                                imageurl = snapshot.child("10").child("imageURL").getValue().toString();
                                title = snapshot.child("10").child("imageName").getValue().toString();
                                des = snapshot.child("10").child("description").getValue().toString();
                                i = new Intent(context, Slide_Detail.class);
                                i.putExtra("url",imageurl);
                                i.putExtra("title",title);
                                i.putExtra("des",des);
                                if(!title.equals("")){
                                    context.startActivity(i);
                                }
                                break;
                        }

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public int getCount() {
        return setTotalCount;
    }

    static class ViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView sliderImageView;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            sliderImageView = itemView.findViewById(R.id.imageview_id);
            this.itemView = itemView;
        }
    }
}
