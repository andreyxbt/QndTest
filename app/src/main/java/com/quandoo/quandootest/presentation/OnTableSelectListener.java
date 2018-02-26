package com.quandoo.quandootest.presentation;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.entities.TableAndCustomer;

public interface OnTableSelectListener {
    void onSelect(@NonNull final TableAndCustomer tableAndCustomer);
}
