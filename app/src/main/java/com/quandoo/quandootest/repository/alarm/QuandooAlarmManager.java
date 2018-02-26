package com.quandoo.quandootest.repository.alarm;

import android.app.PendingIntent;
import android.support.annotation.NonNull;

public interface QuandooAlarmManager {
    void scheduleAlarm(long triggerAtMillis, @NonNull PendingIntent operation, int alarmType);

    void cancelAlarm(@NonNull PendingIntent pendingIntent);

    void scheduleCleanDb(long triggerAtMillis);

    void cancelCleanDb();
}
