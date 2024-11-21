package com.example.sprintproject.view.ui.community;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentCommunityBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.ui.destination.DestinationUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
    String uid = currentUser.getUid();

    private FragmentCommunityBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CommunityViewModel communityViewModel =
                new ViewModelProvider(this).get(CommunityViewModel.class);

        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText tripDuration = binding.communityPostDuration;
        Spinner tripDestination = binding.communityPostDestination;
        EditText accommodationReservations = binding.communityPostAccommodations;
        EditText diningReservations = binding.communityPostDining;
        EditText transportation = binding.communityPostTransportation;
        EditText tripNotes = binding.communityPostNotes;
        Button submitPostButton = binding.addCommunityPostButton;
        Button openCreatePostButton = binding.createCommunityPostButton;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, destinationItems);
        tripDestination.setAdapter(adapter);

        // Handle item selection
        tripDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedItem = parent.getItemAtPosition(position).toString();

                    destinationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Loop through all children of the branch
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                Object value = childSnapshot.child("location").getValue();
                                if (value.equals(selectedItem)) {
                                    Object startDate = childSnapshot.child("startDate").getValue();
                                    Object endDate = childSnapshot.child("endDate").getValue();

                                    long duration = DestinationUtils.calculateDaysBetween((String) startDate,
                                            (String) endDate);
                                    tripDuration.setText(String.valueOf(duration));
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

        String formattedAccommodationsList = formatListToString(accommodationItems);
        accommodationReservations.setText(formattedAccommodationsList);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String formatListToString(List<String> list) {
        // Join the list items with newlines
        return String.join("\n", list);
    }
}