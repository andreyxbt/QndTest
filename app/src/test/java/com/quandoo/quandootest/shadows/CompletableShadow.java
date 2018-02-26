package com.quandoo.quandootest.shadows;

import android.support.annotation.Nullable;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import io.reactivex.Completable;

@Implements(Completable.class)
public class CompletableShadow {

    @Nullable
    private static Completable mock;

    @Implementation
    public static Completable complete() {
        return mock;
    }

    public static void mockCompletable(@Nullable final Completable mock) {
        CompletableShadow.mock = mock;
    }
}
