package com.example.sprintproject.view.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.databinding.FragmentCommunityBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.MainActivity;
import com.example.sprintproject.view.ui.dining.SortContext;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CommunityFragment extends Fragment implements RecycleViewInterface {

    private FragmentCommunityBinding binding;

    //Added arrayList (Allyson)
    ArrayList<CommunityEntry> communityEntries = new ArrayList<>();

    private SortContext context = new SortContext();

    private FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();

    private DatabaseReference tripRef = FirebaseManager.getInstance().getDatabaseReference()
            .child("communityEntry");


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
        Button openCardButton = binding.communityExit;

        //textViews
        TextView startDateTextDisplay = binding.startDateTextDisplay;
        TextView endDateTextDisplay = binding.endDateTextDisplay;

        //cardView
        CardView communityCard = binding.communityCardview;

        //editText
        EditText communityDestinationInput = binding.communityDestinationInput;
        EditText communityAccommodationsInput = binding.accommodationsInput;
        EditText communityDiningInput = binding.diningInput;
        EditText communityNotesInput = binding.notesInput;

        Context fragContext = requireContext();

        RecyclerView recyclerView = binding.recyclerViewCommunity;

        CommunityRecycleViewAdapter adapter = new CommunityRecycleViewAdapter(this.getContext(), communityEntries, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);


        //opens and closes fragments
        openCardButton.setOnClickListener(v -> {
            if (communityCard.getVisibility() == View.GONE) {
                communityCard.setVisibility(View.VISIBLE);
            } else {
                communityCard.setVisibility(View.GONE);
            }
        });

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
            String destinationReview = communityDestinationInput.getText().toString();
            String accommodationsReview = communityAccommodationsInput.getText().toString();
            String diningReview = communityDiningInput.getText().toString();
            String tripNotes = communityNotesInput.getText().toString();

            if (destinationReview.isEmpty() || accommodationsReview.isEmpty() || diningReview.isEmpty()
                    || tripNotes.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            //make new community entry
            CommunityEntry post = new CommunityEntry(startDate, endDate, diningReview, accommodationsReview, destinationReview, tripNotes);
            //communityEntries.add(post);

            //Please check firefox implementation
            AddToCommunityDatabase add = new AddToCommunityDatabase();
            add.interactWithCommunityDatabase(currentUser, tripRef, post, communityEntries, fragContext);

            communityDestinationInput.setText("");
            communityAccommodationsInput.setText("");
            communityDiningInput.setText("");
            communityNotesInput.setText("");

            adapter.notifyDataSetChanged();
        });

        LoadFromCommunityDatabase load = new LoadFromCommunityDatabase();
        load.interactWithCommunityDatabase(currentUser, tripRef, null, communityEntries, fragContext);
        adapter.notifyDataSetChanged();

        final TextView textView = binding.textCommunity;
        communityViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    public void startDateUsage() {
        int year;
        int month;
        int day;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

    //onclick for recycleview
    @Override
    public void onItemClick(int position) {
        //potential implementation
    }
}