package com.quandoo.quandootest.presentation;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateHelper {

    private DateHelper() {
        //empty
    }

    @NonNull
    public static String format(final long timestamp) {
        return format(timestamp, TimeZone.getDefault().getID());
    }

    @NonNull
    public static String format(final long timestamp, @NonNull final String timezone) {
        final SimpleDateFormat isoFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        isoFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        final Date date = new Date(timestamp);
        return isoFormat.format(date);
    }
}
