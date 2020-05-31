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

public class GuestLecturesMyAdapter extends RecyclerView.Adapter<GuestLecturesMyAdapter.MyViewHolder> {

    Context context;
    ArrayList<GuestLecturesRegdatabase> profiles;

    public GuestLecturesMyAdapter(Context c, ArrayList<GuestLecturesRegdatabase> p){
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.guestlectures_cardview,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(profiles.get(position).getName());
        holder.email.setText(profiles.get(position).getEmail());
        Picasso.get().load(profiles.get(position).getProfilepic()).into(holder.profilePic);
        if(profiles.get(position).getPermission()) {
            holder.checkDetails.setVisibility(View.VISIBLE);
            holder.onClick(position);
        }

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        ImageView profilePic;
        Button checkDetails;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.guestlecture_nameTV);
            email = (TextView) itemView.findViewById(R.id.guestlecture_emailTV);
            profilePic = (ImageView) itemView.findViewById(R.id.guestlecture_profilepicIV);
            checkDetails = (Button) itemView.findViewById(R.id.guestlecture_checkdetailsBTN);

        }

        public void onClick(final int position)
        {
            checkDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
