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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
    private PieChart pieChart;
    private CardView cardView;
    private List<String> contributorsList = new ArrayList<>();

    private List<String> notesList = new ArrayList<>();
    private ArrayAdapter<String> notesAdapter;
    private ArrayAdapter<String> contributorsAdapter;

    /**Called to have the fragment instantiate its user interface view.
     *
     * <p>This method inflates the layout for the fragment and initializes UI components,
     * including buttons, card views, pie charts, and list views. It sets up click listeners
     * for various buttons to handle user interactions such as toggling the visibility of
     * certain UI elements, adding contributors, displaying notes, and updating data visualizations.
     * It also loads initial data for notes and contributors from the database.
     *
     * @param inflater           The LayoutInflater object that can be
     *                           used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view
     *                           that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being
     *                           re-constructed from a previous saved state.
     * @return The root View for the fragment's UI.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogisticsViewModel logisticsViewModel =
                new ViewModelProvider(this).get(LogisticsViewModel.class);

        binding = FragmentLogisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardView = root.findViewById(R.id.cardViewGraph);
        cardView.setVisibility(View.GONE);
        Button buttonDataVis = binding.buttonDataVis;
        buttonDataVis.setOnClickListener(view -> {
            if (cardView.getVisibility() == View.VISIBLE) {
                cardView.setVisibility(View.GONE);
            } else {
                cardView.setVisibility(View.VISIBLE);
            }
        });

        pieChart = root.findViewById(R.id.piechart);
        setData();

        ImageButton addContributor = binding.buttonAddContributors;
        ImageButton notes = binding.buttonNotes;
        TableLayout tableLayoutAddContributor = binding.tableLayoutAddContributor;
        EditText contributorUsername = binding.editTextContributorName;
        Button buttonAddContributor = binding.buttonAddContributor;
        ListView contributorsListView = binding.contributorsList; // Assuming you have this binding
        contributorsAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                contributorsList);
        contributorsListView.setAdapter(contributorsAdapter);

        TableLayout tableLayoutNotes = binding.tableLayoutNotes;
        ListView listViewNotes = binding.listViewNotes;
        listViewNotes.setVisibility(View.VISIBLE);
        notesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, notesList);
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
                    Toast.makeText(getContext(), "Please enter a username.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<String> notesAdapter;
        notesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, notesList);
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

    /** Retrieves the PieChart instance associated with this fragment.
     * @return the PieChart instance */
    public PieChart getPieChart() {
        return pieChart;
    }

    /** Retrieves the CardView instance associated with this fragment.
     * @return the CardView instance */
    public CardView getCardView() {
        return cardView;
    }

    /** Adds a new note to the current user's travel log in the Firebase database.
     * <p>This method retrieves the currently authenticated user
     * and checks if a note has been entered.
     * If a valid note is provided, it is stored under the user's travel log in Firebase.
     * Upon successful addition, the input field is cleared,
     * the notes list is reloaded, and the adapter is notified.
     * If the user is not logged in, or if the note addition
     * fails, an error message is displayed. */
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

    /** Loads all notes associated with the current user and
     * their contributors from the Firebase database.
     * <p>This method retrieves the currently authenticated user, fetches their username,
     * and checks their travel logs in Firebase. If the user is the owner or a contributor
     * of a travel log, all associated notes are retrieved and added to the notes list.
     * The adapter is notified of any changes to update the UI.
     * If there is an error during retrieval, an error message is displayed. */
    private void loadNotes() {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = currentUser.getUid();
        DatabaseReference usersRef =
                FirebaseManager.getInstance().
                        getDatabaseReference().child("users")
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
                        notesList.clear(); // Clear the current notes list

                        // Loop through each user's travel log
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userId = userSnapshot.getKey();

                            // Check if the current user is the owner or a contributor
                            if (userId.equals(currentUserId) || userSnapshot.child("contributors")
                                    .hasChild(currentUsername)) {
                                // Load notes for this user
                                for (DataSnapshot noteSnapshot : userSnapshot.child("notes")
                                        .getChildren()) {
                                    String note = noteSnapshot.getValue(String.class);
                                    Log.d("Note retrieved", "This is what is retrieved: " + note);
                                    if (note != null) {
                                        notesList.add(note);
                                    }
                                }
                            }
                        }
                        // Notify the existing adapter of the changes
                        notesAdapter.notifyDataSetChanged();
                        // This updates the ListView with new data
                        Log.d("LoadNotes", "Notes retrieved: " + notesList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Failed to load notes.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to get current user's username.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Invites a user with the specified username to
     * become a contributor to the current user's travel log.
     * <p>This method first checks if the current user
     * is authenticated and retrieves their username.
     * It then verifies that the invited username exists
     * in the database. If the invited user exists,
     * the method adds the invited user as a contributor
     * to the current user's travel log in Firebase.
     * If the current user is not already listed as a
     * contributor, they are also added. The method
     * updates the contributors list, notifies the
     * adapter, and displays appropriate messages for
     * success or failure.
     * @param invitedUsername the username of the user to be invited as a contributor */
    private void inviteContributor(String invitedUsername) {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the current user's ID
            DatabaseReference usersRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("users").child(userId);

            // Get the current user's username
            usersRef.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String currentUsername = dataSnapshot.getValue(String.class);

                    if (currentUsername != null) {
                        // Check if the invited username exists in the "users" database
                        DatabaseReference allUsersRef = FirebaseManager
                                .getInstance().getDatabaseReference()
                                .child("users");
                        allUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean userExists = false;

                                // Iterate through all users to check for the invited username
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    String savedUsername = userSnapshot.
                                            child("username").getValue(String.class);
                                    if (savedUsername != null && savedUsername
                                            .equals(invitedUsername)) {
                                        userExists = true;
                                        break;
                                    }
                                }

                                if (userExists) {
                                    // Username exists, proceed to invite the contributor
                                    DatabaseReference tripRef = FirebaseManager
                                            .getInstance().getDatabaseReference()
                                            .child("travelLogs")
                                            .child(userId).child("contributors");
                                    // Check if the current user is already a contributor
                                    tripRef.child(currentUsername).addListenerForSingleValueEvent(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull
                                                                         DataSnapshot snapshot) {
                                                    if (!snapshot.exists()) {
                                                        // Current user is not a
                                                        // contributor yet, so add them
                                                        tripRef.child(currentUsername)
                                                                .setValue(true)
                                                                .addOnCompleteListener(task -> {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(
                                                                                getContext(),
                                                                                "You are now "
                                                                                        +
                                                                                "a contributor.",
                                                                                Toast
                                                                            .LENGTH_SHORT).show();
                                                                    } else {
                                                                        Toast.makeText(getContext(),
                                                                        "Failed to add current "
                                                                                +
                                                                            "user as contributor.",
                                                                        Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }

                                                // Add the invited user to contributors
                                                tripRef.child(invitedUsername).setValue(true)
                                                        .addOnCompleteListener(inviteTask -> {
                                                            if (inviteTask.isSuccessful()) {
                                                                contributorsList
                                                                        .add(invitedUsername);
                                                                // Add the invited
                                                                // contributor to the list
                                                                contributorsAdapter
                                                                        .notifyDataSetChanged();
                                                                loadContributors();
                                                                // Notify the
                                                                // adapter about the change
                                                                Toast.makeText(getContext(),
                                                                        "Contributor invited!",
                                                                        Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getContext(),
                                                                "Failed to invite contributor.",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(getContext(),
                                                        "Error checking if "
                                                                +
                                                                "current user is a contributor: "
                                                                + error.getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            });
                                } else {
                                    Toast.makeText(getContext(), "Invited username does not exist.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(),
                                        "Error checking user existence: "
                                                + databaseError.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(),
                                "Current user username not found.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(),
                            "Error retrieving current user username: "
                                    + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(),
                    "No user is logged in.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /** Configures the data for the pie chart by adding
     *  slices representing allocated and planned days.
     * <p>This method initializes the pie chart with two
     * slices: "Allocated days" and "Planned days."
     * The "Allocated days" slice displays the difference between the allocated and planned days,
     * while the "Planned days" slice represents the total number of planned days. Each slice is
     * assigned a specific color. After adding the slices, the method starts the pie chart animation
     * to display the data visually. */
    private void loadContributors() {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(),
                    "No user is logged in.",
                    Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(),
                            "Failed to get username.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //Here is where the pull from travelLog occurs for our database, our current primary issue
                DatabaseReference travelLogRef = FirebaseManager.getInstance()
                        .getDatabaseReference().child("travelLogs");
                travelLogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        contributorsList.clear();

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String ownerId = userSnapshot.getKey();

                            // Check if current user is the trip owner or a contributor
                            if (ownerId.equals(currentUserId)
                                    || userSnapshot.child("contributors")
                                    .hasChild(currentUsername)) {
                                // Load all contributors under this owner's travel log
                                for (DataSnapshot contributorSnapshot
                                        : userSnapshot.child("contributors").getChildren()) {
                                    String contributorUsername = contributorSnapshot.getKey();
                                    if (contributorUsername != null) {
                                        contributorsList.add(contributorUsername);
                                    }
                                }
                                break; // Exit loop once contributors are loaded
                            }
                        }

                        contributorsAdapter.notifyDataSetChanged();
                        // Notify the adapter about the changes
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(),
                                "Error loading contributors: "
                                        + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),
                        "Failed to get current user's username.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Loads the list of contributors associated with the current user's travel log from Firebase.
     * <p>This method first verifies that the current user is authenticated, then retrieves their
     * username. It checks if the current user is the owner or a contributor to a travel log in the
     * database. If they are, it loads all contributors associated with that travel log and updates
     * the contributors list. After loading the contributors, the method notifies the adapter to
     * refresh the displayed data. If the user is not
     * logged in or if an error occurs during retrieval,
     * appropriate messages are displayed to the user. */
    private void setData() {
        pieChart.addPieSlice(
                new PieModel(
                        "Allocated days",
                        DestinationFragment.getAllocatedDays() - DestinationFragment
                                .getPlannedDays(),
                        Color.parseColor("#FFC0CB")));
        pieChart.addPieSlice(
                new PieModel(
                        "Planned days",
                        DestinationFragment.getPlannedDays(),
                        Color.parseColor("#FFA500")));
        pieChart.startAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}