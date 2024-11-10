package com.example.sprintproject;

import com.example.sprintproject.view.ui.ReservationUtils;

import org.junit.Assert;
import org.junit.Test;

public class IsValidDurationTest {
    @Test
    public void testValidDuration() {
        boolean duration = ReservationUtils.isValidDate("");
        Assert.assertEquals(false, duration);
    }
}
