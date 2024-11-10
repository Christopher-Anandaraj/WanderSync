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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.FragmentAccommodationBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.ui.ReservationUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        ListView reservationList = binding.accommodationList;
        EditText locationName = binding.accommodationLocationName;
        EditText checkInText = binding.accommodationCheckInDate;
        EditText checkOutText = binding.accommodationCheckOutDate;
        Spinner roomNumber = binding.roomsNumber;
        EditText website = binding.accommodationWebsiteName;
        Spinner roomType = binding.roomsType;


        roomNumberSpinner(roomNumber);
        roomTypeSpinner(roomType);

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
                    roomNumber.setSelection(0);
                    website.setText("");
                    roomType.setSelection(0);
                }
            }
        );

        buttonConfirmAccommodation.setOnClickListener(v -> {
            String location = locationName.getText().toString().trim();
            String checkIn = checkInText.getText().toString().trim();
            String checkOut = checkOutText.getText().toString().trim();
            String room = roomNumber.getSelectedItem().toString().trim();
            String webAddress = website.getText().toString().trim();
            String type = roomType.getSelectedItem().toString().trim();

            if (location.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty() || room.isEmpty()
                    || webAddress.isEmpty() || type.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!ReservationUtils.isValidDate(checkIn)
                    || !ReservationUtils.isValidDate(checkOut)
                    || !ReservationUtils.isStartDateBeforeEndDate(checkIn, checkOut)) {
                Toast.makeText(getContext(), "Please enter valid dates.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            createAccommodation(location, checkIn, checkOut, room, webAddress, type);
            accommodationAdd.setVisibility(View.GONE);
            locationName.setText("");
            checkInText.setText("");
            checkOutText.setText("");
            roomNumber.setSelection(0);
            website.setText("");
            roomType.setSelection(0);
        });

        loadAccommodation(reservationList);
        return root;
    }

    private void roomNumberSpinner(Spinner spinner) {
        ArrayList<String> numbersList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            numbersList.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, numbersList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private List<String> getContributors() {
        return new ArrayList<>(); //
    }

    private void roomTypeSpinner(Spinner spinner) {
        ArrayList<String> roomType = new ArrayList<>();
        roomType.add("Standard Room");
        roomType.add("Deluxe Room");
        roomType.add("Suite");
        roomType.add("Single Room");
        roomType.add("Double Room");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, roomType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void createAccommodation(String locationName, String checkInDate, String checkOutDate,
                                     String numberOfRooms, String website, String roomType) {
        FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();

        /* Chris, you'll have to edit the area below and add the database reference */
        if (user != null) {
            String uid = user.getUid(); // Get the user ID
            DatabaseReference accommodationRef =
                    FirebaseManager.getInstance().getDatabaseReference()
                    .child("accommodation").child(uid); // Reference to the user's travel log

            // Generate a unique ID for the destination
            String accommodationId = accommodationRef.child("accommodations").push().getKey();

            // Create a map for the destination data
            Map<String, Object> accommodationMap = new HashMap<>();
            accommodationMap.put("locationName", locationName);
            accommodationMap.put("checkInDate", checkInDate);
            accommodationMap.put("checkOutDate", checkOutDate);
            accommodationMap.put("numberOfRooms", numberOfRooms);
            accommodationMap.put("website", website);
            accommodationMap.put("roomType", roomType);

            // Include contributor IDs if applicable
            List<String> contributorIds = getContributors(); // This fetches contributor IDs
            if (!contributorIds.isEmpty()) {
                accommodationMap.put("contributors", contributorIds);
            }

            if (accommodationId != null) {
                // Add accommodation to the user's travel log
                accommodationRef.child("accommodations")
                        .child(accommodationId).setValue(accommodationMap)
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
                Toast.makeText(getContext(), "No user is logged in.",
                        Toast.LENGTH_SHORT).show();
            }
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

                DatabaseReference travelLogRef = FirebaseManager
                        .getInstance().getDatabaseReference()
                        .child("travelLogs");

                travelLogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean hasAccess = false;
                        String ownerID = null;
                        // Loop through each user's travel log
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userId = userSnapshot.getKey();

                            // Check if the current user is the owner or a contributor by username
                            if (userId.equals(currentUserId)
                                    || userSnapshot.child("contributors")
                                    .hasChild(currentUsername)) {
                                ownerID = userSnapshot.getKey();
                                hasAccess = true;
                                break;
                            }
                        }

                        if (hasAccess && ownerID != null) {
                            DatabaseReference accommodationRef = FirebaseManager
                                    .getInstance().getDatabaseReference()
                                    .child("accommodation").child(ownerID)
                                    .child("accommodations");
                            List<String> accommodationReservation = new ArrayList<>();

                            accommodationRef.addListenerForSingleValueEvent(new
                                                                            ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot
                                                                 accommodationSnapshot) {
                                    accommodationReservation.clear();

                                    for (DataSnapshot accommodationData : accommodationSnapshot
                                            .getChildren()) {

                                        String location = accommodationData.child("locationName")
                                                .getValue(String.class);
                                        String checkIn = accommodationData.child("checkInDate")
                                                .getValue(String.class);
                                        String checkOut = accommodationData.child("checkOutDate")
                                                .getValue(String.class);
                                        String roomAmount = accommodationData.child("numberOfRooms")
                                                .getValue(String.class);
                                        String webAddress = accommodationData.child("website")
                                                .getValue(String.class);
                                        String typeRoom = accommodationData.child("roomType")
                                                .getValue(String.class);

                                        String checkInText = "Check-in: ";
                                        String checkOutText = "Check-Out: ";
                                        String numberRoomsText = "Number of rooms: ";

                                        //This is for checking if the checkout date
                                        // is past the current date
                                        boolean isExpired = false;
                                        SimpleDateFormat dateFormat =
                                                new SimpleDateFormat("yyyy-MM-dd"); // or
                                        // the format of your dates
                                        try {
                                            Date checkOutDate = dateFormat.parse(checkOut);
                                            Calendar today = Calendar.getInstance();
                                            today.set(Calendar.HOUR_OF_DAY, 0);
                                            today.set(Calendar.MINUTE, 0);
                                            today.set(Calendar.SECOND, 0);
                                            today.set(Calendar.MILLISECOND, 0);

                                            if (checkOutDate.before(today.getTime())) {
                                                isExpired = true;
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        String formattedAccommodation = String.format(
                                                "%s\n%s %s %s %s\n%s %s\n%s\n%s%s",
                                                location,
                                                checkInText, checkIn,
                                                checkOutText, checkOut,
                                                numberRoomsText, roomAmount,
                                                webAddress, typeRoom,
                                                isExpired ? "\nEXPIRED" : ""
                                        );
                                        accommodationReservation.add(formattedAccommodation);
                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                            R.layout.reservation_font, accommodationReservation);
                                    accommodationList.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getContext(), "Failed to load accommodation.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Access denied.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Failed to load travel logs.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load user data.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}