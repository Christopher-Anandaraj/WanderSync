package com.example.sprintproject.view.ui.dining;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface DatabaseInteraction {
    void interactWithDatabase(FirebaseUser user, DatabaseReference database, DiningEntry reservation, ArrayList<DiningEntry> diningEntries, Context context, DiningRecycleViewAdapter adapter);
}
