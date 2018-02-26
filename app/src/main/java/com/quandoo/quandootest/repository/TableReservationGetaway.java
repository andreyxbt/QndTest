package com.quandoo.quandootest.repository;

import com.quandoo.quandootest.repository.network.transport.CustomerApiEntity;
import com.quandoo.quandootest.repository.network.transport.TableApiEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface TableReservationGetaway {

    @NonNull
    Observable<List<CustomerApiEntity>> getCustomers();

    @NonNull
    Observable<List<TableApiEntity>> getTables();
}
