package com.quandoo.quandootest.domain.equality;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.TableAndCustomer;

public class TableAndCustomerEqualitator implements Equalitator<TableAndCustomer> {

    private final TableEqualitator tableEqualitator;
    private final CustomerEqualitator customerEqualitator;

    public TableAndCustomerEqualitator(@NonNull final TableEqualitator tableEqualitator, @NonNull final CustomerEqualitator customerEqualitator) {
        this.tableEqualitator = tableEqualitator;
        this.customerEqualitator = customerEqualitator;
    }

    public boolean isEquals(@Nullable final TableAndCustomer left, @Nullable final TableAndCustomer right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return tableEqualitator.isEquals(left.getTable(), right.getTable()) && customerEqualitator.isEquals(left.getCustomer(), right.getCustomer());
    }
}
