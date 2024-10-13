package com.example.sprintproject.view.ui.destination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import  com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.FragmentDestinationBinding;

public class DestinationFragment extends Fragment {

    private FragmentDestinationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);

        binding = FragmentDestinationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        TextView textView = binding.textDestination;
        Button button_vacation_log = binding.buttonLogTravel;
        LinearLayout form_vacation = binding.vacationForm;
        //Creates a onClickListener for button_log_travel.
        button_vacation_log.setOnClickListener(view -> {
            if (form_vacation.getVisibility() == View.VISIBLE)
              form_vacation.setVisibility(View.GONE);
            else
               form_vacation.setVisibility(View.VISIBLE);});
        destinationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}