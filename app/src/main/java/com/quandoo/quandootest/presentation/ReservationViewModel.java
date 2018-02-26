package com.quandoo.quandootest.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ReservationViewModel extends ViewModel {

    @NonNull
    private final MutableLiveData<Long> selectedId;
    @NonNull
    private final MutableLiveData<Void> switchToTab;

    public ReservationViewModel() {
        selectedId = new MutableLiveData<>();
        switchToTab = new MutableLiveData<>();
    }

    @NonNull
    public LiveData<Long> getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(@Nullable final Long id) {
        selectedId.postValue(id);
    }

    @NonNull
    public MutableLiveData<Void> getSwitchToTab() {
        return switchToTab;
    }

    public void switchToTab() {
        switchToTab.postValue(null);
    }
}
