package com.example.sprintproject.view.ui.logistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
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

        ImageButton addContributor = binding.buttonAddContributors;
        ImageButton notes = binding.buttonNotes;

        addContributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addContributorsFragment = new AddContributorFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_logistics, addContributorsFragment)
                        .addToBackStack(null).commit();
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Function
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}