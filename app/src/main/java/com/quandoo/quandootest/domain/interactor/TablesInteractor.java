package com.quandoo.quandootest.domain.interactor;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.domain.entities.TableAndCustomer;

import io.reactivex.Completable;

import static com.quandoo.quandootest.QuandooApp.DB_CLEAN_ALARM_SCHEDULE_MILLIS;

public class TablesInteractor {

    @NonNull
    private final Repository repository;

    public TablesInteractor(@NonNull final Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public DataSource.Factory<Integer, TableAndCustomer> getTablesAndCustomers() {
        return repository.getTablesAndCustomers();
    }

    @NonNull
    public Completable softUpdate() {
        if (repository.isTablesCacheValid(System.currentTimeMillis())) {
            return Completable.complete();
        }
        return update();
    }

    @NonNull
    public Completable updateTableWithNewCustomer(@NonNull final TableAndCustomer tableAndCustomer,
                                                  @Nullable final Long customerId) {
        final Table table = tableAndCustomer.getTable();
        if (table.isReserved() || tableAndCustomer.getCustomer() != null || customerId == null) {
            return Completable.complete();
        }
        return repository.updateTableWithNewCustomer(table.getId(), true, customerId);
    }

    @NonNull
    public Completable update() {
        return repository.updateTables()
                .doOnComplete(() -> repository.scheduleCleanDb(System.currentTimeMillis() + DB_CLEAN_ALARM_SCHEDULE_MILLIS));
    }

    public long getLastUpdateTime() {
        return repository.getTablesLastUpdateTime();
    }
}
