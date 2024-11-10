package com.example.sprintproject.view.ui.dining;

//Allyson Implementation ------------------------------------------------------------------------

//Adding Dining Entry as a class
public class DiningEntry {
    private final String location;
    private final String restaurant;
    private final String time;
    private final String link;

    //dining entry constructor
    public DiningEntry(String location, String restaurant, String time, String link) {
        this.location = location;
        this.restaurant = restaurant;
        this.time = time;
        this.link = link;
    }

    //dining entry getters
    public String getLocation() {
        return location;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getTime() {
        return time;
    }

    public String getLink() {
        return link;
    }
}
//End of Allyson Implementation ------------------------------------------------------------------
