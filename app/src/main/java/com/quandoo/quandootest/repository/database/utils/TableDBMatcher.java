package com.quandoo.quandootest.repository.database.utils;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.repository.database.dao.TableDBEntity;

import java.util.List;

public final class TableDBMatcher {

    TableDBMatcher() {
    }

    @NonNull
    public static TableDBEntity[] toDbTables(@NonNull final List<? extends Table> tables) {
        final int size = tables.size();
        final TableDBEntity[] entities = new TableDBEntity[size];
        for (int i = 0; i < size; i++) {
            entities[i] = from(tables.get(i));
        }
        return entities;
    }

    @NonNull
    public static TableDBEntity from(@NonNull final Table table) {
        return new TableDBEntity(
                table.getId(),
                null,
                table.isReserved()
        );
    }
}
