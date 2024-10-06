package com.example.sprintproject.view.ui.logistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.databinding.FragmentLogisticsBinding;

public class LogisticsFragment extends Fragment {

    private FragmentLogisticsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogisticsViewModel logisticsViewModel =
                new ViewModelProvider(this).get(LogisticsViewModel.class);

        binding = FragmentLogisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLogistics;
        logisticsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}