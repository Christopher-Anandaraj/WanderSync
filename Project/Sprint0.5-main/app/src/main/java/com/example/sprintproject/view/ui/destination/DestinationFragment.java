package com.example.sprintproject.view.ui.destination;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentDestinationBinding;

public class DestinationFragment extends Fragment {

    private FragmentDestinationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);

        binding = FragmentDestinationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDestination;
        destinationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}