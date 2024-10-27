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

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;  // To populate the ListView with travel logs
import android.widget.ListView;  // For displaying travel logs in a list

import com.google.firebase.database.DataSnapshot;  // For reading data from Firebase
import com.google.firebase.database.DatabaseError;  // For Firebase database error handling
import com.google.firebase.database.DatabaseReference;  // To reference a specific part of Firebase
import com.google.firebase.database.ValueEventListener;  // For listening to Firebase data changes

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
            if (!DestinationUtils.isValidDate(startDate) || !DestinationUtils.isValidDate(endDate) || !DestinationUtils.isStartDateBeforeEndDate(startDate, endDate)) {
                Toast.makeText(getContext(), "Please enter valid dates.", Toast.LENGTH_SHORT).show();
                return;
            }

            createTravelLog(travelLocation, startDate, endDate);
        });

        //Allyson ---------------------------------------------------------------------------------
        //Added vacation_time form
        TableLayout vacation_time_form = binding.vacationTimeForm;
        TableLayout vacation_time_form_results = binding.vacationTimeFormResults;

        //Added Buttons for Calculate Vacation time
        Button button_calculate_vacation = binding.buttonCalculateVacation;
        Button button_vacation_time_cancel = binding.buttonVacationTimeCancel;
        Button button_vacation_time_submit = binding.buttonVacationTimeSubmit;

        //"edit text" user input
        EditText vacation_time_start_data_info = binding.vacationTimeStartDataInfo;
        EditText vacation_time_end_data_info = binding.vacationTimeEndDataInfo;
        EditText vacation_time_duration_data_info = binding.vacationTimeDurationDataInfo;

        //altered text views
        TextView vacation_time_result = binding.vacationTimeResult;

        //make new log to store data
        Map<String, Object> vacationDatalog = createVacationDays();


        button_calculate_vacation.setOnClickListener(view -> {
            if (vacation_time_form.getVisibility() == View.VISIBLE)
                vacation_time_form.setVisibility(View.GONE);
            else
                vacation_time_form.setVisibility(View.VISIBLE);});

        button_vacation_time_cancel.setOnClickListener(view -> {
            if (vacation_time_form.getVisibility() == View.VISIBLE) {
                vacation_time_form.setVisibility(View.GONE);
                vacation_time_start_data_info.setText("");
                vacation_time_end_data_info.setText("");
                vacation_time_duration_data_info.setText("");}
        });

        button_vacation_time_submit.setOnClickListener(v -> {
            String vacationStartData = vacation_time_start_data_info.getText().toString().trim();
            String vacationEndData = vacation_time_end_data_info.getText().toString().trim();
            String vacationDuration = vacation_time_duration_data_info.getText().toString().trim();
            vacation_time_form.setVisibility(View.GONE);

            if (vacationStartData.isEmpty() && vacationEndData.isEmpty() && vacationDuration.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in at least two fields and try again.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!vacationDuration.isEmpty() && isValidDuration(vacationDuration)) {
                vacationDatalog.put("calculatedVacationDuration", vacationDuration);
                vacation_time_form_results.setVisibility(View.VISIBLE);
                vacation_time_result.setText(String.format(Locale.getDefault(), "%.2f", Double.parseDouble(vacationDuration)));
            } else if (!vacationStartData.isEmpty() && !vacationEndData.isEmpty() && DestinationUtils.isValidDate(vacationStartData) && DestinationUtils.isValidDate(vacationEndData) && DestinationUtils.isStartDateBeforeEndDate(vacationStartData, vacationEndData)) {
                loadTravelLogsDuration(totalDuration -> {
                    vacationDatalog.put("calculatedVacationDuration", vacationDuration);
                    vacation_time_form_results.setVisibility(View.VISIBLE);
                    vacation_time_result.setText(String.format(Locale.getDefault(), "%.2f", totalDuration));
                });
            } else if (!vacationStartData.isEmpty() && !vacationDuration.isEmpty() && DestinationUtils.isValidDate(vacationStartData) && isValidDuration(vacationDuration)) {
                String endDate = calculateEndDate(vacationStartData, vacationDuration);
                loadTravelLogsDuration(totalDuration -> {
                    vacationDatalog.put("calculatedVacationDuration", vacationDuration);
                    vacation_time_form_results.setVisibility(View.VISIBLE);
                    vacation_time_result.setText(String.format(Locale.getDefault(), "%.2f", totalDuration));
                });
            } else if (!vacationEndData.isEmpty() && !vacationDuration.isEmpty() && DestinationUtils.isValidDate(vacationEndData) && isValidDuration(vacationDuration)) {
                String startDate = calculateStartDate(vacationEndData, vacationDuration);
                loadTravelLogsDuration(totalDuration -> {
                    vacationDatalog.put("calculatedVacationDuration", vacationDuration);
                    vacation_time_form_results.setVisibility(View.VISIBLE);
                    vacation_time_result.setText(String.format(Locale.getDefault(), "%.2f", totalDuration));
                });
            }
        });

        destinationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        loadTravelLogs(listView);
        return root;
    }

    //add to firebase
    private Map<String, Object> createVacationDays() {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return null;
        }
        String uid = currentUser.getUid();
        DatabaseReference vacationLogRef = FirebaseManager.getInstance().getDatabaseReference()
                .child("users").child(uid).child("vacationLogs");

        String logId = vacationLogRef.push().getKey();

        // Map to store only the vacation duration
        Map<String, Object> vacationData = new HashMap<>();

        return vacationData;
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
                    long days = DestinationUtils.calculateDaysBetween(startDate, endDate);

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

    //Allyson ____________________________________________________________________________________________
    private List<Double> travelLogsDuration = new ArrayList<>();

    private void loadTravelLogsDuration(OnDurationLoadedListener listener) {
        FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DatabaseReference travelLogRef = FirebaseManager.getInstance().getDatabaseReference()
                .child("travelLogs").child(userId);

        travelLogRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                travelLogsDuration.clear();  // Clear the list to avoid duplicates

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String startDate = snapshot.child("startDate").getValue(String.class);
                    String endDate = snapshot.child("endDate").getValue(String.class);

                    double days = (double) DestinationUtils.calculateDaysBetween(startDate, endDate);
                    travelLogsDuration.add(days);
                }

                // Callback to notify that the duration is loaded
                if (listener != null) {
                    listener.onDurationLoaded(countDurationTotal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load travel logs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnDurationLoadedListener {
        void onDurationLoaded(double totalDuration);
    }

    private Double countDurationTotal() {
        double total = 0;
        if (travelLogsDuration.isEmpty()) {
            return 0.0;
        }else{
            for (Double day: travelLogsDuration) {
                total += day;
            }
        }
        return total;
    }

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

    //new methods to find gives days using duration instead of start/end data
    private String calculateEndDate(String startDate, String duration) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the dates from strings
            Date start = dateFormat.parse(startDate);

            if (start != null && duration != null) {

                //convert duration to int
                int daysToAdd = Integer.parseInt(duration);

                // Set calendar to be at given start date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(start);

                calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);

                //Return end date
                return dateFormat.format(calendar.getTime());
            }
            //What is this?? (ASK)
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // If there's a parsing error or invalid input
        return "ERROR";
    }

    private String calculateStartDate(String endDate, String duration) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the dates from strings
            Date end = dateFormat.parse(endDate);

            if (end != null && duration != null) {

                //convert duration to int
                int daysToAdd = Integer.parseInt(duration);

                // Set calendar to be at given start date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(end);

                calendar.add(Calendar.DAY_OF_YEAR, -daysToAdd);

                //Return end date
                return dateFormat.format(calendar.getTime());
            }
            //What is this?? (ASK)
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // If there's a parsing error or invalid input
        return "ERROR";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
