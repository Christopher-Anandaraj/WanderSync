package com.example.sprintproject.view.ui.community;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddToCommunityDatabase implements CommunityDatabaseInteraction {
    @Override
    public void interactWithCommunityDatabase(FirebaseUser user, DatabaseReference database, CommunityEntry post, ArrayList<CommunityEntry> communityEntries, Context context) {
        if (user != null) {
            String uid = user.getUid();

            DatabaseReference userReservations = database.child(uid);

            String postId = userReservations.child("community").push().getKey();

            Map<String, String> communityMap = new HashMap<>();
            communityMap.put("Start date", post.getStartDate());
            communityMap.put("End Date", post.getEndDate());
            communityMap.put("Dining review", post.getDiningReview());
            communityMap.put("Accommodation review", post.getAccomodationsReview());
            communityMap.put("Destination", post.getDestination());
            communityMap.put("Trip notes", post.getTripNotes());

            if (postId != null) {
                userReservations.child(postId).setValue(communityMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Community post added to log!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to add post.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(context, "Failed to generate post ID.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        //update list
        communityEntries.add(post);
    }
}
