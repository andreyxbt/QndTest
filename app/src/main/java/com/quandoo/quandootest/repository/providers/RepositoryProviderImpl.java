package com.quandoo.quandootest.repository.providers;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.interactor.Repository;

public class RepositoryProviderImpl implements RepositoryProvider {

    @NonNull
    private Repository repository;

    public RepositoryProviderImpl(@NonNull final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void inject(@NonNull final Repository repository) {
        throw new SecurityException("Unable to inject repository!");
    }

    @NonNull
    public Repository getRepository() {
        return repository;
    }
}
