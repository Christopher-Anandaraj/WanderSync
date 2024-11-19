package com.example.sprintproject.view.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentCommunityBinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.view.ui.dining.DiningEntry;

import java.util.ArrayList;

//Allyson Implementation ----------------------------------------------
public class CommunityRecycleViewAdapter extends RecyclerView.Adapter<com.example.sprintproject.view.ui.dining.DiningRecycleViewAdapter.MyViewHolder> {

    //variables for dining entries
    Context context; //for inflator
    ArrayList<CommunityEntry> communityEntries;

    //constructor
    public CommunityRecycleViewAdapter(Context context, ArrayList<DiningEntry> communityEntries) {
        this.context = context;
        this.communityEntries = communityEntries;
    }

    //creates 'inflate' layout and gets our next box
    @NonNull
    @Override
    public com.example.sprintproject.view.ui.dining.DiningRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layouts (giving a look to our rows)
        LayoutInflater inflator = LayoutInflater.from(context);
        //inflates based on fragment_community_entries.xml
        View view = inflator.inflate(R.layout.fragment_community_entry, parent, false);
        return new com.example.sprintproject.view.ui.dining.DiningRecycleViewAdapter.MyViewHolder(view);
    }

    //assigns values to each row as it comes back on screen
    @Override
    public void onBindViewHolder(@NonNull com.example.sprintproject.view.ui.community.CommunityRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.location.setText(communityEntries.get(position).getLocation());
        holder.food.setText(communityEntries.get(position).getFood());
        holder.link.setText(communityEntries.get(position).getTime());


    }

    //helps on binding process by keeping count
    @Override
    public int getItemCount() {
        return diningEntries.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //assign textview vars
        TextView location;
        TextView restaurant;
        TextView time;
        TextView link;

        //might have to change view import
        //kinda like an on create method for each little box
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //check import method for R
            //update textViews to correct values
            location = itemView.findViewById(R.id.community_entry_location);
            food = itemView.findViewById(R.id.community_entry_dining);
            time = itemView.findViewById(R.id.reservation_entry_time);
            link = itemView.findViewById(R.id.reservation_entry_link);
        }
    }