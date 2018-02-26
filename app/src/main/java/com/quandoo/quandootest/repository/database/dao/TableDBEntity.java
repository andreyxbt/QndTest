package com.quandoo.quandootest.repository.database.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Table;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(tableName = "tables",
        foreignKeys = @ForeignKey(entity = CustomerDBEntity.class, parentColumns = "id", childColumns = "customer_id", onDelete = SET_NULL))
public class TableDBEntity implements Table {

    @ColumnInfo(name = "customer_id")
    final Long customerId;
    @PrimaryKey
    @ColumnInfo(name = "table_id")
    private final long id;
    /**
     * My idea was that table should have a customer to be reserved, but due to receiving array of flags from server
     * I decided to keep possibility for the table to be reserved without customer.
     */
    @ColumnInfo(name = "is_reserved")
    private final boolean isReserved;

    public TableDBEntity(final long id, @Nullable final Long customerId, final boolean isReserved) {
        this.id = id;
        this.customerId = customerId;
        this.isReserved = isReserved;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }

    @Nullable
    @Override
    public Long getCustomerId() {
        return customerId;
    }
}
