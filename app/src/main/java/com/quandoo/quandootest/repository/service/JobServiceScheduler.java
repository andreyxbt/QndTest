package com.quandoo.quandootest.repository.service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

class JobServiceScheduler {

    private static final long MAX_EXECUTION_DELAY_MILLIS = 10_000L;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void scheduleJob(@NonNull final Context context, @NonNull final JobInfo.Builder builder) {
        builder.setOverrideDeadline(MAX_EXECUTION_DELAY_MILLIS);
        final JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }
}
