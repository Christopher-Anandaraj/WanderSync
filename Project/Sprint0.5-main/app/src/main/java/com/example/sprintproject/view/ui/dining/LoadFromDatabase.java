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

        if (user == null) {
            Toast.makeText(context, "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = user.getUid();
        DatabaseReference usersRef = FirebaseManager.getInstance()
                .getDatabaseReference().child("users")
                .child(currentUserId).child("username");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentUsername = snapshot.getValue(String.class);
                if (currentUsername == null) {
                    Toast.makeText(context, "Failed to get username.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                /*Chris, You'll have to edit the database here */
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
                            if (userId.equals(currentUserId) || userSnapshot.child("contributors").hasChild(currentUsername)) {
                                ownerID = userSnapshot.getKey();
                                hasAccess = true;
                                break;
                            }
                        }

                        if (hasAccess && ownerID != null) {
                            DatabaseReference diningRef = database.child(ownerID);

                            diningRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    diningEntries.clear();

                                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                                        String loadedName = childSnapshot.child("name").getValue().toString();
                                        String loadedLoc = childSnapshot.child("location").getValue().toString();
                                        String loadedTime = childSnapshot.child("reservation_time").getValue().toString();
                                        String loadedWebsite = childSnapshot.child("website").getValue().toString();

                                        diningEntries.add(new DiningEntry(loadedName, loadedLoc, loadedTime, loadedWebsite));
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(context, "Failed to load accommodation.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(context, "Access denied.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(context, "Failed to load travel logs.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Failed to load user data.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}


