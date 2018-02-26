package com.quandoo.quandootest.domain.entities;

import android.support.annotation.Nullable;

public interface Table {

    long getId();

    boolean isReserved();

    @Nullable
    Long getCustomerId();
}
