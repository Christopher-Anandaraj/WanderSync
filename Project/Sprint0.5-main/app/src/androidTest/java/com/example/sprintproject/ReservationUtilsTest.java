package com.example.sprintproject;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.sprintproject.view.ui.ReservationUtils;

public class ReservationUtilsTest {

    @Test
    public void testIsValidDate_validDate() {
        assertTrue(ReservationUtils.isValidDate("2023-10-27"));
    }

    @Test
    public void testIsValidDate_invalidDate() {
        assertFalse(ReservationUtils.isValidDate("2023-13-01")); // Invalid month
        assertFalse(ReservationUtils.isValidDate("2023-02-30")); // Invalid day
        assertFalse(ReservationUtils.isValidDate("invalid-date")); // Non-date string
    }

    @Test
    public void testIsStartDateBeforeEndDate_validDates() {
        assertTrue(ReservationUtils.isStartDateBeforeEndDate("2023-10-01", "2023-10-31"));
    }

    @Test
    public void testIsStartDateBeforeEndDate_invalidDates() {
        assertFalse(ReservationUtils.isStartDateBeforeEndDate("2023-10-31", "2023-10-01")); // Start date after end date
        assertFalse(ReservationUtils.isStartDateBeforeEndDate("2023-10-01", "invalid-date")); // Invalid end date
    }

    @Test
    public void testCalculateDaysBetween_validDates() {
        long days = ReservationUtils.calculateDaysBetween("2023-10-01", "2023-10-31");
        assertEquals(30, days);
    }

    @Test
    public void testCalculateDaysBetween_sameDate() {
        long days = ReservationUtils.calculateDaysBetween("2023-10-01", "2023-10-01");
        assertEquals(0, days);
    }

    @Test
    public void testCalculateDaysBetween_invalidDates() {
        long days = ReservationUtils.calculateDaysBetween("invalid-date", "2023-10-31");
        assertEquals(0, days);
    }
}