package com.example.sprintproject.view.ui.dining;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByTimeStrategy implements SortingStrategy {
    @Override
    public void sort(List<DiningEntry> diningEntries) {
        Collections.sort(diningEntries, Comparator.comparingInt(DiningEntry::getTotalMinutes));
        System.out.println("Reservations sorted by total minutes.");
    }
}

