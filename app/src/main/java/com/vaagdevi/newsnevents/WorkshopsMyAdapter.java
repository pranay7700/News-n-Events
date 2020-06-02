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

public class WorkshopsMyAdapter extends RecyclerView.Adapter<WorkshopsMyAdapter.MyViewHolder>{

    Context context;
    ArrayList<WorkshopsRegdatabase> workshopsRegdatabase;

    public WorkshopsMyAdapter(Context c , ArrayList<WorkshopsRegdatabase> w)
    {
        context = c;
        workshopsRegdatabase = w;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.workshops_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(workshopsRegdatabase.get(position).getImage()).into(holder.image);
        holder.name.setText(workshopsRegdatabase.get(position).getName());
        holder.description.setText(workshopsRegdatabase.get(position).getDescription());
        holder.place.setText(workshopsRegdatabase.get(position).getPlace());
        holder.date.setText(workshopsRegdatabase.get(position).getDate());
        holder.time.setText(workshopsRegdatabase.get(position).getTime());


    }

    @Override
    public int getItemCount() {
        return workshopsRegdatabase.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,description,place,date,time;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.workshops_imageIV);
            name = (TextView) itemView.findViewById(R.id.workshops_nameTV);
            description = (TextView) itemView.findViewById(R.id.workshops_descTV);
            place = (TextView) itemView.findViewById(R.id.workshops_placeTV);
            date = (TextView) itemView.findViewById(R.id.workshops_dateTV);
            time = (TextView) itemView.findViewById(R.id.workshops_timeTV);

        }

    }

}
