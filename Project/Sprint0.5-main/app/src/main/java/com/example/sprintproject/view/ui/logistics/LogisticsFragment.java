package com.example.sprintproject.view.ui.logistics;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.FragmentLogisticsBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.ui.destination.DestinationFragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

public class LogisticsFragment extends Fragment {

    private FragmentLogisticsBinding binding;
    public PieChart pieChart;
    CardView cardView;
    private List<String> contributorsList = new ArrayList<>();

    private List<String> notesList = new ArrayList<>();
    private ArrayAdapter<String> notesAdapter;
    private ArrayAdapter<String> contributorsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogisticsViewModel logisticsViewModel =
                new ViewModelProvider(this).get(LogisticsViewModel.class);

        binding = FragmentLogisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardView = root.findViewById(R.id.cardViewGraph);
        cardView.setVisibility(View.GONE);
        Button button_dataVis = binding.buttonDataVis;
        button_dataVis.setOnClickListener(view -> {
            if (cardView.getVisibility() == View.VISIBLE)
                cardView.setVisibility(View.GONE);
            else
                cardView.setVisibility(View.VISIBLE);});

        pieChart = root.findViewById(R.id.piechart);
        setData();

        ImageButton addContributor = binding.buttonAddContributors;
        ImageButton notes = binding.buttonNotes;
        TableLayout tableLayoutAddContributor = binding.tableLayoutAddContributor;
        EditText contributorUsername = binding.editTextContributorName;
        Button buttonAddContributor = binding.buttonAddContributor;
        ListView contributorsListView = binding.contributorsList; // Assuming you have this binding
        contributorsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, contributorsList);
        contributorsListView.setAdapter(contributorsAdapter);

        TableLayout tableLayoutNotes = binding.tableLayoutNotes;
        ListView listViewNotes = binding.listViewNotes;
        listViewNotes.setVisibility(View.VISIBLE);
        notesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, notesList);
        notesAdapter.notifyDataSetChanged();
        listViewNotes.setAdapter(notesAdapter);
        EditText editTextNewNote = binding.editTextNewNote;
        Button buttonAddNote = binding.buttonAddNote;

        addContributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show addContributor section and hide notes section if it's visible
                if (tableLayoutNotes.getVisibility() == View.VISIBLE) {
                    tableLayoutNotes.setVisibility(View.GONE);
                }
                // Toggle visibility of contributor input section
                if (tableLayoutAddContributor.getVisibility() == View.VISIBLE) {
                    tableLayoutAddContributor.setVisibility(View.GONE);
                } else {
                    tableLayoutAddContributor.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonAddContributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = contributorUsername.getText().toString().trim();

                if (!username.isEmpty()) {
                    inviteContributor(username);
                } else {
                    Toast.makeText(getContext(), "Please enter a username.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<String> notesAdapter;
        notesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(notesAdapter);

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableLayoutAddContributor.getVisibility() == View.VISIBLE) {
                    tableLayoutAddContributor.setVisibility(View.GONE);
                }
                if (tableLayoutNotes.getVisibility() == View.VISIBLE) {
                    tableLayoutNotes.setVisibility(View.GONE);
                } else {
                    tableLayoutNotes.setVisibility(View.VISIBLE);
                    loadNotes();
                }
            }
        });

        buttonAddNote.setOnClickListener(view -> addNote());
        loadNotes();
        loadContributors();
        return root;
    }

    private void addNote() {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText editTextNewNote = binding.editTextNewNote;

        String newNote = editTextNewNote.getText().toString().trim();
        if (!newNote.isEmpty()) {
            String currentUserId = currentUser.getUid();
            DatabaseReference notesRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("travelLogs").child(currentUserId).child("notes").push();

            notesRef.setValue(newNote).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Note added", Toast.LENGTH_SHORT).show();
                    editTextNewNote.setText("");
                    loadNotes();
                    notesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to add note", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Please enter a note", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNotes() {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = currentUser.getUid();
        DatabaseReference usersRef = FirebaseManager.getInstance().getDatabaseReference().child("users").child(currentUserId).child("username");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentUsername = snapshot.getValue(String.class);
                if (currentUsername == null) {
                    Toast.makeText(getContext(), "Failed to get username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference travelLogRef = FirebaseManager.getInstance().getDatabaseReference().child("travelLogs");
                travelLogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        notesList.clear(); // Clear the current notes list

                        // Loop through each user's travel log
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userId = userSnapshot.getKey();

                            // Check if the current user is the owner or a contributor
                            if (userId.equals(currentUserId) || userSnapshot.child("contributors").hasChild(currentUsername)) {
                                // Load notes for this user
                                for (DataSnapshot noteSnapshot : userSnapshot.child("notes").getChildren()) {
                                    String note = noteSnapshot.getValue(String.class);
                                    Log.d("Note retrieved", "This is what is retrieved: " + note);
                                    if (note != null) {
                                        notesList.add(note);
                                    }
                                }
                            }
                        }
                        // Notify the existing adapter of the changes
                        notesAdapter.notifyDataSetChanged(); // This updates the ListView with new data
                        Log.d("LoadNotes", "Notes retrieved: " + notesList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Failed to load notes.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to get current user's username.", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void inviteContributor(String invitedUsername) {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the current user's ID
            DatabaseReference usersRef = FirebaseManager.getInstance().getDatabaseReference().child("users").child(userId);

            // Get the current user's username
            usersRef.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String currentUsername = dataSnapshot.getValue(String.class);

                    if (currentUsername != null) {
                        // Check if the invited username exists in the "users" database
                        DatabaseReference allUsersRef = FirebaseManager.getInstance().getDatabaseReference().child("users");
                        allUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean userExists = false;

                                // Iterate through all users to check for the invited username
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    String savedUsername = userSnapshot.child("username").getValue(String.class);
                                    if (savedUsername != null && savedUsername.equals(invitedUsername)) {
                                        userExists = true;
                                        break;
                                    }
                                }

                                if (userExists) {
                                    // Username exists, proceed to invite the contributor
                                    DatabaseReference tripRef = FirebaseManager.getInstance().getDatabaseReference()
                                            .child("travelLogs").child(userId).child("contributors");

                                    // Check if the current user is already a contributor
                                    tripRef.child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (!snapshot.exists()) {
                                                // Current user is not a contributor yet, so add them
                                                tripRef.child(currentUsername).setValue(true)
                                                        .addOnCompleteListener(task -> {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getContext(), "You are now a contributor.", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getContext(), "Failed to add current user as contributor.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }

                                            // Add the invited user to contributors
                                            tripRef.child(invitedUsername).setValue(true)
                                                    .addOnCompleteListener(inviteTask -> {
                                                        if (inviteTask.isSuccessful()) {
                                                            contributorsList.add(invitedUsername); // Add the invited contributor to the list
                                                            contributorsAdapter.notifyDataSetChanged();
                                                            loadContributors(); // Notify the adapter about the change
                                                            Toast.makeText(getContext(), "Contributor invited!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getContext(), "Failed to invite contributor.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(getContext(), "Error checking if current user is a contributor: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(), "Invited username does not exist.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "Error checking user existence: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Current user username not found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Error retrieving current user username: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "No user is logged in.", Toast.LENGTH_SHORT).show();
        }
    }



    private void loadContributors() {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "No user is logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = currentUser.getUid();
        DatabaseReference usersRef = FirebaseManager.getInstance().getDatabaseReference().child("users").child(currentUserId).child("username");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentUsername = snapshot.getValue(String.class);
                if (currentUsername == null) {
                    Toast.makeText(getContext(), "Failed to get username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference travelLogRef = FirebaseManager.getInstance().getDatabaseReference().child("travelLogs");
                travelLogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        contributorsList.clear();

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String ownerId = userSnapshot.getKey();

                            // Check if current user is the trip owner or a contributor
                            if (ownerId.equals(currentUserId) || userSnapshot.child("contributors").hasChild(currentUsername)) {
                                // Load all contributors under this owner's travel log
                                for (DataSnapshot contributorSnapshot : userSnapshot.child("contributors").getChildren()) {
                                    String contributorUsername = contributorSnapshot.getKey();
                                    if (contributorUsername != null) {
                                        contributorsList.add(contributorUsername);
                                    }
                                }
                                break; // Exit loop once contributors are loaded
                            }
                        }

                        contributorsAdapter.notifyDataSetChanged(); // Notify the adapter about the changes
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Error loading contributors: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to get current user's username.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setData() {
        pieChart.addPieSlice(
                new PieModel(
                        "Allocated days",
                        DestinationFragment.allocatedDays - DestinationFragment.plannedDays,
                        Color.parseColor("#FFC0CB")));
        pieChart.addPieSlice(
                new PieModel(
                        "Planned days",
                        DestinationFragment.plannedDays,
                        Color.parseColor("#FFA500")));
        pieChart.startAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}