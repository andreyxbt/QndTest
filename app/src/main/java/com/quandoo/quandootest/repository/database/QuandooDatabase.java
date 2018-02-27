package com.quandoo.quandootest.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.repository.database.dao.CustomerDBEntity;
import com.quandoo.quandootest.repository.database.dao.CustomersDao;
import com.quandoo.quandootest.repository.database.dao.TableAndCustomerDao;
import com.quandoo.quandootest.repository.database.dao.TableDBEntity;
import com.quandoo.quandootest.repository.database.dao.TablesDao;

@Database(entities = {CustomerDBEntity.class, TableDBEntity.class}, version = 1)
public abstract class QuandooDatabase extends RoomDatabase {

    @NonNull
    public abstract CustomersDao customersDao();

    @NonNull
    public abstract TablesDao tablesDao();

    @NonNull
    public abstract TableAndCustomerDao tableAndCustomerDao();
}
