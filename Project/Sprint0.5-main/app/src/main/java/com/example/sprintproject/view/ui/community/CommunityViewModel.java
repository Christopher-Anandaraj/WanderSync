package com.example.sprintproject.view.ui.community;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.viewmodel.MainViewModel;

public class CommunityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MainViewModel mainViewModel;

    public CommunityViewModel() {
        this.mainViewModel = new MainViewModel();
        mText = new MutableLiveData<>();
        mText.setValue(mainViewModel.getCommunityMessage().getValue());
    }

    public LiveData<String> getText() {
        return mText;
    }
}