package com.quandoo.quandootest.repository.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.repository.broadcast.QuandooAlarmReceiver;

public class QuandooAlarmManagerImpl implements QuandooAlarmManager {
    private static final int CLEAR_DB_REQUEST_CODE = 1;
    @NonNull
    private final Context context;
    @NonNull
    private final AlarmManager impl;

    private QuandooAlarmManagerImpl(@NonNull final Context context, @NonNull final AlarmManager impl) {
        this.context = context;
        this.impl = impl;
    }

    @Nullable
    public static QuandooAlarmManager from(@NonNull final Context context) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return null;
        }
        return new QuandooAlarmManagerImpl(context, alarmManager);
    }

    @Nullable
    private static PendingIntent getClearDbPendingIntent(@NonNull final Context context, final int flags) {
        final Intent intent = QuandooAlarmReceiver.buildIntent(context);
        try {
            return PendingIntent.getBroadcast(context, CLEAR_DB_REQUEST_CODE, intent, flags);
        } catch (@NonNull final Exception e) {
            // java.lang.SecurityException: Permission Denial: getIntentSender() from pid=31482, uid=10057, (need uid=-1) is not allowed
            return null;
        }
    }

    @Override
    public void scheduleAlarm(final long triggerAtMillis, @NonNull final PendingIntent pendingIntent, final int alarmType) {
        try {
            impl.set(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, pendingIntent);
        } catch (@NonNull final Exception ignored) {
        }
    }

    @Override
    public void cancelAlarm(@NonNull final PendingIntent pendingIntent) {
        try {
            impl.cancel(pendingIntent);
        } catch (@NonNull final Exception ignored) {
        }
    }

    @Override
    public void scheduleCleanDb(final long triggerAtMillis) {
        final int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT;
        final PendingIntent pendingIntent = getClearDbPendingIntent(context, flags);
        if (pendingIntent != null) {
            scheduleAlarm(triggerAtMillis, pendingIntent, AlarmManager.ELAPSED_REALTIME);
        }
    }

    @Override
    public void cancelCleanDb() {
        final int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT;
        final PendingIntent pendingIntent = getClearDbPendingIntent(context, flags);
        if (pendingIntent != null) {
            cancelAlarm(pendingIntent);
        }
    }
}
