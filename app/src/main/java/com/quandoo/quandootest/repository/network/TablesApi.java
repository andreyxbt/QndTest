package com.quandoo.quandootest.repository.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TablesApi {

    @GET("/quandoo-assessment/table-map.json")
    Observable<List<Boolean>> getTables();
}
