package com.quandoo.quandootest.repository.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.Nullable;

@Dao
public interface TablesDao {

    @Query("SELECT * FROM tables")
    DataSource.Factory<Integer, TableDBEntity> getAllDataSource();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TableDBEntity... tables);

    @Query("UPDATE tables SET customer_id = :customerId, is_reserved = :isReserved WHERE table_id LIKE :tableId")
    void updateTableWithCustomer(final long tableId, boolean isReserved, @Nullable final Long customerId);

    @Query("DELETE FROM tables")
    void deleteAll();
}
