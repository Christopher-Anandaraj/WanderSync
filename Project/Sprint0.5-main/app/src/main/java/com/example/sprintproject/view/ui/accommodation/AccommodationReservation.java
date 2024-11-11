package com.example.sprintproject.view.ui.accommodation;

public class AccommodationReservation {
    private String locationName;
    private String checkInDate;
    private String checkOutDate;
    private String numberOfRooms;
    private String website;
    private String roomType;

    //Accommodation Reservation constructor
    public AccommodationReservation(String locationName, String checkInDate, String checkOutDate,
                                    String numberOfRooms, String website, String roomType) {
        this.locationName = locationName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfRooms = numberOfRooms;
        this.website = website;
        this.roomType = roomType;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
