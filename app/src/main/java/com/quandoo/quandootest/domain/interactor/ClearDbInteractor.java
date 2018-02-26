package com.quandoo.quandootest.domain.interactor;

import android.support.annotation.NonNull;

import io.reactivex.Completable;

public class ClearDbInteractor {
    @NonNull
    private final Repository repository;

    public ClearDbInteractor(@NonNull final Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public Completable clearDb() {
        return repository.clearCustomers()
                .mergeWith(repository.clearTables());
    }
}
