package com.quandoo.quandootest.repository.providers;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.interactor.Repository;

public interface RepositoryProvider {

    void inject(@NonNull Repository repository);

    @NonNull
    Repository getRepository();
}
