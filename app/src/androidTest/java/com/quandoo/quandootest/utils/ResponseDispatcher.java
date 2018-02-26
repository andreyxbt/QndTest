package com.quandoo.quandootest.utils;

import android.text.TextUtils;

import io.reactivex.annotations.NonNull;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class ResponseDispatcher extends Dispatcher {

    private static MockResponse buildResponse(@NonNull final String resPath) {
        return new MockResponse()
                .setResponseCode(200)
                .setStatus("OK")
                .setBody(ResUtils.getResource(resPath));
    }

    private static MockResponse buildNotFoundResponse() {
        return new MockResponse()
                .setResponseCode(404)
                .setStatus("NOT FOUND");
    }

    @Override
    public MockResponse dispatch(final RecordedRequest request) throws InterruptedException {
        final String path = request.getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        if (path.endsWith("/customer-list.json")) {
            return buildResponse("/customer-list.json");
        } else if (path.endsWith("/table-map.json")) {
            return buildResponse("/table-map.json");
        } else {
            return buildNotFoundResponse();
        }
    }
}
