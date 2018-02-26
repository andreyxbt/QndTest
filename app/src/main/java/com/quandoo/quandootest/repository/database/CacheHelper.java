package com.quandoo.quandootest.repository.database;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.repository.CachePrefs;


public class CacheHelper {

    private final long cacheTtlMillis;
    @NonNull
    private final CachePrefs prefs;
    @NonNull
    private final String key;

    public CacheHelper(@NonNull final String key, @NonNull final CachePrefs prefs, final long cacheTtlMillis) {
        this.key = key;
        this.cacheTtlMillis = cacheTtlMillis;
        this.prefs = prefs;
    }

    public long getCacheTimestamp() {
        return prefs.getCacheTimestamp(key);
    }

    public void setCacheTimestamp(final long timestamp) {
        prefs.setCacheTimestamp(key, timestamp);
    }

    public boolean isCacheValid(final long timestamp) {
        return timestamp - prefs.getCacheTimestamp(key) < cacheTtlMillis;
    }
}
