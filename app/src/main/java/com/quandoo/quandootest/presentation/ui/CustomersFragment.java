package com.quandoo.quandootest.presentation.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quandoo.quandootest.R;
import com.quandoo.quandootest.domain.equality.CustomerEqualitator;
import com.quandoo.quandootest.presentation.CustomersViewModel;
import com.quandoo.quandootest.presentation.ReservationViewModel;
import com.quandoo.quandootest.presentation.adapters.CustomerAdapter;
import com.quandoo.quandootest.presentation.adapters.CustomerDiffCallback;

public class CustomersFragment extends Fragment {

    private static final String SELECTED_ID_KEY = "customers_fragment_selected_id_key";

    private CustomersViewModel customersViewModel;
    @Nullable
    private ReservationViewModel reservationViewModel;
    @Nullable
    private Long selectedId;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customersViewModel = ViewModelProviders.of(this).get(CustomersViewModel.class);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            reservationViewModel = ViewModelProviders.of(activity).get(ReservationViewModel.class);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        customersViewModel.updateCustomersSoft();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_customers_list, container, false);
        final RecyclerView recyclerView = rootView.findViewById(R.id.customer_recycler_view);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final CustomerAdapter adapter = new CustomerAdapter(new CustomerDiffCallback(new CustomerEqualitator()));
        customersViewModel.getCustomers().observe(this, adapter::setList);
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(customersViewModel::updateCustomers);
        customersViewModel.getUpdating().observe(this, swipeRefreshLayout::setRefreshing);

        final TextView updateStatus = rootView.findViewById(R.id.update_status);
        customersViewModel.getConnectionStatus().observe(this, connectionStatus -> {
            if (connectionStatus != null) {
                updateStatus.setText(connectionStatus.message);
                updateStatus.setVisibility(View.VISIBLE);
            } else {
                updateStatus.setVisibility(View.GONE);
            }
        });

        if (savedInstanceState != null) {
            final long restoredId = savedInstanceState.getLong(SELECTED_ID_KEY, -1L);
            if (restoredId != -1L) {
                selectedId = restoredId;
            }
        }

        if (reservationViewModel != null) {
            adapter.setCustomerSelectListener(id -> {
                if (selectedId == null) {
                    selectedId = id;
                } else {
                    selectedId = id == selectedId ? null : id;
                }
                reservationViewModel.setSelectedId(id);
                reservationViewModel.switchToTab();
            });
            reservationViewModel.getSelectedId().observe(this, adapter::setSelectedId);
            reservationViewModel.setSelectedId(selectedId);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        if (selectedId != null) {
            outState.putLong(SELECTED_ID_KEY, selectedId);
        }
        super.onSaveInstanceState(outState);
    }
}
