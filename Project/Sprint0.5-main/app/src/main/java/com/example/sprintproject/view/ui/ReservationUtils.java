package com.example.sprintproject.view.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReservationUtils {


    /** Validates whether the provided date string conforms to the "yyyy-MM-dd" date format.
     * <p>This method uses strict parsing to ensure that the input string represents a valid date
     * in the specified format. It returns {@code true} if the date is valid and {@code false} otherwise.
     * @param date the date string to validate, expected in "yyyy-MM-dd" format
     * @return {@code true} if the date string is a valid date in the specified format; {@code false} otherwise
     */
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

    /** Determines whether the start date occurs before the end date.
     * <p>This method parses the provided date strings and checks if the start date is before
     * the end date. It returns {@code true} if both dates are valid and the start date is earlier;
     * otherwise, it returns {@code false}.
     * @param startDate the start date string in "yyyy-MM-dd" format
     * @param endDate the end date string in "yyyy-MM-dd" format
     * @return {@code true} if the start date is before the end date; {@code false} otherwise*/
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

    /**Calculates the number of days between two dates.
     * <p>This method parses the start and end date strings and computes the difference in days.
     * If the dates are valid, it returns the number of days between them. If parsing fails or
     * the inputs are invalid, it returns {@code 0}.
     * @param startDate the start date string in "yyyy-MM-dd" format
     * @param endDate the end date string in "yyyy-MM-dd" format
     * @return the number of days between the start and end dates; {@code 0} if inputs are invalid */
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
