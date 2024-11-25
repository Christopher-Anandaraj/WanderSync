package com.example.sprintproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.sprintproject.view.ui.community.CommunityFragment.isStartDateBeforeEndDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import android.widget.DatePicker;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.regex.Pattern;

public class CommunityTestCases {

    @Test
    public void testIsStartDateBeforeEndDateTrue() {
        // Test case 1: Valid dates where start date is before end date
        assertTrue(isStartDateBeforeEndDate("2023/01/01", "2023/12/31"));
    }

    @Test
    public void testIsStartDateBeforeEndDateFalse() {
        // Test case 1: Valid dates where start date is before end date
        assertFalse(isStartDateBeforeEndDate("2023-01-01", "2023-12-31"));
    }

    @Test
    public void testIsStartDateBeforeEndDateCommunity() {
        // Test case 1: Valid dates where start date is before end date
        assertFalse(isStartDateBeforeEndDate("2023-01-01", "2023-12-31"));
    }

    @Test
    public void testRecyclerViewIsDisplayed() {
        onView(withId(R.id.communityPostList))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSubmitButtonIsClickable() {
        onView(withId(R.id.addCommunityPostButton))
                .check(matches(isClickable()));
    }

    @Test
    public void testStartDatePickerOpens() {
        onView(withId(R.id.startDateButtonCommunity)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSpinnerDefaultItem() {
        onView(withId(R.id.communityPostDestination))
                .check(matches(withSpinnerText(containsString("Destinations"))));
    }


    @Test
    public void testCommunityCardVisibilityToggle() {
        onView(withId(R.id.createCommunityPostButton)).perform(click());

        // Check if the card is now visible
        onView(withId(R.id.createCommunityPost_cardview))
                .check(matches(isDisplayed()));

        // Toggle back to hide the card
        onView(withId(R.id.createCommunityPostButton)).perform(click());

        // Check if the card is hidden
        onView(withId(R.id.createCommunityPost_cardview))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void testDestinationSpinnerSelection() {
        // Perform a click to open the spinner dropdown
        onView(withId(R.id.communityPostDestination)).perform(click());

        // Select the second item (assuming the spinner has at least two items)
        onData(anything()).atPosition(1).perform(click());

        // Check that the selected item text is displayed
        onView(withId(R.id.communityPostDestination))
                .check(matches(withSpinnerText(containsString("Expected Destination")))); // Replace "Expected Destination" with the actual value
    }

    @Test
    public void testClearInputFieldsAfterSubmission() {
        // Fill in the input fields
        onView(withId(R.id.communityPostAccommodations)).perform(typeText("Hotel XYZ"));
        onView(withId(R.id.communityPostDining)).perform(typeText("Restaurant ABC"));
        onView(withId(R.id.communityPostTransportation)).perform(typeText("Car"));
        onView(withId(R.id.communityPostNotes)).perform(typeText("This was an amazing trip!"));
        closeSoftKeyboard();

        // Submit the post
        onView(withId(R.id.addCommunityPostButton)).perform(click());

        // Verify that all fields are cleared after submission
        onView(withId(R.id.communityPostAccommodations)).check(matches(withText("")));
        onView(withId(R.id.communityPostDining)).check(matches(withText("")));
        onView(withId(R.id.communityPostTransportation)).check(matches(withText("")));
        onView(withId(R.id.communityPostNotes)).check(matches(withText("")));
    }
}
