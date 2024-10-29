package com.example.sprintproject;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;

import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.sprintproject.view.NavigationActivity;
import com.example.sprintproject.view.ui.destination.DestinationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class DestinationFragmentTest {
    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(DestinationFragment.class);
    }

    @Test
    public void testTravelLogButton() {
        Espresso.onView(withId(R.id.vacation_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_log_travel)).perform(click());
        Espresso.onView(withId(R.id.vacation_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.button_log_travel)).perform(click());
        Espresso.onView(withId(R.id.vacation_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testTravelLogCancelButton() {
        Espresso.onView(withId(R.id.button_log_travel)).perform(click());
        Espresso.onView(withId(R.id.vacation_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.button_log_travel_cancel)).perform(click());
        Espresso.onView(withId(R.id.vacation_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testCalculateVacationTimeDisplay() {
        Espresso.onView(withId(R.id.button_calculate_vacation)).perform(click());
        Espresso.onView(withId(R.id.vacation_time_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.button_calculate_vacation)).perform(click());
        Espresso.onView(withId(R.id.vacation_time_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        Espresso.onView(withId(R.id.button_calculate_vacation)).perform(click());
        Espresso.onView(withId(R.id.button_vacation_time_cancel)).perform(click());
        Espresso.onView(withId(R.id.vacation_time_form))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testTravelLogInput(){
        //Open the travel log input screen
        Espresso.onView(withId(R.id.button_log_travel)).perform(click());

        //Input the information into each
        String locationText = "Logging Test";
        String startDate = "2024-09-14";
        String endDate = "2024-10-27";


        Espresso.onView(withId(R.id.travel_location)).perform(typeText(locationText));
        Espresso.onView(withId(R.id.travel_location)).check(matches(withText(locationText)));

        Espresso.onView(withId(R.id.start_date)).perform(typeText(startDate));
        Espresso.onView(withId(R.id.start_date)).check(matches(withText(startDate)));

        Espresso.onView(withId(R.id.end_date)).perform(typeText(endDate));
        Espresso.onView(withId(R.id.end_date)).check(matches(withText(endDate)));
    }
}
