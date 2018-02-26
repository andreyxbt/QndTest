package com.quandoo.quandootest.domain.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface TableAndCustomer {

    @NonNull
    Table getTable();

    @Nullable
    Customer getCustomer();
}
