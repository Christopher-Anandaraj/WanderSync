package com.example.sprintproject.view.ui.community;

public class CommunityEntry {
    private final String startDate;
    private final String endDate;
    private final String diningReview;
    private final String accomodationsReview;
    private final String destination;
    private final String tripNotes;


    //dining entry constructor
    public CommunityEntry(String startDate, String endDate, String diningReview, String accomodationsReview, String destination, String tripNotes) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.diningReview = diningReview;
        this.accomodationsReview = accomodationsReview;
        this.destination = destination;
        this.tripNotes = tripNotes;
    }

    //community entry getters
    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDiningReview() {
        return diningReview;
    }

    public String getAccomodationsReview() {
        return accomodationsReview;
    }

    public String getDestination() {
        return destination;
    }

    public String getTripNotes() {
        return tripNotes;
    }
}
