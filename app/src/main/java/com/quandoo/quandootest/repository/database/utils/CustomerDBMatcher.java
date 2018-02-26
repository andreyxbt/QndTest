package com.quandoo.quandootest.repository.database.utils;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.repository.database.dao.CustomerDBEntity;

import java.util.List;

public final class CustomerDBMatcher {

    CustomerDBMatcher() {
    }

    @NonNull
    public static CustomerDBEntity[] toDbCustomers(@NonNull final List<? extends Customer> customers) {
        final int size = customers.size();
        final CustomerDBEntity[] entities = new CustomerDBEntity[size];
        for (int i = 0; i < size; i++) {
            entities[i] = from(customers.get(i));
        }
        return entities;
    }

    @NonNull
    public static CustomerDBEntity from(@NonNull final Customer customer) {
        return new CustomerDBEntity(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName()
        );
    }
}
