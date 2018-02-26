package com.quandoo.quandootest.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;

import okhttp3.mockwebserver.MockWebServer;

public class EndpointActivityRule<T extends Activity> extends ActivityTestRule<T> {

    private static final String EXTRA_ENDPOINT_URL = "endpointUrl";
    @NonNull
    private final MockWebServer server;

    public EndpointActivityRule(@NonNull final Class<T> activityClass, @NonNull final MockWebServer server) {
        this(activityClass, server, false);
    }

    public EndpointActivityRule(@NonNull final Class<T> activityClass, @NonNull final MockWebServer server, final boolean initialTouchMode) {
        this(activityClass, server, initialTouchMode, false);
    }

    public EndpointActivityRule(@NonNull final Class<T> activityClass, @NonNull final MockWebServer server, final boolean initialTouchMode,
                                final boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
        this.server = server;
    }
}
