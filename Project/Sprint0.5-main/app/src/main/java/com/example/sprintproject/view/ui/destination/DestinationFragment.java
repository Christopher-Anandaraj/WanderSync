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
import com.example.sprintproject.view.CreateAccount;
import com.example.sprintproject.view.SecondActivity;
import com.google.firebase.auth.FirebaseUser;

public class DestinationFragment extends Fragment {

    private FragmentDestinationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);

        binding = FragmentDestinationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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
            createTravelLog(travelLocation, startDate, endDate);
        });

        destinationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void createTravelLog(String travelLocation, String startDate, String endDate){
        if (travelLocation.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            //If any of the fields are empty
        }

        //For Sophie Create travel log in Database
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}