package com.example.sprintproject.view.ui.community;

//Allyson Implementation (Creator Class) ________________
public class CommunityEntry {
    private final String location;
    private final String food;
    private final String time;
    private final String duration;

    public CommunityEntry(String location, String food, String time, String duration) {
        this.location = location;
        this.food = food;
        this.time = time;
        this.duration = duration;
    }

    //getters
    public String getLocation() {
        return location;
    }

    public String getFood() {
        return food;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

}
