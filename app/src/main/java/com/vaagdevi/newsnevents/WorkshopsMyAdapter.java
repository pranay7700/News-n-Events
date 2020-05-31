package com.vaagdevi.newsnevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WorkshopsMyAdapter extends RecyclerView.Adapter<WorkshopsMyAdapter.MyViewHolder>{

    Context context;
    ArrayList<WorkshopsRegdatabase> workshopsRegdatabase;

    public WorkshopsMyAdapter(Context c , ArrayList<WorkshopsRegdatabase> r)
    {
        context = c;
        workshopsRegdatabase = r;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.workshops_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(workshopsRegdatabase.get(position).getName());
        holder.description.setText(workshopsRegdatabase.get(position).getDescription());
        Picasso.get().load(workshopsRegdatabase.get(position).getImage()).into(holder.image);
        if(workshopsRegdatabase.get(position).isPermission()) {
            holder.permission.setVisibility(View.VISIBLE);
            holder.onClick(position);
        }

    }

    @Override
    public int getItemCount() {
        return workshopsRegdatabase.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,description;
        ImageView image;
        Button permission;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.workshops_nameTV);
            description = (TextView) itemView.findViewById(R.id.workshops_descTV);
            image = (ImageView) itemView.findViewById(R.id.workshops_imageIV);
            permission = (Button) itemView.findViewById(R.id.workshops_checkdetailsBTN);
        }
        public void onClick(final int position)
        {
            permission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
