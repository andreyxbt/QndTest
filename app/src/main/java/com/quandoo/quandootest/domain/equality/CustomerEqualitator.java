package com.quandoo.quandootest.domain.equality;

import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Customer;

public class CustomerEqualitator implements Equalitator<Customer> {

    public boolean isEquals(@Nullable final Customer left, @Nullable final Customer right) {
        if (left == right) {
            return true;
        }
        if (left != null && right == null || left == null || left.getClass() != right.getClass()) {
            return false;
        }
        if (left.getId() != right.getId()) {
            return false;
        }
        if (!left.getFirstName().equals(right.getFirstName())) {
            return false;
        }
        return left.getLastName().equals(right.getLastName());
    }
}
