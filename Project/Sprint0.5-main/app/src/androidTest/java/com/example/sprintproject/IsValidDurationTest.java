package com.example.sprintproject;

import org.junit.Assert;
import org.junit.Test;

public class IsValidDurationTest {
    @Test
    public void testValidDuration() {
        boolean duration = com.example.sprintproject.view.ui.destination.DestinationUtils.isValidDate("");
        Assert.assertEquals(false, duration);
    }
}
