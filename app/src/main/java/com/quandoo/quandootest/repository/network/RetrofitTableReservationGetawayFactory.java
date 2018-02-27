package com.quandoo.quandootest.repository.network;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.repository.TableReservationGetaway;
import com.quandoo.quandootest.repository.providers.TableReservationGetawayFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTableReservationGetawayFactory implements TableReservationGetawayFactory {

    @NonNull
    private final String baseUrl;

    public RetrofitTableReservationGetawayFactory(@NonNull final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @NonNull
    public TableReservationGetaway getTimetableGataway() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final CustomersApi customersApi = retrofit.create(CustomersApi.class);
        final TablesApi tablesApi = retrofit.create(TablesApi.class);
        return new RetrofitTableReservationGetaway(customersApi, tablesApi);
    }
}
