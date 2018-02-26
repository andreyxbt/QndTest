package com.quandoo.quandootest.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import com.quandoo.quandootest.domain.equality.CustomerEqualitator;
import com.quandoo.quandootest.domain.entities.Customer;

public class CustomerDiffCallback extends DiffCallback<Customer> {

    @NonNull
    private final CustomerEqualitator equalitator;

    public CustomerDiffCallback(@NonNull final CustomerEqualitator equalitator) {
        this.equalitator = equalitator;
    }

    @Override
    public boolean areItemsTheSame(@NonNull final Customer oldItem, @NonNull final Customer newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull final Customer oldItem, @NonNull final Customer newItem) {
        return equalitator.isEquals(oldItem, newItem);
    }
}
