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

import com.example.sprintproject.databinding.FragmentDiningBinding;

public class DiningFragment extends Fragment {

    private FragmentDiningBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiningViewModel diningViewModel =
                new ViewModelProvider(this).get(DiningViewModel.class);

        binding = FragmentDiningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Allyson Implementation ------------------------------------------------------------------------

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

        //End of Allyson Implementation ------------------------------------------------------------------
        final TextView textView = binding.textDining;
        diningViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}