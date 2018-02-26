package com.quandoo.quandootest.repository.network;

import com.quandoo.quandootest.repository.network.transport.CustomerApiEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CustomersApi {

    @GET("/quandoo-assessment/customer-list.json")
    Observable<List<CustomerApiEntity>> getCustomers();
}
