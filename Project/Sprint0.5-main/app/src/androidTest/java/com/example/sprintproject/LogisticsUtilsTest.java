package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.View;
import android.widget.Button;

import androidx.fragment.app.testing.FragmentScenario;

import com.example.sprintproject.view.ui.logistics.LogisticsFragment;

import org.junit.Test;

public class LogisticsUtilsTest {
    @Test
    public void testCardViewVisibilityToggle() {
        FragmentScenario<LogisticsFragment> scenario =
                FragmentScenario.launchInContainer(LogisticsFragment.class);

        scenario.onFragment(fragment -> {
            View cardView = fragment.getView().findViewById(R.id.cardViewGraph);
            Button button = fragment.getView().findViewById(R.id.buttonDataVis);

            assertEquals(View.GONE, cardView.getVisibility());

            // Simulate button click to show the card view
            button.performClick();
            assertEquals(View.VISIBLE, cardView.getVisibility());

            // Simulate another button click to hide the card view
            button.performClick();
            assertEquals(View.GONE, cardView.getVisibility());
        });
    }

    @Test
    public void testPieChartDataInitialization() {
        FragmentScenario<LogisticsFragment> scenario =
                FragmentScenario.launchInContainer(LogisticsFragment.class);

        scenario.onFragment(fragment -> {
            assertNotNull(fragment.getPieChart());

            // Verify that the pie chart has data (2 slices added)
            assertEquals(2, fragment.getPieChart().getData().size());
        });
    }
}
