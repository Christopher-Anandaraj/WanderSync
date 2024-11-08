package com.example.sprintproject.view.ui.dining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;

import java.util.ArrayList;

//please note that some of these methods are automatic by androidstudios, not my personal implementation
public class DiningRecycleViewAdapter extends RecyclerView.Adapter<DiningRecycleViewAdapter.MyViewHolder> {

    //variables for dining entries
    Context context; //for inflator
    ArrayList<DiningEntry> diningEntries;

    //constructor
    public DiningRecycleViewAdapter(Context context, ArrayList<DiningEntry> diningEntries) {
        this.context = context;
        this.diningEntries = diningEntries;
    }

    //creates 'inflate' layout and gets our next box
    @NonNull
    @Override
    public DiningRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layouts (giving a look to our rows)
        LayoutInflater inflator = LayoutInflater.from(context);
        //inflates based on fragment_dining_list_entries.xml
        View view = inflator.inflate(R.layout.fragment_dining_list_entries, parent, false);
        return new DiningRecycleViewAdapter.MyViewHolder(view);
    }

    //assigns values to each row as it comes back on screen
    @Override
    public void onBindViewHolder(@NonNull DiningRecycleViewAdapter.MyViewHolder holder, int position) {

        //change textview text to values stored in diningEntries from arrayList
        holder.location.setText(diningEntries.get(position).getLocation());
        holder.restaurant.setText(diningEntries.get(position).getRestaurant());
        holder.time.setText(diningEntries.get(position).getTime());
        holder.link.setText(diningEntries.get(position).getLink());

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
            location = itemView.findViewById(R.id.reservation_entry_location);
            restaurant = itemView.findViewById(R.id.reservation_entry_restaurant);
            time = itemView.findViewById(R.id.reservation_entry_time);
            link = itemView.findViewById(R.id.reservation_entry_link);
        }
    }
}
