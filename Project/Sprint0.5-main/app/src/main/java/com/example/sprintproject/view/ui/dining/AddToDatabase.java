package com.example.sprintproject.view.ui.dining;

import static androidx.test.InstrumentationRegistry.getContext;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddToDatabase implements DatabaseInteraction {
    @Override
    public void interactWithDatabase(FirebaseUser user, DatabaseReference database, DiningEntry reservation, ArrayList<DiningEntry> diningEntries, Context context, RecyclerView.Adapter adapter) {
        if (user != null) {
            String uid = user.getUid();

            DatabaseReference userReservations = database.child(uid);

            String reservationId = userReservations.child("reservations").push().getKey();

            Map<String, String> reservationMap = new HashMap<>();
            reservationMap.put("name", reservation.getRestaurant());
            reservationMap.put("location", reservation.getLocation());
            reservationMap.put("website", reservation.getLink());
            reservationMap.put("reservation_time", reservation.getTime());

            if (reservationId != null) {
                userReservations.child(reservationId).setValue(reservationMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Restaurant reservation added to log!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to add reservation.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(context, "Failed to generate reservation ID.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        //update list
        diningEntries.add(reservation);
    }

}
