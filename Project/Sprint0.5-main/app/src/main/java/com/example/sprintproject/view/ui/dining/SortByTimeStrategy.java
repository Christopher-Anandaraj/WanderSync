package com.example.sprintproject.view.ui.dining;

import java.util.Comparator;
import java.util.List;

public class SortByTimeStrategy implements SortingStrategy {
    @Override
    public void sort(List<DiningEntry> diningEntries) {
        diningEntries.sort(Comparator.comparing(DiningEntry::getTime));
    }
}
