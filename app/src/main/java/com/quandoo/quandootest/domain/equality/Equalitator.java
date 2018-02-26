package com.quandoo.quandootest.domain.equality;

import android.support.annotation.Nullable;

public interface Equalitator<T> {
    boolean isEquals(@Nullable T left, @Nullable T right);
}
