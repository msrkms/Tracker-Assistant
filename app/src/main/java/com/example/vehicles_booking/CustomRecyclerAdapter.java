package com.example.vehicles_booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

  ArrayList<RecyclerViewMainModel> RMainModels;
  Context context;

     public  CustomRecyclerAdapter(Context context,ArrayList<RecyclerViewMainModel> RMainModels){
         this.context=context;
         this.RMainModels=RMainModels;

     }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         //Create View
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

         //Set logo/pic to imageView
        holder.imageView.setImageResource(RMainModels.get(position).getLangLogo());
        //when Set Any text
        //holder.imageView.setText(RMainModels.get(position).get());

    }

    @Override
    public int getItemCount() {
        return RMainModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       //Initialize variable
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Assign variable
            imageView=itemView.findViewById(R.id.image_view);
        }
    }
}
