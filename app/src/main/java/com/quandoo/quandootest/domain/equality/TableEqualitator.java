package com.quandoo.quandootest.domain.equality;

import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.domain.entities.Table;

import java.util.Objects;

public class TableEqualitator implements Equalitator<Table> {

    public boolean isEquals(@Nullable final Table left, @Nullable final Table right) {
        if (left == right) {
            return true;
        }
        if (left != null && right == null || left == null || left.getClass() != right.getClass()) {
            return false;
        }
        if (left.getId() != right.getId()) {
            return false;
        }
        if (left.isReserved() != right.isReserved()) {
            return false;
        }
        if (left.getCustomerId() != null) {
            return left.getCustomerId().equals(right.getCustomerId());
        }
        return right.getCustomerId() == null || right.getCustomerId().equals(left.getCustomerId());
    }
}
