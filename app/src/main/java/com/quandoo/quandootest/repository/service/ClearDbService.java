package com.quandoo.quandootest.repository.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.quandoo.quandootest.QuandooApp;
import com.quandoo.quandootest.domain.interactor.ClearDbInteractor;

import io.reactivex.schedulers.Schedulers;

@Deprecated
public class ClearDbService extends Service {
    @VisibleForTesting
    static final String ACTION_EXECUTE = "com.quandoo.quandootest.ACTION_EXECUTE";

    @NonNull
    static Intent buildIntent(@NonNull final Context context) {
        return new Intent(context, ClearDbService.class).setAction(ACTION_EXECUTE);
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (intent != null && ACTION_EXECUTE.equals(intent.getAction())) {
            final ClearDbInteractor interactor = new ClearDbInteractor(QuandooApp.getInstance().getRepository());
            interactor.clearDb()
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> stopSelf(startId));
        } else {
            stopSelf(startId);
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }
}