package com.example.sprintproject.view.ui.destination;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DestinationUtils {

    public static boolean isValidDate(String date) {
        // Assuming date format is "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false); // Strict parsing
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isStartDateBeforeEndDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return start != null && end != null && start.before(end);
        } catch (ParseException e) {
            return false;
        }
    }

    public static long calculateDaysBetween(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the dates from strings
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            if (start != null && end != null) {
                // Calculate the difference in milliseconds
                long diffInMillies = end.getTime() - start.getTime();

                // Convert the difference to days
                return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // If there's a parsing error or invalid input, return 0 days by default
        return 0;
    }
}
