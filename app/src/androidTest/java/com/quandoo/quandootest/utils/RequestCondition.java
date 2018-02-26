package com.quandoo.quandootest.utils;

import org.assertj.core.api.Condition;

import okhttp3.mockwebserver.RecordedRequest;

public class RequestCondition {
    public static Condition<RecordedRequest> path(final String path) {
        return new Condition<RecordedRequest>() {
            @Override
            public boolean matches(final RecordedRequest value) {
                return value != null
                        && value.getPath() != null
                        && value.getPath().equalsIgnoreCase(path);
            }

            @Override
            public String toString() {
                return path;
            }
        };
    }
}
