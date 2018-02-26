package com.quandoo.quandootest.presentation.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.quandoo.quandootest.R;


class ReservationsAdapter extends FragmentPagerAdapter {

    @NonNull
    private final Context context;

    public ReservationsAdapter(@NonNull final Context context, @NonNull final FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(final int position) {
        return position == 0 ? new CustomersFragment() : new TablesFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return position == 0
                ? context.getString(R.string.page_tite_customers)
                : context.getString(R.string.page_tite_tables);
    }
}
