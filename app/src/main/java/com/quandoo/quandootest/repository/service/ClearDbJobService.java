package com.quandoo.quandootest.repository.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.SparseArray;

import com.quandoo.quandootest.QuandooApp;
import com.quandoo.quandootest.domain.interactor.ClearDbInteractor;

import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ClearDbJobService extends JobService {
    @NonNull
    private final Object monitor = new Object();
    @NonNull
    private SparseArray<JobParameters> jobParametersCache = new SparseArray<>();

    @NonNull
    static JobInfo.Builder getJobBuilder(@NonNull final Context context) {
        final ComponentName serviceComponent = new ComponentName(context, ClearDbJobService.class);
        return new JobInfo.Builder(0, serviceComponent);
    }

    @Override
    public boolean onStartJob(@Nullable final JobParameters jobParameters) {
        if (jobParameters == null) {
            return false;
        }
        synchronized (monitor) {
            jobParametersCache.put(jobParameters.getJobId(), jobParameters);
        }
        final ClearDbInteractor interactor = new ClearDbInteractor(QuandooApp.getInstance().getRepository());
        interactor.clearDb()
                .subscribeOn(Schedulers.io())
                .subscribe(() -> jobFinished(jobParameters, false));
        return true;
    }

    @Override
    public boolean onStopJob(@Nullable final JobParameters jobParameters) {
        return false;
    }

}
