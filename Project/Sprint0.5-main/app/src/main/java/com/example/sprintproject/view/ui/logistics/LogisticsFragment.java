package com.example.sprintproject.view.ui.logistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.FragmentLogisticsBinding;
import com.example.sprintproject.view.ui.destination.DestinationFragment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class LogisticsFragment extends Fragment {

    private FragmentLogisticsBinding binding;
    public PieChart pieChart;
    CardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogisticsViewModel logisticsViewModel =
                new ViewModelProvider(this).get(LogisticsViewModel.class);

        binding = FragmentLogisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardView = root.findViewById(R.id.cardViewGraph);
        cardView.setVisibility(View.GONE);
        Button button_dataVis = binding.buttonDataVis;
        button_dataVis.setOnClickListener(view -> {
            if (cardView.getVisibility() == View.VISIBLE)
                cardView.setVisibility(View.GONE);
            else
                cardView.setVisibility(View.VISIBLE);});

        pieChart = root.findViewById(R.id.piechart);
        setData();

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


    private void setData() {
        pieChart.addPieSlice(
                new PieModel(
                        "Allocated days",
                        DestinationFragment.allocatedDays - DestinationFragment.plannedDays,
                        Color.parseColor("#FFC0CB")));
        pieChart.addPieSlice(
                new PieModel(
                        "Planned days",
                        DestinationFragment.plannedDays,
                        Color.parseColor("#FFA500")));
        pieChart.startAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}