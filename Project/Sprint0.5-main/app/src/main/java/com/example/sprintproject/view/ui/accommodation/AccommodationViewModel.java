package com.example.sprintproject.view.ui.accommodation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.viewmodel.MainViewModel;

public class AccommodationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MainViewModel mainViewModel;

    public AccommodationViewModel() {
        this.mainViewModel = new MainViewModel();
        mText = new MutableLiveData<>();
        mText.setValue(mainViewModel.getAccommodationMessage().getValue());
    }

    public LiveData<String> getText() {
        return mText;
    }
}