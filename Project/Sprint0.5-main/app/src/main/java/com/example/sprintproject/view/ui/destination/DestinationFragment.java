package com.example.sprintproject.view.ui.destination;

import android.content.Intent;
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
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.sprintproject.databinding.FragmentDestinationBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.CreateAccount;
import com.example.sprintproject.view.SecondActivity;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;  // To populate the ListView with travel logs
import android.widget.ListView;  // For displaying travel logs in a list

import com.google.firebase.database.DataSnapshot;  // For reading data from Firebase
import com.google.firebase.database.DatabaseError;  // For Firebase database error handling
import com.google.firebase.database.DatabaseReference;  // To reference a specific part of Firebase
import com.google.firebase.database.FirebaseDatabase;  // To get a Firebase database instance
import com.google.firebase.database.ValueEventListener;  // For listening to Firebase data changes
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;  // For handling date parsing exceptions
import java.text.SimpleDateFormat;  // To format and parse dates in "yyyy-MM-dd" format
import java.util.Date;  // For date-related operations
import java.util.HashMap;  // For creating a map to store travel logs
import java.util.Locale;  // For specifying locale in date formatting
import java.util.Map;  // To use a map to structure data
import java.util.concurrent.TimeUnit;  // For calculating the difference between dates in days


public class DestinationFragment extends Fragment {

    private FragmentDestinationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);

        binding = FragmentDestinationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.listViewTravelLogs;


        TextView textView = binding.textDestination;
        Button button_travel_log = binding.buttonLogTravel;
        Button button_travel_log_submit = binding.buttonLogTravelSubmit;
        Button button_travel_log_cancel = binding.buttonLogTravelCancel;
        EditText editText_travel_location = binding.travelLocation;
        EditText editText_start_date = binding.startDate;
        EditText editText_end_date = binding.endDate;
        TableLayout form_vacation = binding.vacationForm;
        //Creates a onClickListener for button_log_travel.
        button_travel_log.setOnClickListener(view -> {
            if (form_vacation.getVisibility() == View.VISIBLE)
              form_vacation.setVisibility(View.GONE);
            else
               form_vacation.setVisibility(View.VISIBLE);});
        button_travel_log_cancel.setOnClickListener(view -> {
            if (form_vacation.getVisibility() == View.VISIBLE) {
                form_vacation.setVisibility(View.GONE);
                editText_travel_location.setText("");
                editText_start_date.setText("");
                editText_end_date.setText("");}
        });

        button_travel_log_submit.setOnClickListener(v -> {
            String travelLocation = editText_travel_location.getText().toString().trim();
            String startDate = editText_start_date.getText().toString().trim();
            String endDate = editText_end_date.getText().toString().trim();

            if (travelLocation.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidDate(startDate) || !isValidDate(endDate) || !isStartDateBeforeEndDate(startDate, endDate)) {
                Toast.makeText(getContext(), "Please enter valid dates.", Toast.LENGTH_SHORT).show();
                return;
            }

            createTravelLog(travelLocation, startDate, endDate);
        });

        //Allyson

        destinationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        loadTravelLogs(listView);
        return root;
    }

    private void createTravelLog(String travelLocation, String startDate, String endDate) {
        // Get the current logged-in user using the FirebaseManager Singleton
        FirebaseUser user = FirebaseManager.getInstance().getAuth().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();


            DatabaseReference travelLogRef = FirebaseManager.getInstance().getDatabaseReference()
                    .child("travelLogs").child(uid);

            String logId = travelLogRef.push().getKey();

            Map<String, Object> travelLogMap = new HashMap<>();
            travelLogMap.put("location", travelLocation);
            travelLogMap.put("startDate", startDate);
            travelLogMap.put("endDate", endDate);

            if (logId != null) {
                travelLogRef.child(logId).setValue(travelLogMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Travel log added!", Toast.LENGTH_SHORT).show();
                                ListView listView = binding.listViewTravelLogs;
                                loadTravelLogs(listView); // Refresh the displayed list
                            } else {
                                Toast.makeText(getContext(), "Failed to add travel log.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Failed to generate log ID.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No user is logged in.", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadTravelLogs(ListView listViewTravelLogs) {
        //This thing follows the Singleton pattern
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        // Reference to the user's travel logs in Firebase
        DatabaseReference travelLogRef = FirebaseManager.getInstance().getDatabaseReference()
                .child("travelLogs").child(userId);

        // List to store the travel logs
        List<String> travelLogs = new ArrayList<>();

        // Firebase event listener to retrieve the travel logs
        travelLogRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                travelLogs.clear();  // Clear the list to avoid duplicates

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String travelLocation = snapshot.child("location").getValue(String.class);
                    String startDate = snapshot.child("startDate").getValue(String.class);
                    String endDate = snapshot.child("endDate").getValue(String.class);

                    // Calculate days between startDate and endDate sorry Allyson lol
                    long days = calculateDaysBetween(startDate, endDate);

                    // Format the string and add it to the travelLogs list
                    travelLogs.add(travelLocation + "          " + days + " days planned");
                }

                // Update the ListView with the new data
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_1, travelLogs);
                listViewTravelLogs.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load travel logs.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isValidDate(String date) {
        // Assuming date format is "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false); // Strict parsing
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isStartDateBeforeEndDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return start != null && end != null && start.before(end);
        } catch (ParseException e) {
            return false;
        }
    }

    private long calculateDaysBetween(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the dates from strings
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            if (start != null && end != null) {
                // Calculate the difference in milliseconds
                long diffInMillies = end.getTime() - start.getTime();

                // Convert the difference to days
                return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // If there's a parsing error or invalid input, return 0 days by default
        return 0;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}