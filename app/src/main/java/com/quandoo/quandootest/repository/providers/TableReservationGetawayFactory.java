package com.quandoo.quandootest.repository.providers;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.repository.TableReservationGetaway;

public interface TableReservationGetawayFactory {
    @NonNull
    TableReservationGetaway getTimetableGataway();
}
