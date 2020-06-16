package com.vaagdevi.newsnevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationsMyAdapter extends RecyclerView.Adapter<NotificationsMyAdapter.MyViewHolder> {

    Context context;
    ArrayList<NotificationsRegdatabase> notificationsRegdatabase;

    public NotificationsMyAdapter(Context c , ArrayList<NotificationsRegdatabase> n)
    {
        context = c;
        notificationsRegdatabase = n;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationsMyAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.notifications_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(notificationsRegdatabase.get(position).getImage()).into(holder.image);
        holder.title.setText(notificationsRegdatabase.get(position).getTitle());
        holder.description.setText(notificationsRegdatabase.get(position).getDescription());
        holder.date.setText(notificationsRegdatabase.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return notificationsRegdatabase.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,date;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.notifications_imageIV);
            title = (TextView) itemView.findViewById(R.id.notifications_titleTV);
            description = (TextView) itemView.findViewById(R.id.notifications_descTV);
            date = (TextView) itemView.findViewById(R.id.notifications_dateTV);
        }
    }
}
