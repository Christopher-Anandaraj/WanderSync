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

    private final RecycleViewInterface recycleViewInterface;
    //variables for dining entries
    private Context context; //for inflator
    private ArrayList<CommunityEntry> communityEntries;

    //constructor
    public CommunityRecycleViewAdapter(Context context,
                                       ArrayList<CommunityEntry> communityEntries,
                                       RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.communityEntries = communityEntries;
        this.recycleViewInterface = recycleViewInterface;
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
        return new CommunityRecycleViewAdapter.MyViewHolder(view, recycleViewInterface);
    }

    //assigns values to each row as it comes back on screen
    @Override
    public void onBindViewHolder(@NonNull CommunityRecycleViewAdapter.MyViewHolder holder,
                                 int position) {
        //CommunityEntry entry = communityEntries.get(position);
        holder.destination.setText(communityEntries.get(position).getDestination());
        holder.accomodations.setText(communityEntries.get(position).getAccommodationsReview());
        holder.startDate.setText(communityEntries.get(position).getStartDate());
        holder.endDate.setText(communityEntries.get(position).getEndDate());
        holder.diningReview.setText(communityEntries.get(position).getDiningReview());
        holder.tripNotes.setText(communityEntries.get(position).getTripNotes());

    }

    //helps on binding process by keeping count
    @Override
    public int getItemCount() {
        return communityEntries.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //assign textview vars
        private TextView diningReview;
        private TextView accomodations;
        private TextView destination;
        private TextView tripNotes;
        private TextView startDate;
        private TextView endDate;

        //might have to change view import
        //kinda like an on create method for each little box
        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);

            //update textViews to correct values
            destination = itemView.findViewById(R.id.community_entry_location);
            accomodations = itemView.findViewById(R.id.community_entry_accomodations);
            startDate = itemView.findViewById(R.id.community_startDate_entry);
            endDate = itemView.findViewById(R.id.community_endDate_entry);
            diningReview = itemView.findViewById(R.id.community_entry_dining);
            tripNotes = itemView.findViewById(R.id.community_entry_notes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}