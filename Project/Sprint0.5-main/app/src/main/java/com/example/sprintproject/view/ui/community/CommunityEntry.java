package com.example.sprintproject.view.ui.community;

public class CommunityEntry {
    private final String duration;
    private final String diningReservation;
    private final String accomodationsReservation;
    private final String destination;
    private final String transportation;
    private final String tripNotes;


    //dining entry constructor
    public CommunityEntry(String duration, String diningReview,
                          String accomodationsReview, String destination, String transportation,
                          String tripNotes) {
        this.duration = duration;
        this.diningReservation = diningReview;
        this.accomodationsReservation = accomodationsReview;
        this.destination = destination;
        this.transportation = transportation;
        this.tripNotes = tripNotes;
    }

    //community entry getters
    public String getDuration() {
        return duration;
    }

    public String getDiningReview() {
        return diningReservation;
    }

    public String getAccommodationsReview() {
        return accomodationsReservation;
    }

    public String getDestination() {
        return destination;
    }

    public String getTransportation() { return transportation; }

    public String getTripNotes() {
        return tripNotes;
    }
}
