package com.example.sprintproject.view.ui.logistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.FragmentAddContributorBinding;

public class AddContributorFragment extends Fragment {
    private FragmentAddContributorBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddContributorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button add = binding.buttonAdd;
        Button cancel = binding.buttonCancel;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add functionality

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add functionality
            }
        });

        return root;
    }
}
