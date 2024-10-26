package com.example.sprintproject.view.ui.logistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;

import com.example.sprintproject.R;
import com.example.sprintproject.view.AddContributorActivity;
import com.example.sprintproject.view.MainActivity;
import com.example.sprintproject.view.NavigationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.sprintproject.databinding.FragmentLogisticsBinding;

public class LogisticsFragment extends Fragment {

    private FragmentLogisticsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogisticsViewModel logisticsViewModel =
                new ViewModelProvider(this).get(LogisticsViewModel.class);

        binding = FragmentLogisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageButton button_add_contributors = binding.buttonAddContributors;

        button_add_contributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddContributorActivity.class);
                startActivity(intent);
            }
        });

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