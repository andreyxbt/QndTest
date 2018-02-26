package com.quandoo.quandootest.repository.network.transport;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class TableApiHelper {

    private TableApiHelper() {
    }

    @NonNull
    public static List<TableApiEntity> toEntities(@NonNull final List<Boolean> booleans) {
        final int size = booleans.size();
        final List<TableApiEntity> entities = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            entities.add(new TableApiEntity(i, booleans.get(i)));
        }
        return entities;
    }
}
