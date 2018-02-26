package com.quandoo.quandootest;

import android.app.Application;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.interactor.Repository;
import com.quandoo.quandootest.repository.CachePrefs;
import com.quandoo.quandootest.repository.RepositoryImpl;
import com.quandoo.quandootest.repository.RetrofitTableReservationGetaway;
import com.quandoo.quandootest.repository.TableReservationGetaway;
import com.quandoo.quandootest.repository.alarm.QuandooAlarmManagerImpl;
import com.quandoo.quandootest.repository.database.CacheHelper;
import com.quandoo.quandootest.repository.database.QuandooDatabase;
import com.quandoo.quandootest.repository.network.CustomersApi;
import com.quandoo.quandootest.repository.network.TablesApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuandooApp extends Application {

    // this is how often db will be cleaned by service
    public static final long DB_CLEAN_ALARM_SCHEDULE_MILLIS = 15 * 60_000L;
    // this is just ttl for entities, may be adjusted to prevent reloading before db clearance.
    private static final long CACHE_TTL_MILLIS = 5 * 60_000;

    private static QuandooApp instance;

    private Repository repository;

    @NonNull
    public static QuandooApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_host))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final CustomersApi customersApi = retrofit.create(CustomersApi.class);
        final TablesApi tablesApi = retrofit.create(TablesApi.class);
        final CacheHelper tableCacheHelper = new CacheHelper(getString(R.string.cache_helper_tables_key), CachePrefs.from(this), CACHE_TTL_MILLIS);
        final CacheHelper customerCacheHelper = new CacheHelper(getString(R.string.cache_helper_customers_key), CachePrefs.from(this), CACHE_TTL_MILLIS);
        final TableReservationGetaway tableReservationGetaway = new RetrofitTableReservationGetaway(customersApi, tablesApi);
        repository = new RepositoryImpl(
                tableReservationGetaway,
                getDatabase(),
                tableCacheHelper,
                customerCacheHelper,
                QuandooAlarmManagerImpl.from(this)
        );
    }

    @NonNull
    public QuandooDatabase getDatabase() {
        return QuandooDatabase.getInstance(this);
    }

    @NonNull
    public Repository getRepository() {
        return repository;
    }
}
