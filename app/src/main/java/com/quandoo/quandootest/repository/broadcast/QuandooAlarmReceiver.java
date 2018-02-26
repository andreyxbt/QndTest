package com.quandoo.quandootest.repository.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.repository.service.QuandooServiceCompat;

public class QuandooAlarmReceiver extends BroadcastReceiver {
    @NonNull
    private static final String ACTION = "com.quandoo.quandootest.CLEAR_DB";

    @NonNull
    public static Intent buildIntent(@NonNull final Context context) {
        return new Intent(context, QuandooAlarmReceiver.class).setAction(ACTION);
    }

    @Override
    public void onReceive(@NonNull final Context context, @Nullable final Intent intent) {
        if (intent == null || !ACTION.equals(intent.getAction())) {
            return;
        }
        QuandooServiceCompat.startService(context);
    }
}
