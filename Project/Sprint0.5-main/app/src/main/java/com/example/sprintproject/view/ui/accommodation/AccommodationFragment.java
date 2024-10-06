package com.example.sprintproject.view.ui.accommodation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}