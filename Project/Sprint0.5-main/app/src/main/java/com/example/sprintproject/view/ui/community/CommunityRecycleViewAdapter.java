package com.example.sprintproject.view.ui.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;

import java.util.ArrayList;

//Allyson Implementation ----------------------------------------------
public class CommunityRecycleViewAdapter extends
        RecyclerView.Adapter
                <com.example.sprintproject.view.ui.community.
                        CommunityRecycleViewAdapter.MyViewHolder> {

//    private final RecycleViewInterface recycleViewInterface;
    //variables for dining entries
    private Context context; //for inflator
    private ArrayList<CommunityEntry> communityEntries;

    //constructor
    public CommunityRecycleViewAdapter(Context context,
                                       ArrayList<CommunityEntry> communityEntries) {
        this.context = context;
        this.communityEntries = communityEntries;
//        this.recycleViewInterface = recycleViewInterface;
    }

    //creates 'inflate' layout and gets our next box
    @NonNull
    @Override
    public CommunityRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                       int viewType) {
        //inflate layouts (giving a look to our rows)
        LayoutInflater inflator = LayoutInflater.from(context);
        //inflates based on fragment_community_entries.xml
        View view = inflator.inflate(R.layout.fragment_community_entry, parent, false);
        return new CommunityRecycleViewAdapter.MyViewHolder(view);
    }

    //assigns values to each row as it comes back on screen
    @Override
    public void onBindViewHolder(@NonNull CommunityRecycleViewAdapter.MyViewHolder holder,
                                 int position) {
        //CommunityEntry entry = communityEntries.get(position);
        holder.username.setText(CommunityFragment.getCurrentUsername());
        holder.destination.setText(communityEntries.get(position).getDestination());
        holder.duration.setText(communityEntries.get(position).getDuration() + " days");
        holder.dining.setText(communityEntries.get(position).getDiningReview());
        holder.accomodations.setText(communityEntries.get(position).getAccommodationsReview());
        holder.transportation.setText(communityEntries.get(position).getTransportation());
        holder.tripNotes.setText(communityEntries.get(position).getTripNotes());

    }

    //helps on binding process by keeping count
    @Override
    public int getItemCount() {
        return communityEntries.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //assign textview vars
        private TextView username;
        private TextView duration;
        private TextView destination;
        private TextView dining;
        private TextView accomodations;
        private TextView transportation;
        private TextView tripNotes;

        //might have to change view import
        //kinda like an on create method for each little box
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //update textViews to correct values
            username = itemView.findViewById(R.id.community_entry_username);
            duration = itemView.findViewById(R.id.community_entry_duration);
            destination = itemView.findViewById(R.id.community_entry_location);
            accomodations = itemView.findViewById(R.id.community_entry_accomodations);
            dining = itemView.findViewById(R.id.community_entry_dining);
            transportation = itemView.findViewById(R.id.community_entry_transportation);
            tripNotes = itemView.findViewById(R.id.community_entry_notes);
        }
    }
}