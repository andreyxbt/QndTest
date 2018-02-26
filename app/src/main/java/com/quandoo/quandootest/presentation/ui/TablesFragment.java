package com.quandoo.quandootest.presentation.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quandoo.quandootest.R;
import com.quandoo.quandootest.domain.equality.CustomerEqualitator;
import com.quandoo.quandootest.domain.equality.TableEqualitator;
import com.quandoo.quandootest.presentation.ReservationViewModel;
import com.quandoo.quandootest.presentation.TablesViewModel;
import com.quandoo.quandootest.presentation.adapters.TableAdapter;
import com.quandoo.quandootest.presentation.adapters.TableAndCustomerDiffCallback;

public class TablesFragment extends Fragment {

    private TablesViewModel tablesViewModel;
    @Nullable
    private Long customerId = null;
    @Nullable
    private ReservationViewModel reservationViewModel;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tablesViewModel = ViewModelProviders.of(this).get(TablesViewModel.class);
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            reservationViewModel = ViewModelProviders.of(activity).get(ReservationViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_table_list, container, false);
        final RecyclerView recyclerView = rootView.findViewById(R.id.tables_recycler_view);
        final int spanCount = getResources().getInteger(R.integer.tables_fragment_recycler_span_count);
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final TableAdapter adapter = new TableAdapter(new TableAndCustomerDiffCallback(new TableEqualitator(), new CustomerEqualitator()));
        adapter.setOnTableSelectListener(tableAndCustomer -> tablesViewModel.updateTableWithNewCustomer(tableAndCustomer, customerId));

        tablesViewModel.getTables().observe(this, adapter::setList);
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(tablesViewModel::updateTables);
        tablesViewModel.getUpdating().observe(this, swipeRefreshLayout::setRefreshing);

        final TextView updateStatus = rootView.findViewById(R.id.update_status);
        tablesViewModel.getConnectionStatus().observe(this, connectionStatus -> {
            if (connectionStatus != null) {
                updateStatus.setText(connectionStatus.message);
                updateStatus.setVisibility(View.VISIBLE);
            } else {
                updateStatus.setVisibility(View.GONE);
            }
        });

        if (reservationViewModel != null) {
            reservationViewModel.getSelectedId().observe(this, selectedId -> customerId = selectedId);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        tablesViewModel.updateTablesSoft();
    }
}
