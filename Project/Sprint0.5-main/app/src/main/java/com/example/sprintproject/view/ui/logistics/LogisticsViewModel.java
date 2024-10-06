package com.example.sprintproject.view.ui.logistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.viewmodel.MainViewModel;

public class LogisticsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MainViewModel mainViewModel;

    public LogisticsViewModel() {
        this.mainViewModel = new MainViewModel();
        mText = new MutableLiveData<>();
        mText.setValue(mainViewModel.getLogisticsMessage().getValue());
    }

    public LiveData<String> getText() {
        return mText;
    }
}