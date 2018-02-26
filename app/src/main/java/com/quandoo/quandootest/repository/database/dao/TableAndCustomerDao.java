package com.quandoo.quandootest.repository.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface TableAndCustomerDao {

    @Query("SELECT * FROM tables LEFT JOIN customers ON tables.customer_id = customers.id")
    DataSource.Factory<Integer, TableAndCustomerDBEntity> getAllDataSource();
}
