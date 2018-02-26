package com.quandoo.quandootest.domain.interactor;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.domain.entities.TableAndCustomer;

import io.reactivex.Completable;

public interface Repository {

    @NonNull
    DataSource.Factory<Integer, Customer> getCustomers();

    @NonNull
    DataSource.Factory<Integer, Table> getTables();

    @NonNull
    DataSource.Factory<Integer, TableAndCustomer> getTablesAndCustomers();

    @NonNull
    Completable updateTables();

    @NonNull
    Completable clearTables();

    @NonNull
    Completable clearCustomers();

    @NonNull
    Completable updateTableWithNewCustomer(long tableId, boolean isReserved, @Nullable Long customerId);

    @NonNull
    Completable updateCustomers();

    void scheduleCleanDb(final long triggerAtMillis);

    void cancelCleanDbScheduler();

    boolean isCustomersCacheValid(final long timeInMillis);

    boolean isTablesCacheValid(final long timeInMillis);

    long getTablesLastUpdateTime();

    long getCustomersLastUpdateTime();
}
