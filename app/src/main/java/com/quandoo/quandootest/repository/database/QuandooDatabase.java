package com.quandoo.quandootest.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.R;
import com.quandoo.quandootest.repository.database.dao.CustomerDBEntity;
import com.quandoo.quandootest.repository.database.dao.CustomersDao;
import com.quandoo.quandootest.repository.database.dao.TableAndCustomerDao;
import com.quandoo.quandootest.repository.database.dao.TableDBEntity;
import com.quandoo.quandootest.repository.database.dao.TablesDao;

@Database(entities = {CustomerDBEntity.class, TableDBEntity.class}, version = 1)
public abstract class QuandooDatabase extends RoomDatabase {

    private static volatile QuandooDatabase INSTANCE;

    @NonNull
    public static QuandooDatabase getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (QuandooDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuandooDatabase.class, context.getResources().getString(R.string.db_name))
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    public abstract CustomersDao customersDao();

    @NonNull
    public abstract TablesDao tablesDao();

    @NonNull
    public abstract TableAndCustomerDao tableAndCustomerDao();
}
