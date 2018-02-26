package com.quandoo.quandootest.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class CachePrefs {

    @NonNull
    private static final String PREFS_NAME = "cache_prefs";
    @NonNull
    private static final String KEY_CACHE_TIMESTAMP = "key_cache_timestamp_";
    @NonNull
    private final SharedPreferences preferences;

    private CachePrefs(@NonNull final SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @NonNull
    public static CachePrefs from(@NonNull final Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return new CachePrefs(preferences);
    }

    public long getCacheTimestamp(@NonNull final String key) {
        return preferences.getLong(KEY_CACHE_TIMESTAMP + key, 0);
    }

    public void setCacheTimestamp(@NonNull final String key, final long timestamp) {
        preferences.edit().putLong(KEY_CACHE_TIMESTAMP + key, timestamp).apply();
    }
}
