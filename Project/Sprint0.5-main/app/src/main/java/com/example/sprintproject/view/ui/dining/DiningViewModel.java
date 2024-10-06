package com.example.sprintproject.view.ui.dining;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.viewmodel.MainViewModel;

public class DiningViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MainViewModel mainViewModel;

    public DiningViewModel() {
        this.mainViewModel = new MainViewModel();
        mText = new MutableLiveData<>();
        mText.setValue(mainViewModel.getDiningMessage().getValue());
    }

    public LiveData<String> getText() {
        return mText;
    }
}