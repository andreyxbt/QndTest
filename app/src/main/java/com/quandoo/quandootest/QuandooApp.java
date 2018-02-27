package com.quandoo.quandootest;

import android.app.Application;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.interactor.Repository;
import com.quandoo.quandootest.repository.CacheHelper;
import com.quandoo.quandootest.repository.RepositoryImpl;
import com.quandoo.quandootest.repository.TableReservationGetaway;
import com.quandoo.quandootest.repository.alarm.QuandooAlarmManagerImpl;
import com.quandoo.quandootest.repository.database.CachePrefs;
import com.quandoo.quandootest.repository.database.DatabaseFactoryImpl;
import com.quandoo.quandootest.repository.database.QuandooDatabase;
import com.quandoo.quandootest.repository.network.RetrofitTableReservationGetawayFactory;
import com.quandoo.quandootest.repository.providers.RepositoryProvider;
import com.quandoo.quandootest.repository.providers.RepositoryProviderImpl;

public class QuandooApp extends Application {

    // this is how often db will be cleaned by service
    public static final long DB_CLEAN_ALARM_SCHEDULE_MILLIS = 15 * 60_000L;
    // this is just ttl for entities, may be adjusted to prevent reloading before db clearance.
    private static final long CACHE_TTL_MILLIS = 5 * 60_000;

    private static QuandooApp instance;

    private RepositoryProvider repositoryProvider;

    @NonNull
    public static QuandooApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        final CacheHelper tableCacheHelper = new CacheHelper(
                getString(R.string.cache_helper_tables_key),
                CachePrefs.from(this),
                CACHE_TTL_MILLIS);

        final CacheHelper customerCacheHelper = new CacheHelper(
                getString(R.string.cache_helper_customers_key),
                CachePrefs.from(this),
                CACHE_TTL_MILLIS);

        final QuandooDatabase quandooDatabase =
                new DatabaseFactoryImpl(this, getString(R.string.db_name))
                        .getDatabase();

        final TableReservationGetaway tableReservationGetaway =
                new RetrofitTableReservationGetawayFactory(getString(R.string.api_host))
                        .getTimetableGataway();

        final Repository repository = new RepositoryImpl(
                tableReservationGetaway,
                quandooDatabase,
                tableCacheHelper,
                customerCacheHelper,
                QuandooAlarmManagerImpl.from(this)
        );

        repositoryProvider = new RepositoryProviderImpl(repository);
    }

    @NonNull
    public Repository getRepository() {
        return repositoryProvider.getRepository();
    }

    @NonNull
    public RepositoryProvider getRepositoryProvider() {
        return repositoryProvider;
    }
}
