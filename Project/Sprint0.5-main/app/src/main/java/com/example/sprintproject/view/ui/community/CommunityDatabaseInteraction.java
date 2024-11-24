package com.example.sprintproject.view.ui.community;

import android.content.Context;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface CommunityDatabaseInteraction {
    void interactWithCommunityDatabase(FirebaseUser user, DatabaseReference database,
                                       CommunityEntry post,
                                       ArrayList<CommunityEntry> communityEntries, Context context,
                                       CommunityRecycleViewAdapter adapter);
}

