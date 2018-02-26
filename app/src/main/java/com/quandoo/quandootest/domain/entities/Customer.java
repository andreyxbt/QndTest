package com.quandoo.quandootest.domain.entities;

import android.support.annotation.NonNull;

public interface Customer {

    long getId();

    @NonNull
    String getFirstName();

    @NonNull
    String getLastName();
}
