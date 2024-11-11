package com.example.sprintproject.view.ui.dining;

import java.util.List;

public class SortContext {
    private SortingStrategy sortStrategy;

    public void setSortStrategy(SortingStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void sortReservations(List<DiningEntry> diningEntries) {
        if (sortStrategy != null) {
            sortStrategy.sort(diningEntries);
        } else {
            //throw error
        }
    }
}
