package com.orem.bashhub.utils;

import com.orem.bashhub.data.UserPOJO;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveDataModel extends ViewModel {

    private MutableLiveData<UserPOJO.Data> userLiveData = new MutableLiveData<>();

    public MutableLiveData<UserPOJO.Data> getUserLiveData() {
        return userLiveData;
    }
}
