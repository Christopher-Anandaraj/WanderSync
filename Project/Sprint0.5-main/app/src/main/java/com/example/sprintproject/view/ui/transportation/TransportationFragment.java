package com.example.sprintproject.view.ui.transportation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentTransportationBinding;

public class TransportationFragment extends Fragment {

    private FragmentTransportationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransportationViewModel transportationViewModel =
                new ViewModelProvider(this).get(TransportationViewModel.class);

        binding = FragmentTransportationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTransportation;
        transportationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}