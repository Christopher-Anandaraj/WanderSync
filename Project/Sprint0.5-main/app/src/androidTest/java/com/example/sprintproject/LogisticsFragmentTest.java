package com.example.sprintproject;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import android.widget.ListView;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.sprintproject.view.ui.destination.DestinationFragment;
import com.example.sprintproject.view.ui.logistics.LogisticsFragment;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogisticsFragmentTest {
    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(LogisticsFragment.class);
    }

    @Test
    public void dataVisualizationButtonTest() {
        Espresso.onView(withId(R.id.buttonDataVis)).perform(click());
        Espresso.onView(withId(R.id.cardViewGraph))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void addContributorTest() {
        Espresso.onView(withId(R.id.button_add_contributors)).perform(click());
        Espresso.onView(withId(R.id.tableLayout_add_contributor))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.editText_contributor_name)).perform(typeText("JUnit_Test"));
        Espresso.onView(withId(R.id.editText_contributor_name)).check(matches(withText("JUnit_Test")));
    }

    @Test
    public void notesTest() {
        String testString = "This is a test";

        Espresso.onView(withId(R.id.button_notes)).perform(click());
        Espresso.onView(withId(R.id.tableLayoutNotes))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.editTextNewNote)).perform(typeText(testString));
        Espresso.onView(withId(R.id.editTextNewNote)).check(matches(withText(equalTo("This is a test"))));
    }
}
