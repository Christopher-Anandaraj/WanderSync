package com.example.sprintproject.view.ui.dining;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.databinding.FragmentDiningBinding;

import java.util.ArrayList;

public class DiningFragment extends Fragment {

    private FragmentDiningBinding binding;

    //holds all entries for dining (Allyson)
    ArrayList<DiningEntry> diningEntries = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiningViewModel diningViewModel =
                new ViewModelProvider(this).get(DiningViewModel.class);

        binding = FragmentDiningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Allyson Implementation ------------------------------------------------------------------------

        //recycle view
        RecyclerView recyclerView = binding.diningList;

        //setup dining list
        //setUpDiningEntriesArray();

        //testing for recycle view and acts as setUpDiningEntriesArray method for testing
        diningEntries.add(new DiningEntry("Tech Dr.", "Wingnuts", "2:00", "wingnuts.com"));

        //Buttons for Dining Screen
        Button open_reservation_cardview_button = binding.openReservationCardviewButton;

        //cardviews
        CardView reservation_cardview = binding.reservationCardview;

        //Opens and closes the Add reservation viewcard
        open_reservation_cardview_button.setOnClickListener(view -> {
            if (reservation_cardview.getVisibility() == View.GONE) {
                reservation_cardview.setVisibility(View.VISIBLE);
            } else {
                reservation_cardview.setVisibility(View.GONE);
            }
        });

        //setup recycleview functionality (sending dining entries to adapter)
        //call constructor
        DiningRecycleViewAdapter adapter = new DiningRecycleViewAdapter(this.getContext(), diningEntries);
        recyclerView.setAdapter(adapter);

        //layout is linear layout for the boxes
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //End of Allyson Implementation ------------------------------------------------------------------

        final TextView textView = binding.textDining;
        diningViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;


    }

    //Allyson Implementation ------------------------------------------------------------------------
    private void setUpDiningEntriesArray() {
        //implementation for adding reservations from database(reach out to sophie) to dining arraylist
    }


    //End of Allyson Implementation ------------------------------------------------------------------

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}