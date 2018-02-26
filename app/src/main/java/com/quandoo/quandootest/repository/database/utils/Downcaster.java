package com.quandoo.quandootest.repository.database.utils;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

public final class Downcaster {
    Downcaster() {
    }

    @SuppressWarnings("unchecked")
    public static <K, V, U extends V> DataSource.Factory<K, V> downcastFactory(@NonNull final DataSource.Factory<K, U> factory) {
        return (DataSource.Factory<K, V>) factory;
    }
}
