package com.example.sprintproject.view.ui.dining;

import android.content.Context;
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

import com.example.sprintproject.databinding.FragmentDiningBinding;
import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.view.ui.community.LoadFromCommunityDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class DiningFragment extends Fragment {

    //holds all entries for dining (Allyson)
    private ArrayList<DiningEntry> diningEntries = new ArrayList<>();

    private SortContext context = new SortContext();

    private FirebaseUser currentUser = FirebaseManager.getInstance().getAuth().getCurrentUser();

    private DatabaseReference reservationRef = FirebaseManager.getInstance().getDatabaseReference()
            .child("diningReservations");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiningViewModel diningViewModel =
                new ViewModelProvider(this).get(DiningViewModel.class);

        com.example.sprintproject.databinding.FragmentDiningBinding binding =
                FragmentDiningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // sophie stuff
        EditText editTextReservationName = binding.diningNameInput;
        Button buttonReservationSubmit = binding.addReservationButton;
        EditText editTextReservationLocation = binding.diningLocationInput;
        EditText editTextReservationWebsite = binding.diningWebsiteInput;
        EditText editTextReservationTime = binding.diningTimeInput;
        Context fragContext = requireContext();
        // end sophie stuff
        //Allyson Implementation ---------------------

        //recycle view
        RecyclerView recyclerView = binding.diningList;

        //setup dining list

        //setup recycleview functionality (sending dining entries to adapter)+ call constructor
        DiningRecycleViewAdapter adapter = new
                DiningRecycleViewAdapter(this.getContext(), diningEntries);
        recyclerView.setAdapter(adapter);

        //layout is linear layout for the boxes
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Buttons for Dining Screen
        Button openReservationCardviewButton = binding.openReservationCardviewButton;

        //cardviews
        CardView reservationCardview = binding.reservationCardview;
        reservationCardview.setVisibility(View.GONE);
        //Opens and closes the Add reservation viewcard
        openReservationCardviewButton.setOnClickListener(view -> {
            if (reservationCardview.getVisibility() == View.GONE) {
                reservationCardview.setVisibility(View.VISIBLE);
            } else {
                reservationCardview.setVisibility(View.GONE);
            }
        });
        //End of Allyson Implementation --------------------------------------------------
        LoadFromDatabase load = new LoadFromDatabase();
        load.interactWithDatabase(currentUser, reservationRef, null,
                diningEntries, fragContext, adapter);
        adapter.notifyDataSetChanged();
        // sophie stuff
        //add reservation button
        buttonReservationSubmit.setOnClickListener(v -> {
            String resvName = editTextReservationName.getText().toString().trim();
            String resvLocation = editTextReservationLocation.getText().toString().trim();
            String resvTime = editTextReservationTime.getText().toString().trim();
            String resvWebsite = editTextReservationWebsite.getText().toString().trim();

            if (resvLocation.isEmpty() || resvTime.isEmpty() || resvWebsite.isEmpty()
                    || resvName.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields and try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidMilitaryTime(resvTime)) {
                Toast.makeText(getContext(), "Please put a valid time input.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            DiningEntry reservation =
                    new DiningEntry(resvLocation, resvName, resvTime, resvWebsite);
            AddToDatabase add = new AddToDatabase();
            add.interactWithDatabase(currentUser, reservationRef, reservation, diningEntries,
                    fragContext, adapter);

            context.setSortStrategy(new SortByTimeStrategy());
            context.sortReservations(diningEntries);

            editTextReservationName.setText("");
            editTextReservationLocation.setText("");
            editTextReservationTime.setText("");
            editTextReservationWebsite.setText("");

            //Ally
            // Update the list and notify the adapter
            adapter.notifyDataSetChanged(); // Notify adapter of data change
            reservationCardview.setVisibility(View.GONE);
        });
        // end of sophie stuff

        //Allyson Implementaion -----------------------------------------
        load = new LoadFromDatabase();
        load.interactWithDatabase(currentUser, reservationRef, null, diningEntries, fragContext, adapter);
        adapter.notifyDataSetChanged();

        final TextView textView = binding.textDining;
        diningViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }
  
    public boolean isValidMilitaryTime(String time) {
        String regex = "^([01]?\\d|2[0-3]):[0-5]\\d$";
        return time.matches(regex);
    }

}