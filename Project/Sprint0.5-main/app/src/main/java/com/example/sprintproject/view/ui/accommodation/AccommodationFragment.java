package com.example.sprintproject.view.ui.accommodation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentAccommodationBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccommodationFragment extends Fragment {

    private FragmentAccommodationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccommodationViewModel accommodationViewModel =
                new ViewModelProvider(this).get(AccommodationViewModel.class);

        binding = FragmentAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAccommodation;
        accommodationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ImageButton buttonAddAccommodation = binding.buttonAddAccommodation;
        Button buttonConfirmAccommodation = binding.buttonConfirmAccommodation;
        Button buttonCancelAccommodation = binding.buttonCancelAccommodation;
        TableLayout accommodationAdd = binding.accommodationAdd;
        EditText locationName = binding.accommodationLocationName;
        EditText checkInText = binding.accommodationCheckInDate;
        EditText checkOutText = binding.accommodationCheckOutDate;
        EditText roomNumber = binding.roomsNumber;
        EditText website = binding.accommodationWebsiteName;
        EditText roomType = binding.roomsType;

        buttonAddAccommodation.setOnClickListener(view -> {
            if (accommodationAdd.getVisibility() == View.VISIBLE) {
                accommodationAdd.setVisibility(View.GONE);
            } else {
                accommodationAdd.setVisibility(View.VISIBLE);
            }
        });

        buttonCancelAccommodation.setOnClickListener(view -> {
                    if (accommodationAdd.getVisibility() == View.VISIBLE) {
                        accommodationAdd.setVisibility(View.GONE);
                        locationName.setText("");
                        checkInText.setText("");
                        checkOutText.setText("");
                        roomNumber.setText("");
                        website.setText("");
                        roomType.setText("");
                    }
                }
        );

        buttonConfirmAccommodation.setOnClickListener(v -> {
            String location = locationName.getText().toString().trim();
            int checkIn = Integer.parseInt(checkInText.getText().toString().trim());
            int checkOut = Integer.parseInt(checkOutText.getText().toString().trim());
            int room = Integer.parseInt(roomNumber.getText().toString().trim());
            String webAddress = website.getText().toString().trim();
            String type = roomType.getText().toString().trim();

            if (location.isEmpty() || checkIn == 0 || checkOut == 0 || room == 0 || webAddress.isEmpty() || type.isEmpty())  {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            /* Need to create checks here
            if (!DestinationUtils.isValidDate(startDate)
                    || !DestinationUtils.isValidDate(endDate)
                    || !DestinationUtils.isStartDateBeforeEndDate(startDate, endDate)) {
                Toast.makeText(getContext(), "Please enter valid dates.",
                        Toast.LENGTH_SHORT).show();
                return;
            }*/

            createAccommodation(location, checkIn, checkOut, room, webAddress, type);
            locationName.setText("");
            checkInText.setText("");
            checkOutText.setText("");
            roomNumber.setText("");
            website.setText("");
            roomType.setText("");
        });

        return root;
    }

    private void createAccommodation(String locationName, int checkInDate, int checkOutDate, int numberOfRooms, String website, String roomType) {
        FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();

        /* Chris, you'll have to edit the area below and add the database reference */
        if (user != null) {
            String uid = user.getUid(); // Get the user ID
            DatabaseReference accommodationRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("accommodation").child(uid); // Reference to the user's travel log

            // Generate a unique ID for the destination
            String destinationId = accommodationRef.child("accommodation").push().getKey();

            // Create a map for the destination data
            Map<String, Object> accommodationMap = new HashMap<>();
            accommodationMap.put("locationName", locationName);
            accommodationMap.put("checkInDate", checkInDate);
            accommodationMap.put("checkOutDate", checkOutDate);
            accommodationMap.put("numberOfRooms", numberOfRooms);
            accommodationMap.put("website", website);
            accommodationMap.put("roomType", roomType);

            // Include contributor IDs if applicable

            if (destinationId != null) {
                // Add accommodation to the user's travel log
                accommodationRef.child("accommodation").child(destinationId).setValue(accommodationMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Reservation added to accommodation!",
                                        Toast.LENGTH_SHORT).show();
                                ListView reservationList = binding.accommodationList;
                                loadAccommodation(reservationList);
                            } else {
                                Toast.makeText(getContext(), "Failed to add accommodation.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Failed to generate accommodation ID.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No user is logged in.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAccommodation(ListView accommodationList) {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = currentUser.getUid();
        DatabaseReference usersRef = FirebaseManager.getInstance()
                .getDatabaseReference().child("users")
                .child(currentUserId).child("username");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentUsername = snapshot.getValue(String.class);
                if (currentUsername == null) {
                    Toast.makeText(getContext(), "Failed to get username.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                /*Chris, You'll have to edit the database here */
                DatabaseReference accommodationRef = FirebaseManager
                        .getInstance().getDatabaseReference()
                        .child("accommodation");

                List<String> accommodationReservation = new ArrayList<>();

                accommodationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        accommodationReservation.clear();

                        // Loop through each user's travel log
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userId = userSnapshot.getKey();

                            // Check if the current user is the owner or a contributor by username
                            if (userId.equals(currentUserId)
                                    || userSnapshot.child("contributors")
                                    .hasChild(currentUsername)) {
                                // Load all destinations for this user
                                for (DataSnapshot accommodationSnapshot : userSnapshot
                                        .child("accommodation")
                                        .getChildren()) {
                                    String location = accommodationSnapshot.child("locationName")
                                            .getValue(String.class);
                                    String checkIn = accommodationSnapshot.child("checkInDate")
                                            .getValue(String.class);
                                    String checkOut = accommodationSnapshot.child("checkOutDate")
                                            .getValue(String.class);
                                    String roomAmount = accommodationSnapshot.child("numberOfRooms")
                                            .getValue(String.class);
                                    String webAddress = accommodationSnapshot.child("website")
                                            .getValue(String.class);
                                    String typeRoom = accommodationSnapshot.child("roomType")
                                            .getValue(String.class);

                                    String formattedAccommodation = String.format(
                                            "%s\nCheck-in: %s Check-out: %s\nNumber of Rooms: %s\n%s\n%s",
                                            location, checkIn, checkOut, roomAmount, webAddress, typeRoom);
                                    accommodationReservation.add(formattedAccommodation);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                        android.R.layout.simple_list_item_1, accommodationReservation);
                                accommodationList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Failed to load accommodation.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(
                    @NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load accommodation.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}