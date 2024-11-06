package com.example.sprintproject.view.ui.accommodation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentAccommodationBinding;

public class AccommodationFragment extends Fragment {

    private FragmentAccommodationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccommodationViewModel accommodationViewModel =
                new ViewModelProvider(this).get(AccommodationViewModel.class);

        binding = FragmentAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAccommodation;
        accommodationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ImageButton buttonAddAccommodation = binding.buttonAddAccommodation;
        Button buttonConfirmAccommodation = binding.buttonConfirmAccommodation;
        Button buttonCancelAccommodation = binding.buttonCancelAccommodation;
        TableLayout accommodationAdd = binding.accommodationAdd;
        EditText checkInText = binding.accommodationCheckInDate;
        EditText checkOutText = binding.accommodationCheckOutDate;

        buttonAddAccommodation.setOnClickListener(view -> {
            if (accommodationAdd.getVisibility() == View.VISIBLE) {
                accommodationAdd.setVisibility(View.GONE);
            } else {
                accommodationAdd.setVisibility(View.VISIBLE);
            }
        });

        buttonCancelAccommodation.setOnClickListener(view -> {
                    if (accommodationAdd.getVisibility() == View.VISIBLE) {
                        accommodationAdd.setVisibility(View.GONE);
                         checkInText.setText("");
                        checkOutText.setText("");
                    }
                }
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}