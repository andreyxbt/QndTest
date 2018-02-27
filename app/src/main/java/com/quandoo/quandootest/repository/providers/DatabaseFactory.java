package com.quandoo.quandootest.repository.providers;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.repository.database.QuandooDatabase;

public interface DatabaseFactory {
    @NonNull
    QuandooDatabase getDatabase();
}
