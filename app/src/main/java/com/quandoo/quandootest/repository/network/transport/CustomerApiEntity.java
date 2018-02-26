package com.quandoo.quandootest.repository.network.transport;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.quandoo.quandootest.domain.entities.Customer;

public class CustomerApiEntity implements Customer {

    @SerializedName("id")
    @Expose
    public final long id;

    @SerializedName("customerFirstName")
    @Expose
    @NonNull
    public final String firstName;

    @SerializedName("customerLastName")
    @Expose
    @NonNull
    public final String lastName;

    public CustomerApiEntity(final long id, @NonNull final String firstName, @NonNull final String lastName) {
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
