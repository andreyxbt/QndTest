package com.quandoo.quandootest.repository.providers;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.interactor.Repository;
import com.quandoo.quandootest.repository.providers.RepositoryProvider;

/**
 * RepositoryProviderImpl to mock in UI tests
 */
public class RepositoryProviderImpl implements RepositoryProvider {

    @NonNull
    private Repository repository;

    public RepositoryProviderImpl(@NonNull final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void inject(@NonNull final Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public Repository getRepository() {
        return repository;
    }
}
