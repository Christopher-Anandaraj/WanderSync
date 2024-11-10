package com.example.sprintproject.view.ui.dining;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.databinding.FragmentDiningBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class DiningFragment extends Fragment {

    private FragmentDiningBinding binding;

    //holds all entries for dining (Allyson)
    ArrayList<DiningEntry> diningEntries = new ArrayList<>();

    FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiningViewModel diningViewModel =
                new ViewModelProvider(this).get(DiningViewModel.class);
        loadReservation(currentUser);
        binding = FragmentDiningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // sophie stuff
        EditText editText_reservationName = binding.diningNameInput;
        Button button_reservationSubmit = binding.addReservationButton;
        EditText editText_reservationLocation = binding.diningLocationInput;
        EditText editText_reservationWebsite = binding.diningWebsiteInput;
        EditText editText_reservationTime = binding.diningTimeInput;
        // end sophie stuff

        //Allyson Implementation ------------------------------------------------------------------------

        //recycle view
        RecyclerView recyclerView = binding.diningList;

        //setup dining list

        //setup recycleview functionality (sending dining entries to adapter)+ call constructor
        DiningRecycleViewAdapter adapter = new DiningRecycleViewAdapter(this.getContext(), diningEntries);
        recyclerView.setAdapter(adapter);

        //layout is linear layout for the boxes
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Buttons for Dining Screen
        ImageButton open_reservation_cardview_button = binding.openReservationCardviewButton;

        //cardviews
        TableLayout reservationAddDining= binding.diningReservation;

        //Opens and closes the Add reservation viewcard
        open_reservation_cardview_button.setOnClickListener(view -> {
            if (reservationAddDining.getVisibility() == View.GONE) {
                reservationAddDining.setVisibility(View.VISIBLE);
            } else {
                reservationAddDining.setVisibility(View.GONE);
            }
        });

        //End of Allyson Implementation ------------------------------------------------------------------

        // sophie stuff
        //add reservation button
        button_reservationSubmit.setOnClickListener(v -> {
            String resvName = editText_reservationName.getText().toString().trim();
            String resvLocation = editText_reservationLocation.getText().toString().trim();
            String resvTime = editText_reservationTime.getText().toString().trim();
            String resvWebsite = editText_reservationWebsite.getText().toString().trim();

            if (resvLocation.isEmpty() || resvTime.isEmpty() || resvWebsite.isEmpty() || resvName.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            //adds to database
            createReservation(resvName, resvLocation, resvTime, resvWebsite);
            reservationAddDining.setVisibility(View.GONE);
            editText_reservationName.setText("");
            editText_reservationLocation.setText("");
            editText_reservationTime.setText("");
            editText_reservationWebsite.setText("");

            //Ally
            // Update the list and notify the adapter
            adapter.notifyDataSetChanged(); // Notify adapter of data change
        });
        // end of sophie stuff

        //Allyson Implementaion -----------------------------------------


        final TextView textView = binding.textDining;
        diningViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    // sophie stuff
    private void createReservation(String restaurantName, String location, String time, String website) {
        if (currentUser != null) {
            String uid = currentUser.getUid();

            DatabaseReference reservationRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("diningReservations").child(uid);

            String reservationId = reservationRef.child("reservations").push().getKey();

            Map<String, String> reservationMap = new HashMap<>();
            reservationMap.put("name", restaurantName);
            reservationMap.put("location", location);
            reservationMap.put("website", website);
            reservationMap.put("reservation_time", time);

            if (reservationId != null) {
                reservationRef.child(reservationId).setValue(reservationMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Restaurant reservation added to log!",
                                        Toast.LENGTH_SHORT).show();
//                                ListView listView = binding.listViewTravelLogs;
//                                loadTravelLogs(listView);
                            } else {
                                Toast.makeText(getContext(), "Failed to add reservation.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Failed to generate reservation ID.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        //update list (Ally)
        DiningEntry newEntry = new DiningEntry(restaurantName,location,time,website);
        diningEntries.add(newEntry);

    }

    private void loadReservation(FirebaseUser currentUser) {
        //Allyson ________________________________
        //Clear arraylist beforehand
        diningEntries.clear();
        //End of Allyson _________________________

        if (currentUser != null) {
            String uid = currentUser.getUid();

            DatabaseReference reservationRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("diningReservations").child(uid);

            reservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    // Loop through each child in the snapshot
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        // Get the key and value of each child
                        String loadedName = childSnapshot.child("name").getValue().toString();
                        String loadedLoc = childSnapshot.child("location").getValue().toString();
                        String loadedTime = childSnapshot.child("reservation_time").getValue().toString();
                        String loadedWebsite = childSnapshot.child("website").getValue().toString();

                        // put the reservation's name/loc/time/website into arrayList of entries

                        //Allyson Implementation --------------------------------------
                        diningEntries.add(new DiningEntry(loadedName, loadedLoc, loadedTime, loadedWebsite));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Failed to load travel logs.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
