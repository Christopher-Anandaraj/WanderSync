package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.FirebaseManager;
import com.example.sprintproject.model.User;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends ViewModel {
    private FirebaseManager fbm;
    private LiveData<FirebaseUser> userLiveData;
    private LiveData<User> userDetailsLD;

    public UserViewModel() {
        fbm = FirebaseManager.getInstance();
    }
}
