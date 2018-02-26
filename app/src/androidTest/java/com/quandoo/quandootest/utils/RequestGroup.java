package com.quandoo.quandootest.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RequestGroup {
    private static final int TIMEOUT_SECONDS = 10;

    public static List<RecordedRequest> requestsGroup(@NonNull final MockWebServer server, final int requestCount) throws InterruptedException {
        return requestsGroup(server, requestCount, TIMEOUT_SECONDS);
    }

    public static List<RecordedRequest> requestsGroup(@NonNull final MockWebServer server) throws InterruptedException {
        return requestsGroup(server, server.getRequestCount(), TIMEOUT_SECONDS);
    }

    public static List<RecordedRequest> requestsGroup(@NonNull final MockWebServer server, final int requestCount, final int timeoutSeconds) throws InterruptedException {
        final List<RecordedRequest> requests = new ArrayList<>();
        final int count = Math.min(server.getRequestCount(), requestCount);
        for (int i = 0; i < count; i++) {
            final RecordedRequest request = server.takeRequest(timeoutSeconds, SECONDS);
            if (request != null) {
                requests.add(request);
            }
        }
        return requests;
    }
}