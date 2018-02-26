package com.quandoo.quandootest.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import com.quandoo.quandootest.domain.equality.CustomerEqualitator;
import com.quandoo.quandootest.domain.equality.TableEqualitator;
import com.quandoo.quandootest.domain.entities.TableAndCustomer;

public class TableAndCustomerDiffCallback extends DiffCallback<TableAndCustomer> {

    private final TableEqualitator tableEqualitator;
    private final CustomerEqualitator customerEqualitator;

    public TableAndCustomerDiffCallback(@NonNull final TableEqualitator tableEqualitator, @NonNull final CustomerEqualitator customerEqualitator) {
        this.tableEqualitator = tableEqualitator;
        this.customerEqualitator = customerEqualitator;
    }

    @Override
    public boolean areItemsTheSame(@NonNull final TableAndCustomer oldItem, @NonNull final TableAndCustomer newItem) {
        return oldItem.getTable().getId() == newItem.getTable().getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull final TableAndCustomer oldItem, @NonNull final TableAndCustomer newItem) {
        if (oldItem == newItem) {
            return true;
        }
        return tableEqualitator.isEquals(oldItem.getTable(), newItem.getTable())
                && customerEqualitator.isEquals(oldItem.getCustomer(), newItem.getCustomer());
    }
}
