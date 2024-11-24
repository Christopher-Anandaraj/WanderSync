package com.example.sprintproject.view.ui.community;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.databinding.FragmentCommunityBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.ui.ReservationUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    private ArrayList<CommunityEntry> communityEntries = new ArrayList<>();
    private FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
    private String uid = currentUser.getUid();
    private static String currentUsername;
    private DatabaseReference tripRef = FirebaseManager.getInstance().getDatabaseReference()
            .child("communityEntry");

    private FragmentCommunityBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CommunityViewModel communityViewModel =
                new ViewModelProvider(this).get(CommunityViewModel.class);

        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final long[] foundDuration = new long[1];
        Spinner tripDestination = binding.communityPostDestination;
        EditText accommodationReservations = binding.communityPostAccommodations;
        EditText diningReservations = binding.communityPostDining;
        EditText tripTransportation = binding.communityPostTransportation;
        EditText tripNotes = binding.communityPostNotes;

        TextView startDateTextDisplay = binding.startDateTextDisplay;
        TextView endDateTextDisplay = binding.endDateTextDisplay;

        Button startDateButton = binding.startDateButtonCommunity;
        Button endDateButton = binding.endDateButtonCommunity;

        Button submitPostButton = binding.addCommunityPostButton;
        Button openCreatePostButton = binding.createCommunityPostButton;

        Context fragContext = requireContext();

        RecyclerView recyclerView = binding.communityPostList;
        CommunityRecycleViewAdapter adapter = new CommunityRecycleViewAdapter(this.getContext(),
                communityEntries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        CardView communityCard = binding.createCommunityPostCardview;
        communityCard.setVisibility(View.GONE);
        openCreatePostButton.setOnClickListener(v -> {
            if (communityCard.getVisibility() == View.GONE) {
                communityCard.setVisibility(View.VISIBLE);
            } else {
                communityCard.setVisibility(View.GONE);
            }
        });

        LoadFromCommunityDatabase load = new LoadFromCommunityDatabase();
        load.interactWithCommunityDatabase(currentUser, tripRef, null,
                communityEntries, fragContext, adapter);
        adapter.notifyDataSetChanged();

        //calls a new date picker fragment when the button is clicked
        startDateButton.setOnClickListener(v -> {
            DatePickerFragment startDatePicker =
                    DatePickerFragment.newInstance((year, month, day) -> {
                        //call whatever method you need with these variables for start date :)

                        //note to self: java converts if for you!!!
                        String startDate = year + "/" + month + "/" + day;
                        startDateTextDisplay.setText(startDate);
                    });
            //make sure it updates properly (will need to test)
            startDatePicker.show(getChildFragmentManager(), "startDatePicker");

        });

        //calls a new date picker fragment when the button is clicked
        endDateButton.setOnClickListener(v -> {
            DatePickerFragment endDatePicker =
                    DatePickerFragment.newInstance((year, month, day) -> {

                        //add method call for firebase upload
                        String endDate = year + "/" + month + "/" + day;
                        endDateTextDisplay.setText(endDate);
                    });
            endDatePicker.show(getChildFragmentManager(), "endDatePicker");
        });

        // get username of current user
        DatabaseReference usersRef = FirebaseManager.getInstance()
                .getDatabaseReference().child("users")
                .child(uid).child("username");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUsername = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // create arraylist of all destinations the current user logged
        List<String> destinationItems = new ArrayList<>();
        destinationItems.add("Destinations");
        DatabaseReference destinationRef = FirebaseDatabase.getInstance().getReference()
                .child("travelLogs").child(uid).child("destinations");
        DatabaseReference userTravelRef = FirebaseDatabase.getInstance().getReference()
                .child("travelLogs").child(uid);

        destinationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through all children of the branch
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Object value = childSnapshot.child("location").getValue();
                    destinationItems.add((String) value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                System.err.println("Error: " + databaseError.getMessage());
            }
        });

        // Set up the ArrayAdapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, destinationItems);
        tripDestination.setAdapter(arrayAdapter);

        final String[] selectedItem = new String[1];
        // Handle item selection
        tripDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedItem[0] = parent.getItemAtPosition(position).toString();

                    destinationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Loop through all children of the branch
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                Object value = childSnapshot.child("location").getValue();
                                if (value.equals(selectedItem[0])) {
                                    final Object[] startDate = {childSnapshot.child("startDate").getValue()};
                                    final Object[] endDate = {childSnapshot.child("endDate").getValue()};

                                    String[] startSplit = ((String) startDate[0]).split("-");
                                    String newStartDate = startSplit[0] + "/" + startSplit[1] + "/" + startSplit[2]; // Hardcoded date
                                    startDateTextDisplay.setText(newStartDate); // Update the TextView

                                    String[] endSplit = ((String) endDate[0]).split("-");
                                    String newEndDate = endSplit[0] + "/" + endSplit[1] + "/" + endSplit[2]; // Hardcoded date
                                    endDateTextDisplay.setText(newEndDate); // Update the TextView

                                    foundDuration[0] = ReservationUtils.calculateDaysBetween((String) startDate[0],
                                            (String) endDate[0]);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle possible errors
                            System.err.println("Error: " + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        // create arraylist of all dining reservations the current user logged
        List<String> diningItems = new ArrayList<>();
        DatabaseReference diningRef = FirebaseDatabase.getInstance().getReference()
                .child("diningReservations").child(uid);

        diningRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through all children of the branch
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Object value = childSnapshot.child("name").getValue();
                    diningItems.add(String.valueOf(value));
                }
                String formattedDiningItems = String.join(", ", diningItems);
                diningReservations.setText(formattedDiningItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                System.err.println("Error: " + databaseError.getMessage());
            }
        });


        // create arraylist of all accommodations reservations the current user logged
        List<String> accommodationItems = new ArrayList<>();
        DatabaseReference accommodationRef = FirebaseDatabase.getInstance().getReference()
                .child("accommodation").child(uid).child("accommodations");

        accommodationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through all children of the branch
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Object value = childSnapshot.child("locationName").getValue();
                    accommodationItems.add((String) value);
                }
                String formattedAccommodationItems = String.join(", ", accommodationItems);
                accommodationReservations.setText(formattedAccommodationItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                System.err.println("Error: " + databaseError.getMessage());
            }
        });

        submitPostButton.setOnClickListener(v -> {
            //get review info
            String duration = String.valueOf(foundDuration[0]);
            String destination = selectedItem[0];
            String accommodations = accommodationReservations.getText().toString().trim();
            String dining = diningReservations.getText().toString().trim();
            String transportation = tripTransportation.getText().toString().trim();
            String notes = tripNotes.getText().toString().trim();

            if (destination.isEmpty() || accommodations.isEmpty()
                    || dining.isEmpty() || transportation.isEmpty() || notes.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            //make new community entry
            CommunityEntry post = new CommunityEntry(duration,
                    dining, accommodations, destination, transportation, notes);

            //Please check firefox implementation
            AddToCommunityDatabase add = new AddToCommunityDatabase();
            add.interactWithCommunityDatabase(currentUser, tripRef, post,
                    communityEntries, fragContext, adapter);

            accommodationReservations.setText("");
            diningReservations.setText("");
            tripTransportation.setText("");
            tripNotes.setText("");

            adapter.notifyDataSetChanged();
            communityCard.setVisibility(View.GONE);
        });
        load.interactWithCommunityDatabase(currentUser, tripRef, null,
                communityEntries, fragContext, adapter);
        adapter.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }
}