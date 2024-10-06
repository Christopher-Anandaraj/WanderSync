package com.example.sprintproject.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class
MainViewModel extends ViewModel {
    //We will use mutableLiveData to hold the data that can be observed by the view
    //Each message will hold the greeting text to welcome users to the activityalue
    private MutableLiveData<String> logisticsMessage;
    private MutableLiveData<String> transportationMessage;
    private MutableLiveData<String> accommodationMessage;
    private MutableLiveData<String> diningMessage;
    private MutableLiveData<String> destinationMessage;
    private MutableLiveData<String> communityMessage;

    //firebase database reference
    private DatabaseReference databaseReference;

    public MainViewModel() {
        logisticsMessage = new MutableLiveData<>();
        transportationMessage = new MutableLiveData<>();
        accommodationMessage = new MutableLiveData<>();
        diningMessage = new MutableLiveData<>();
        destinationMessage = new MutableLiveData<>();
        communityMessage = new MutableLiveData<>();

        logisticsMessage.setValue("Logistics Screen");
        transportationMessage.setValue("Transportation Screen");
        accommodationMessage.setValue("Accommodation Screen");
        diningMessage.setValue("Dining Establishment Screen");
        destinationMessage.setValue("Destination Screen");
        communityMessage.setValue("Travel Community Screen");
    }

    //Provide access to the liveData objects
    public LiveData<String> getLogisticsMessage(){
        return logisticsMessage;
    }

    public LiveData<String> getTransportationMessage(){
        return transportationMessage;
    }

    public LiveData<String> getAccommodationMessage(){
        return accommodationMessage;
    }

    public LiveData<String> getDiningMessage(){
        return diningMessage;
    }

    public LiveData<String> getDestinationMessage(){
        return destinationMessage;
    }

    public LiveData<String> getCommunityMessage(){
        return communityMessage;
    }

    //Behavior Methods
}
