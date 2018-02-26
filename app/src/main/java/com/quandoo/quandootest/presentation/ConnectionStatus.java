package com.quandoo.quandootest.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ConnectionStatus {

    @NonNull
    public final String message;

    @Nullable
    public final Throwable throwable;

    public ConnectionStatus(@NonNull final String message) {
        this(message, null);
    }

    public ConnectionStatus(@NonNull final String message, @Nullable final Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }
}
