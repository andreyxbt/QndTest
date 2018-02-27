package com.quandoo.quandootest.repository;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.domain.entities.TableAndCustomer;
import com.quandoo.quandootest.domain.interactor.Repository;
import com.quandoo.quandootest.repository.alarm.QuandooAlarmManager;
import com.quandoo.quandootest.repository.database.QuandooDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

import static com.quandoo.quandootest.repository.database.utils.CustomerDBMatcher.toDbCustomers;
import static com.quandoo.quandootest.repository.database.utils.Downcaster.downcastFactory;
import static com.quandoo.quandootest.repository.database.utils.TableDBMatcher.toDbTables;

public class RepositoryImpl implements Repository {

    @NonNull
    private static final String TAG = "[Repository]";
    @NonNull
    private final TableReservationGetaway tableReservationGetaway;
    @NonNull
    private final QuandooDatabase quandooDatabase;
    @NonNull
    private final CacheHelper tableCacheHelper;
    @NonNull
    private final CacheHelper customerCacheHelper;
    @Nullable
    private final QuandooAlarmManager alarmManager;
    @Nullable
    private Completable tablesUpdate;
    @Nullable
    private Completable customersUpdate;

    /**
     * Due to @Nullable annotation of context.getSystemService(Context.ALARM_SERVICE)
     * I've decided to annotate @param alarmManager as nullable.
     * Not sure what is the best way: throw exception and finish the app right from the beginning,
     * or make it possible to work without alarmManager. Second option is more safe, but if
     * logic of database clearing is critical the first way is the right option.
     */
    public RepositoryImpl(@NonNull final TableReservationGetaway tableReservationGetaway,
                          @NonNull final QuandooDatabase flixbusDatabase,
                          @NonNull final CacheHelper tableCacheHelper,
                          @NonNull final CacheHelper customerCacheHelper,
                          @Nullable final QuandooAlarmManager alarmManager) {
        this.tableReservationGetaway = tableReservationGetaway;
        this.quandooDatabase = flixbusDatabase;
        this.tableCacheHelper = tableCacheHelper;
        this.customerCacheHelper = customerCacheHelper;
        this.alarmManager = alarmManager;
    }

    private void updateCustomersDB(@NonNull final List<? extends Customer> customers) {
        quandooDatabase.customersDao().insertAll(toDbCustomers(customers));
    }

    private void updateTablesDB(@NonNull final List<? extends Table> tables) {
        quandooDatabase.tablesDao().insertAll(toDbTables(tables));
    }

    @NonNull
    @Override
    public DataSource.Factory<Integer, Customer> getCustomers() {
        return downcastFactory(quandooDatabase.customersDao().getAllDataSource());
    }

    @NonNull
    @Override
    public DataSource.Factory<Integer, Table> getTables() {
        return downcastFactory(quandooDatabase.tablesDao().getAllDataSource());
    }

    @NonNull
    @Override
    public DataSource.Factory<Integer, TableAndCustomer> getTablesAndCustomers() {
        return downcastFactory(quandooDatabase.tableAndCustomerDao().getAllDataSource());
    }

    @NonNull
    public Completable updateTables() {
        if (tablesUpdate == null) {
            tablesUpdate = getTablesUpdate();
        }
        tablesUpdate
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> tableCacheHelper.setCacheTimestamp(System.currentTimeMillis()),
                        throwable ->
                                Log.d(TAG, "Unable to update. Error: " + throwable.toString(), throwable)
                );
        return tablesUpdate;
    }

    @NonNull
    @Override
    public Completable clearTables() {
        final Completable completable = Completable.fromAction(
                () -> {
                    tableCacheHelper.setCacheTimestamp(0);
                    quandooDatabase.tablesDao().deleteAll();
                }
        );
        completable
                .subscribeOn(Schedulers.io())
                .subscribe();
        return completable;
    }

    @NonNull
    @Override
    public Completable clearCustomers() {
        final Completable completable = Completable.fromAction(
                () -> {
                    customerCacheHelper.setCacheTimestamp(0);
                    quandooDatabase.customersDao().deleteAll();
                }
        );
        completable
                .subscribeOn(Schedulers.io())
                .subscribe();
        return completable;
    }

    @NonNull
    @Override
    public Completable updateTableWithNewCustomer(final long tableId, final boolean isReserved, @Nullable final Long customerId) {
        final Completable completable = Completable.fromAction(
                () -> quandooDatabase.tablesDao().updateTableWithCustomer(tableId, isReserved, customerId)
        );
        completable
                .subscribeOn(Schedulers.io())
                .subscribe();
        return completable;
    }

    @NonNull
    public Completable updateCustomers() {
        if (customersUpdate == null) {
            customersUpdate = getCustomerUpdate();
        }
        customersUpdate
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> customerCacheHelper.setCacheTimestamp(System.currentTimeMillis()),
                        throwable ->
                                Log.d(TAG, "Unable to update. Error: " + throwable.toString(), throwable)
                );
        return customersUpdate;
    }

    @Override
    public void scheduleCleanDb(final long triggerAtMillis) {
        if (alarmManager != null) {
            alarmManager.scheduleCleanDb(triggerAtMillis);
        }
    }

    @Override
    public void cancelCleanDbScheduler() {
        if (alarmManager != null) {
            alarmManager.cancelCleanDb();
        }
    }

    @Override
    public boolean isCustomersCacheValid(final long timeInMillis) {
        return customerCacheHelper.isCacheValid(timeInMillis);
    }

    @Override
    public boolean isTablesCacheValid(final long timeInMillis) {
        return tableCacheHelper.isCacheValid(timeInMillis);
    }

    @Override
    public long getTablesLastUpdateTime() {
        return tableCacheHelper.getCacheTimestamp();
    }

    @Override
    public long getCustomersLastUpdateTime() {
        return customerCacheHelper.getCacheTimestamp();
    }

    @NonNull
    private Completable getTablesUpdate() {
        return tableReservationGetaway.getTables()
                .doOnNext(this::updateTablesDB)
                .flatMapCompletable(timetable -> Completable.complete());
    }

    @NonNull
    private Completable getCustomerUpdate() {
        return tableReservationGetaway.getCustomers()
                .doOnNext(this::updateCustomersDB)
                .flatMapCompletable(timetable -> Completable.complete());
    }
}
