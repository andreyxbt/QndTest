package com.quandoo.quandootest.repository.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface CustomersDao {

    @Query("SELECT * FROM customers")
    DataSource.Factory<Integer, CustomerDBEntity> getAllDataSource();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(CustomerDBEntity... customers);

    @Query("DELETE FROM customers")
    void deleteAll();
}
