package com.quandoo.quandootest.repository.service;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

public class QuandooServiceCompat {

    public static void startService(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.startService(ClearDbService.buildIntent(context));
        } else {
            JobServiceScheduler.scheduleJob(context, ClearDbJobService.getJobBuilder(context));
        }
    }
}
