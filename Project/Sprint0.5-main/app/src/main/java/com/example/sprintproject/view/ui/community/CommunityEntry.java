package com.example.sprintproject.view.ui.community;

//Allyson Implementation (Creator Class) ________________
public class CommunityEntry {
    private final String location;
    private final String food;
    private final String time;
    private final String duration;
    private final String startDate;
    private final String endDate;

    public CommunityEntry(String location, String food, String time, String duration, String startDate, String endDate) {
        this.location = location;
        this.food = food;
        this.time = time;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
