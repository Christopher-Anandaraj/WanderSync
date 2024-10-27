package com.example.sprintproject;

import org.junit.Test;
import static org.junit.Assert.*;

public class DestinationUtilsTest {

    @Test
    public void testIsValidDate_validDate() {
        assertTrue(com.example.sprintproject.view.ui.destination.DestinationUtils.isValidDate("2023-10-27"));
    }

    @Test
    public void testIsValidDate_invalidDate() {
        assertFalse(com.example.sprintproject.view.ui.destination.DestinationUtils.isValidDate("2023-13-01")); // Invalid month
        assertFalse(com.example.sprintproject.view.ui.destination.DestinationUtils.isValidDate("2023-02-30")); // Invalid day
        assertFalse(com.example.sprintproject.view.ui.destination.DestinationUtils.isValidDate("invalid-date")); // Non-date string
    }

    @Test
    public void testIsStartDateBeforeEndDate_validDates() {
        assertTrue(com.example.sprintproject.view.ui.destination.DestinationUtils.isStartDateBeforeEndDate("2023-10-01", "2023-10-31"));
    }

    @Test
    public void testIsStartDateBeforeEndDate_invalidDates() {
        assertFalse(com.example.sprintproject.view.ui.destination.DestinationUtils.isStartDateBeforeEndDate("2023-10-31", "2023-10-01")); // Start date after end date
        assertFalse(com.example.sprintproject.view.ui.destination.DestinationUtils.isStartDateBeforeEndDate("2023-10-01", "invalid-date")); // Invalid end date
    }

    @Test
    public void testCalculateDaysBetween_validDates() {
        long days = com.example.sprintproject.view.ui.destination.DestinationUtils.calculateDaysBetween("2023-10-01", "2023-10-31");
        assertEquals(30, days);
    }

    @Test
    public void testCalculateDaysBetween_sameDate() {
        long days = com.example.sprintproject.view.ui.destination.DestinationUtils.calculateDaysBetween("2023-10-01", "2023-10-01");
        assertEquals(0, days);
    }

    @Test
    public void testCalculateDaysBetween_invalidDates() {
        long days = com.example.sprintproject.view.ui.destination.DestinationUtils.calculateDaysBetween("invalid-date", "2023-10-31");
        assertEquals(0, days);
    }
}