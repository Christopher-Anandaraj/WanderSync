package com.example.sprintproject.view.ui.dining;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoadFromDatabase implements DatabaseInteraction {
    @Override
    public void interactWithDatabase(FirebaseUser user, DatabaseReference database, DiningEntry reservation, ArrayList<DiningEntry> diningEntries, Context context) {
        //Allyson ________________________________
        //Clear arraylist beforehand
        diningEntries.clear();
        //End of Allyson _________________________

        if (user != null) {
            final String[] uid = {user.getUid()};
            final String[] currentUsername = new String[1];

            DatabaseReference usersRef = FirebaseManager.getInstance()
                    .getDatabaseReference().child("users")
                    .child(uid[0]).child("username");
            DatabaseReference travelLogRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("travelLogs");

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUsername[0] = snapshot.getValue(String.class);
                    if (currentUsername[0] == null) {
                        Toast.makeText(context, "Failed to get username.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Access denied.", Toast.LENGTH_SHORT).show();
                }
            });
            travelLogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Loop through each user's travel log
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();

                        // Check if the current user is the owner or a contributor by username
                        if (userId.equals(uid[0]) || userSnapshot.child("contributors")
                                .hasChild(currentUsername[0])) {
                            uid[0] = userSnapshot.getKey();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Failed to load travel logs.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            DatabaseReference userReservations = database.child(uid[0]);

            userReservations.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

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
                    Toast.makeText(context, "Failed to load dining reservations.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
