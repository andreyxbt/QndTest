package com.quandoo.quandootest.repository.network;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.repository.TableReservationGetaway;
import com.quandoo.quandootest.repository.network.transport.CustomerApiEntity;
import com.quandoo.quandootest.repository.network.transport.TableApiEntity;
import com.quandoo.quandootest.repository.network.transport.TableApiHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RetrofitTableReservationGetaway implements TableReservationGetaway {

    @NonNull
    private final CustomersApi customersApi;
    @NonNull
    private final TablesApi tablesApi;

    public RetrofitTableReservationGetaway(@NonNull final CustomersApi customersApi, @NonNull final TablesApi tablesApi) {
        this.customersApi = customersApi;
        this.tablesApi = tablesApi;
    }

    @Override
    @NonNull
    public Observable<List<CustomerApiEntity>> getCustomers() {
        return customersApi.getCustomers()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<TableApiEntity>> getTables() {
        return tablesApi.getTables()
                .map(TableApiHelper::toEntities)
                .subscribeOn(Schedulers.io());
    }
}
