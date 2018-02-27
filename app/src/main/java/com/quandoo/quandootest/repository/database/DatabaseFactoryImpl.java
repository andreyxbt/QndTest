package com.quandoo.quandootest.repository.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.repository.providers.DatabaseFactory;


public class DatabaseFactoryImpl implements DatabaseFactory {

    @NonNull
    private final Context context;
    private final String databaseName;

    public DatabaseFactoryImpl(@NonNull final Context context, @NonNull final String databaseName) {
        this.context = context.getApplicationContext();
        this.databaseName = databaseName;
    }

    @NonNull
    public QuandooDatabase getDatabase() {
        return Room.databaseBuilder(context, QuandooDatabase.class, databaseName).build();
    }
}
