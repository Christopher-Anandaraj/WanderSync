package com.example.sprintproject.view.ui.destination;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.viewmodel.MainViewModel;

public class DestinationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MainViewModel mainViewModel;

    public DestinationViewModel() {
        this.mainViewModel = new MainViewModel();
        mText = new MutableLiveData<>();
        mText.setValue(mainViewModel.getDestinationMessage().getValue());
    }

    public LiveData<String> getText() {
        return mText;
    }
}