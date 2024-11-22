package com.example.sprintproject.view.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentCommunityBinding;
import com.example.sprintproject.view.ui.dining.DiningEntry;
import com.example.sprintproject.view.ui.dining.SortContext;

import java.util.ArrayList;

public class CommunityFragment extends Fragment {



    private FragmentCommunityBinding binding;

    //Added arrayList (Allyson)
    ArrayList<CommunityEntry> communityEntries = new ArrayList<>();

    private SortContext context = new SortContext();

    //Too  public??
    String startDate;
    String endDate;

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
        Button addTripReviewButton = binding.addTripPostbutton;

        //textViews
        TextView startDateTextDisplay = binding.startDateTextDisplay;
        TextView endDateTextDisplay = binding.endDateTextDisplay;

        //editText
        EditText communityDestinationInput = binding.communityDestinationInput;
        EditText communityAccommodationsInput = binding.accommodationsInput;
        EditText communityDiningInput = binding.diningInput;
        EditText communityNotesInput = binding.notesInput;


        //calls a new date picker fragment when the button is clicked
        startDateButton.setOnClickListener(v -> {
            DatePickerFragment startDatePicker = DatePickerFragment.newInstance((year, month, day) -> {
                //call whatever method you need with these variables for start date :)

                //note to self: java converts if for you!!!
                startDate = year + "/" + month + "/" + day;
                startDateTextDisplay.setText(startDate);
            });
            //make sure it updates properly (will need to test)
            startDatePicker.show(getChildFragmentManager(), "startDatePicker");

        });

        //calls a new date picker fragment when the button is clicked
        endDateButton.setOnClickListener(v -> {
            DatePickerFragment endDatePicker = DatePickerFragment.newInstance((year, month, day) -> {

                //add method call for firebase upload
                endDate = year + "/" + month + "/" + day;
                endDateTextDisplay.setText(endDate);
            });
            endDatePicker.show(getChildFragmentManager(), "endDatePicker");
        });

        //press submit button
        addTripReviewButton.setOnClickListener(v -> {
            //get review info
            String destinationReview = communityDestinationInput.getText().toString().trim();
            String accommodationsReview = communityAccommodationsInput.getText().toString().trim();
            String diningReview = communityDiningInput.getText().toString().trim();
            String tripNotes = communityNotesInput.getText().toString().trim();
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