package com.quandoo.quandootest.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.annotations.NonNull;

import static java.lang.Thread.currentThread;

public final class ResUtils {

    private static final String TAG = "[ResUtils]";

    @NonNull
    public static String getResource(@NonNull final String name) {
        return convertStreamToString(currentThread()
                .getContextClassLoader()
                .getResourceAsStream(name));
    }

    @NonNull
    private static String convertStreamToString(@NonNull final InputStream is) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (final IOException e) {
            Log.e(TAG, "Failed to convert Stream to String: ", e);
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (final IOException e) {
                Log.e(TAG, "Failed to close Input Stream", e);
            }
        }
        return sb.toString();
    }
}
