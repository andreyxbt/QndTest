package com.quandoo.quandootest.presentation.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.quandoo.quandootest.R;
import com.quandoo.quandootest.presentation.ReservationViewModel;

public class TableReservationActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ReservationViewModel reservationViewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
        setContentView(R.layout.activity_reservations);
        final ViewPager viewPager = findViewById(R.id.reservatopm_viewpager);
        final TabLayout tabLayout = findViewById(R.id.reservation_tabs);
        final ReservationsAdapter adapter = new ReservationsAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        reservationViewModel.getSwitchToTab().observe(this, empty -> viewPager.setCurrentItem(2));
    }
}
