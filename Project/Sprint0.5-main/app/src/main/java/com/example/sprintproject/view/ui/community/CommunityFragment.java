package com.example.sprintproject.view.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentCommunityBinding;

public class CommunityFragment extends Fragment {



    private FragmentCommunityBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CommunityViewModel communityViewModel =
                new ViewModelProvider(this).get(CommunityViewModel.class);

        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Allyson Implementation ------------------------

        //added UI buttons
        Button startDateButton = binding.startDateButtonCommunity;
        Button endDateButton = binding.endDateButtonCommunity;

        //calls a new date picker fragment when the button is clicked
        startDateButton.setOnClickListener(v -> {
            DatePickerFragment startDatePicker = DatePickerFragment.newInstance((year, month, day) -> {
                int startYear = year;
                int startMonth = month;
                int startDay = day;
                //call whatever method you need with these variables for start date :)

                //note to self: java converts if for you!!!
                String startDate = startYear + "/" + startMonth + "/" + startDay;
            });
            //make sure it updates properly (will need to test)
            startDatePicker.show(getChildFragmentManager(), "startDatePicker");

        });

        //calls a new date picker fragment when the button is clicked
        endDateButton.setOnClickListener(v -> {
            DatePickerFragment endDatePicker = DatePickerFragment.newInstance((year, month, day) -> {
                int endYear = year;
                int endMonth = month;
                int endDay = day;


            });
            endDatePicker.show(getChildFragmentManager(), "endDatePicker");
        });

        final TextView textView = binding.textCommunity;
        communityViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    public void startDateUsage() {
        int year;
        int month;
        int day;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}