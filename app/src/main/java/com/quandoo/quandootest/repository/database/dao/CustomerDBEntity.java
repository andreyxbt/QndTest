package com.quandoo.quandootest.repository.database.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.entities.Customer;

@Entity(tableName = "customers")
public class CustomerDBEntity implements Customer {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public final long id;

    @NonNull
    @ColumnInfo(name = "first_name")
    public final String firstName;

    @NonNull
    @ColumnInfo(name = "last_name")
    public final String lastName;


    public CustomerDBEntity(final long id, @NonNull final String firstName, @NonNull final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public long getId() {
        return id;
    }

    @NonNull
    @Override
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    @Override
    public String getLastName() {
        return lastName;
    }
}
