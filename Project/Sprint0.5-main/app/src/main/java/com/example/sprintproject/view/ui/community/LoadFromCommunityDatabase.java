package com.example.sprintproject.view.ui.community;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.ui.community.CommunityDatabaseInteraction;
import com.example.sprintproject.view.ui.community.CommunityEntry;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoadFromCommunityDatabase implements CommunityDatabaseInteraction {
    @Override
    public void interactWithCommunityDatabase(FirebaseUser user, DatabaseReference database, CommunityEntry
            post, ArrayList<CommunityEntry> communityEntries, Context context, CommunityRecycleViewAdapter adapter) {
        //Allyson ________________________________
        //Clear arraylist beforehand
        communityEntries.clear();
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
                            if (userId.equals(currentUserId)
                                    || userSnapshot
                                    .child("contributors").hasChild(currentUsername)) {
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
                                    communityEntries.clear();

                                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                        String loadedDuration =
                                                childSnapshot
                                                        .child("Duration").getValue()
                                                        .toString();
                                        String loadedDiningReview =
                                                childSnapshot
                                                        .child("Dining reservations").getValue()
                                                        .toString();
                                        String loadedAccommodationReview =
                                                childSnapshot
                                                        .child("Accommodation reservations").getValue().toString();
                                        String loadedDestination =
                                                childSnapshot
                                                        .child("Destination").getValue().toString();
                                        String loadedTransportation =
                                                childSnapshot
                                                        .child("Transportation").getValue().toString();
                                        String loadedTripNotes =
                                                childSnapshot
                                                        .child("Trip notes").getValue().toString();

                                        communityEntries.
                                                add(new CommunityEntry(loadedDuration,
                                                        loadedDiningReview, loadedAccommodationReview,
                                                        loadedDestination, loadedTransportation,
                                                        loadedTripNotes));
                                    }
                                    adapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(context, "Failed to load post.",
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