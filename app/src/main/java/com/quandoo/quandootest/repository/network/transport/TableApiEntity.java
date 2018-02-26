package com.quandoo.quandootest.repository.network.transport;

import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Table;

public class TableApiEntity implements Table {

    private final long id;
    private final boolean isOccupied;

    public TableApiEntity(final long id, final boolean isOccupied) {
        this.id = id;
        this.isOccupied = isOccupied;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isReserved() {
        return isOccupied;
    }

    @Nullable
    @Override
    public Long getCustomerId() {
        return null;
    }
}
