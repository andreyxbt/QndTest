package com.quandoo.quandootest.domain.interactor;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.entities.Customer;

import io.reactivex.Completable;

import static com.quandoo.quandootest.QuandooApp.DB_CLEAN_ALARM_SCHEDULE_MILLIS;

public class CustomerInteractor {
    @NonNull
    private final Repository repository;

    public CustomerInteractor(@NonNull final Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public DataSource.Factory<Integer, Customer> getCustomers() {
        return repository.getCustomers();
    }

    @NonNull
    public Completable softUpdate() {
        if (repository.isCustomersCacheValid(System.currentTimeMillis())) {
            return Completable.complete();
        }
        return update();
    }

    @NonNull
    public Completable update() {
        return repository.updateCustomers()
                .doOnComplete(() -> repository.scheduleCleanDb(System.currentTimeMillis() + DB_CLEAN_ALARM_SCHEDULE_MILLIS));
    }

    public long getLastUpdateTime() {
        return repository.getCustomersLastUpdateTime();
    }
}
