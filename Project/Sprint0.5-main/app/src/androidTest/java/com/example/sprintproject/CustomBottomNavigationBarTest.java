package com.example.sprintproject;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import com.example.sprintproject.view.NavigationActivity;

@RunWith(AndroidJUnit4.class)
public class CustomBottomNavigationViewTest {

    @Before
    public void setUp() {
        // Launch the MainActivity that contains your BottomNavigationView
        ActivityScenario.launch(NavigationActivity.class);
    }

    @Test
    public void testBottomNavigationViewSwitchesFragments() {
        // Accommodation
        Espresso.onView(withId(R.id.navigation_accommodation)).perform(click());
        Espresso.onView(withId(R.id.
                        fragment_accommodation))
                .check(matches(isDisplayed()));

        // Logistics
        Espresso.onView(withId(R.id.navigation_home)).perform(click());
        Espresso.onView(withId(R.id.fragment_logistics))
                .check(matches(isDisplayed()));

        // Destination
        Espresso.onView(withId(R.id.navigation_notifications)).perform(click());
        Espresso.onView(withId(R.id.fragment_destination))
                .check(matches(isDisplayed()));

        // Dining
        Espresso.onView(withId(R.id.navigation_dining)).perform(click());
        Espresso.onView(withId(R.id.fragment_dining))
                .check(matches(isDisplayed()));

        // Transportation
        Espresso.onView(withId(R.id.navigation_dashboard)).perform(click());
        Espresso.onView(withId(R.id.fragment_transportation))
                .check(matches(isDisplayed()));

        // Community
        Espresso.onView(withId(R.id.navigation_community)).perform(click());
        Espresso.onView(withId(R.id.fragment_community))
                .check(matches(isDisplayed()));
    }
}
