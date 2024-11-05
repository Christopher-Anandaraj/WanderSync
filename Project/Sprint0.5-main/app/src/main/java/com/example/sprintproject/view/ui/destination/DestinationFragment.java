package com.example.sprintproject.view.ui.destination;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentDestinationBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;  // To populate the ListView with travel logs
import android.widget.ListView;  // For displaying travel logs in a list

import com.google.firebase.database.DataSnapshot;  // For reading data from Firebase
import com.google.firebase.database.DatabaseError;  // For Firebase database error handling
import com.google.firebase.database.DatabaseReference;  // To reference a specific part of Firebase
import com.google.firebase.database.ValueEventListener;  // For listening to Firebase data changes

import java.util.HashMap;  // For creating a map to store travel logs
import java.util.Locale;  // For specifying locale in date formatting
import java.util.Map;  // To use a map to structure data



public class DestinationFragment extends Fragment {

    private FragmentDestinationBinding binding;

    private static int allocatedDays = 0;
    private static int plannedDays = 0;

    /**Contains functionality for buttons, and EditText Fields for Travel Logs and Calculator.
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that
     *                 can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent
     *                  view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment
     *                           is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);

        binding = FragmentDestinationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView listView = binding.listViewTravelLogs;
        TextView textView = binding.textDestination;
        Button buttonTravelLog = binding.buttonLogTravel;
        Button buttonTravelLogSubmit = binding.buttonLogTravelSubmit;
        Button buttonTravelLogCancel = binding.buttonLogTravelCancel;
        EditText editTextTravelLocation = binding.travelLocation;
        EditText editTextStartDate = binding.startDate;
        EditText editTextEndDate = binding.endDate;
        TableLayout formVacation = binding.vacationForm;

        //Creates a onClickListener for button_log_travel.
        buttonTravelLog.setOnClickListener(view -> {
            if (formVacation.getVisibility() == View.VISIBLE) {
                formVacation.setVisibility(View.GONE);
            } else {
                formVacation.setVisibility(View.VISIBLE);
            }
        });

        buttonTravelLogCancel.setOnClickListener(view -> {
            if (formVacation.getVisibility() == View.VISIBLE) {
                formVacation.setVisibility(View.GONE);
                editTextTravelLocation.setText("");
                editTextStartDate.setText("");
                editTextEndDate.setText("");
            }
        }
        );

        buttonTravelLogSubmit.setOnClickListener(v -> {
            String travelLocation = editTextTravelLocation.getText().toString().trim();
            String startDate = editTextStartDate.getText().toString().trim();
            String endDate = editTextEndDate.getText().toString().trim();

            if (travelLocation.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!DestinationUtils.isValidDate(startDate)
                    || !DestinationUtils.isValidDate(endDate)
                    || !DestinationUtils.isStartDateBeforeEndDate(startDate, endDate)) {
                Toast.makeText(getContext(), "Please enter valid dates.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            createTravelLog(travelLocation, startDate, endDate);
            editTextTravelLocation.setText("");
            editTextStartDate.setText("");
            editTextEndDate.setText("");
        });

        //Allyson ---------------------------------------------------------------------------------
        //Added vacation_time form
        TableLayout vacationTimeForm = binding.vacationTimeForm;
        TableLayout vacationTimeFormResults = binding.vacationTimeFormResults;

        //Added Buttons for Calculate Vacation time
        Button buttonCalculateVacation = binding.buttonCalculateVacation;
        Button buttonVacationTimeCancel = binding.buttonVacationTimeCancel;
        Button buttonVacationTimeSubmit = binding.buttonVacationTimeSubmit;

        //"edit text" user input
        EditText vacationTimeStartDataInfo = binding.vacationTimeStartDataInfo;
        EditText vacationTimeEndDataInfo = binding.vacationTimeEndDataInfo;
        EditText vacationTimeDurationDataInfo = binding.vacationTimeDurationDataInfo;

        //altered text views
        TextView vacationTimeResult = binding.vacationTimeResult;
        TextView textVacationTimeResultTitle = binding.textVacationTimeResultTitle;
        //Where is this being used??

        buttonCalculateVacation.setOnClickListener(view -> {
            if (vacationTimeForm.getVisibility() == View.VISIBLE) {
                vacationTimeForm.setVisibility(View.GONE);
            } else {
                vacationTimeForm.setVisibility(View.VISIBLE);
            }
        }
        );

        buttonVacationTimeCancel.setOnClickListener(view -> {
            if (vacationTimeForm.getVisibility() == View.VISIBLE) {
                vacationTimeForm.setVisibility(View.GONE);
                vacationTimeStartDataInfo.setText("");
                vacationTimeEndDataInfo.setText("");
                vacationTimeDurationDataInfo.setText("");
            }
        }
        );

        buttonVacationTimeSubmit.setOnClickListener(v -> {
            String vacationStartData = vacationTimeStartDataInfo.getText().toString().trim();
            String vacationEndData = vacationTimeEndDataInfo.getText().toString().trim();
            String vacationDuration = vacationTimeDurationDataInfo.getText().toString().trim();
            vacationTimeForm.setVisibility(View.GONE);

            // Ensure at least two fields are filled
            if (vacationStartData.isEmpty()
                    && vacationEndData.isEmpty()
                    && vacationDuration.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in at least two fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!vacationDuration.isEmpty() && isValidDuration(vacationDuration)) {
                double duration = Double.parseDouble(vacationDuration);
                vacationTimeFormResults.setVisibility(View.VISIBLE);
                vacationTimeResult.setText(String.format(Locale.getDefault(), "%.2f", duration));
                createVacationDays(duration); // Save the duration directly
                allocatedDays = (int) duration;
            } else if (!vacationStartData.isEmpty() && !vacationEndData.isEmpty()
                    && DestinationUtils.isValidDate(vacationStartData)
                    && DestinationUtils.isValidDate(vacationEndData)
                    && DestinationUtils
                    .isStartDateBeforeEndDate(vacationStartData, vacationEndData)) {
                double daysBetween = DestinationUtils
                        .calculateDaysBetween(vacationStartData, vacationEndData);
                vacationTimeFormResults.setVisibility(View.VISIBLE);
                vacationTimeResult.setText(String.format(Locale.getDefault(), "%.2f", daysBetween));
                createVacationDays(daysBetween); // Save the calculated duration
                allocatedDays = (int) daysBetween;
            } else if (!vacationEndData.isEmpty() && !vacationDuration.isEmpty()
                    && DestinationUtils
                    .isValidDate(vacationEndData) && isValidDuration(vacationDuration)) {
                loadTravelLogsDuration(totalDuration -> {
                    double duration = Double.parseDouble(vacationDuration);
                    createVacationDays(duration); // Save the new duration
                    vacationTimeFormResults.setVisibility(View.VISIBLE);
                    vacationTimeResult.setText(String
                            .format(Locale.getDefault(), "%.2f", totalDuration));
                    allocatedDays = (int) duration;
                });
            } else {
                Toast.makeText(getContext(), "Please enter valid data.", Toast.LENGTH_SHORT).show();
            }
        });

        destinationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        loadTravelLogs(listView);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**Validates whether the provided duration string represents a valid long integer.
     * <p>This method checks if the input string is non-null, not empty after trimming, and can be
     * parsed into a long integer. It returns {@code true} if all these conditions are met,
     * indicating that the duration is valid. If the string is null, empty, or cannot be parsed
     * as a long, it returns {@code false}.
     * @param duration the duration string to validate
     * @return {@code true} if the duration is a valid long integer; {@code false} otherwise
     */

