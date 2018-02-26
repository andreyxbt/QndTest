package com.quandoo.quandootest.repository.database.dao;

import android.arch.persistence.room.Embedded;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.TableAndCustomer;

public class TableAndCustomerDBEntity implements TableAndCustomer {

    @Embedded
    @NonNull
    public final TableDBEntity table;

    @Embedded
    @Nullable
    public final CustomerDBEntity customer;

    public TableAndCustomerDBEntity(@NonNull final TableDBEntity table, @Nullable final CustomerDBEntity customer) {
        this.table = table;
        this.customer = customer;
    }

    @NonNull
    @Override
    public TableDBEntity getTable() {
        return table;
    }

    @Nullable
    @Override
    public CustomerDBEntity getCustomer() {
        return customer;
    }
}