    private boolean isValidDuration(String duration) {
        try {
            if (duration == null || duration.trim().isEmpty()) {
                return false;
            }
            long number = Long.parseLong(duration);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /** Retrieves the total number of allocated vacation days.
     * @return the total allocated days as an integer.*/
    public static int getAllocatedDays() {
        return allocatedDays;
    }
    /** Retrieves the total number of planned vacation days.
     * @return the total planned days as an integer.*/
    public static int getPlannedDays() {
        return plannedDays;
    }

    //Allyson ------------------------
    /**Add to firebase
     * @param duration Takes in double to calculate duration of vacation*/
    private void createVacationDays(Double duration) {
        // Get the current logged-in user using the FirebaseManager Singleton
        FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            // Reference to the specific user's duration log
            DatabaseReference durationLogRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("users").child(uid).child("VacationDays");

            // Validate the duration before attempting to update
            if (duration == null || duration <= 0) {
                Toast.makeText(getContext(),
                        "Invalid vacation duration.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update the value at this path (replaces existing value)
            durationLogRef.setValue(duration)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Vacation duration updated!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to update vacation duration.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No user is logged in.", Toast.LENGTH_SHORT).show();
        }
    }
    /**Add to firebase
     * @param travelLocation Takes in string to add travel location to database
     * @param endDate Takes in string to add end date to database
     * @param startDate Takes in string to add start date to database */
    private void createTravelLog(String travelLocation, String startDate, String endDate) {
        FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();

        if (user != null) {
            String uid = user.getUid(); // Get the user ID
            DatabaseReference travelLogRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("travelLogs").child(uid); // Reference to the user's travel log

            // Generate a unique ID for the destination
            String destinationId = travelLogRef.child("destinations").push().getKey();

            // Create a map for the destination data
            Map<String, Object> destinationMap = new HashMap<>();
            destinationMap.put("location", travelLocation);
            destinationMap.put("startDate", startDate);
            destinationMap.put("endDate", endDate);

            // Include contributor IDs if applicable
            List<String> contributorIds = getContributors(); // This fetches contributor IDs
            if (!contributorIds.isEmpty()) {
                destinationMap.put("contributors", contributorIds);
            }

            if (destinationId != null) {
                // Add destination to the user's travel log
                travelLogRef.child("destinations").child(destinationId).setValue(destinationMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Destination added to travel log!",
                                        Toast.LENGTH_SHORT).show();
                                ListView listView = binding.listViewTravelLogs;
                                loadTravelLogs(listView);
                            } else {
                                Toast.makeText(getContext(), "Failed to add destination.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Failed to generate destination ID.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No user is logged in.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**Logic to fetch contributor IDs (e.g., from UI input or stored list)
     * @return Return an empty list or actual IDs.*/
    private List<String> getContributors() {
        return new ArrayList<>(); //
    }

    /**Logic to load Travel Logs
     * @param listViewTravelLogs instantiates the list to view Travel Logs on Destination Screen*/
    private void loadTravelLogs(ListView listViewTravelLogs) {
        //This thing follows the Singleton pattern
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Failed to get username.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference travelLogRef = FirebaseManager
                        .getInstance().getDatabaseReference()
                        .child("travelLogs");
                List<String> travelLogs = new ArrayList<>();

                travelLogRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        travelLogs.clear();

                        long dayTotal = 0;
                        // Loop through each user's travel log
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userId = userSnapshot.getKey();

                            // Check if the current user is the owner or a contributor by username
                            if (userId.equals(currentUserId)
                                    || userSnapshot.child("contributors")
                                    .hasChild(currentUsername)) {
                                // Load all destinations for this user
                                for (DataSnapshot destinationSnapshot : userSnapshot
                                        .child("destinations")
                                        .getChildren()) {
                                    String travelLocation = destinationSnapshot.child("location")
                                            .getValue(String.class);
                                    String startDate = destinationSnapshot.child("startDate")
                                            .getValue(String.class);
                                    String endDate = destinationSnapshot.child("endDate")
                                            .getValue(String.class);

                                    // Calculate days between startDate and endDate
                                    long days = DestinationUtils
                                            .calculateDaysBetween(startDate, endDate);
                                    dayTotal += days;


                                    String formattedEntry = String.format("%s - %d days planned",
                                            travelLocation, days);
                                    travelLogs.add(formattedEntry);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                        android.R.layout.simple_list_item_1, travelLogs);
                                listViewTravelLogs.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        plannedDays = (int) dayTotal;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Failed to load travel logs.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(
                    @NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load travel logs.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Allyson ___________________________________
    private List<Integer> travelLogsDuration = new ArrayList<>();

    /**Logic to load Travel Duration
     * @param listener OnDurationLoadedListener for loading duration*/
    private void loadTravelLogsDuration(OnDurationLoadedListener listener) {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DatabaseReference durationRef = FirebaseManager.getInstance().getDatabaseReference()
                .child("users").child(userId).child("VacationDays");

        durationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                travelLogsDuration.clear();  // Clear the list to avoid duplicates

                // Retrieve the duration value directly
                Integer duration = dataSnapshot.getValue(Integer.class);
                if (duration != null) {
                    travelLogsDuration.add(duration);
                } else {
                    Toast.makeText(getContext(), "No vacation duration found.",
                            Toast.LENGTH_SHORT).show();
                }

                // Callback to notify that the duration is loaded
                if (listener != null) {
                    listener.onDurationLoaded(countDurationTotal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load vacation duration.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    /** Calculates the total duration by summing all the durations in the `travelLogsDuration` list.
     * If the list is empty, it returns 0.0.
     *
     * @return the total duration as a {@code Double}; 0.0 if the list is empty.
     */
    private Integer countDurationTotal() {
        int total = 0;
        if (travelLogsDuration.isEmpty()) {
            return 0;
        } else {
            for (int day: travelLogsDuration) {
                total += day;
            }
        }
        return total;
    }

    /**Logic for the listener to load travel log duration*/
    public interface OnDurationLoadedListener {
        /**Logic for the listener to load travel log duration
         * @param totalDuration load total duration*/
        void onDurationLoaded(double totalDuration);
    }

}