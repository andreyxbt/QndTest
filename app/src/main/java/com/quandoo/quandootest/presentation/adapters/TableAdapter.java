package com.quandoo.quandootest.presentation.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.quandoo.quandootest.R;
import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.domain.entities.TableAndCustomer;
import com.quandoo.quandootest.presentation.OnTableSelectListener;

public class TableAdapter extends PagedListAdapter<TableAndCustomer, TableAdapter.ViewHolder> {

    @Nullable
    private OnTableSelectListener onTableSelectListener;

    public TableAdapter(@NonNull final DiffCallback<TableAndCustomer> diffCallback) {
        super(diffCallback);
    }

    public void setOnTableSelectListener(@Nullable final OnTableSelectListener onTableSelectListener) {
        this.onTableSelectListener = onTableSelectListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final TableAndCustomer table = getItem(position);
        if (table != null) {
            holder.bindTo(table, onTableSelectListener);
        } else {
            holder.clear();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        final TextView position;
        @NonNull
        final Button tableButton;
        @NonNull
        final View rootView;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position);
            tableButton = itemView.findViewById(R.id.table_button);
            rootView = itemView;
        }

        private void bindTo(@NonNull final TableAndCustomer tableAndCustomer, @Nullable final OnTableSelectListener listener) {
            final Table table = tableAndCustomer.getTable();
            final Customer customer = tableAndCustomer.getCustomer();
            position.setText(String.valueOf(table.getId()));
            if (table.isReserved()) {
                rootView.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), android.R.color.darker_gray));
            } else {
                rootView.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), android.R.color.white));
            }
            position.setText(rootView.getResources().getString(R.string.item_table_number, String.valueOf(table.getId())));
            final String tableText;
            if (customer != null) {
                tableText = rootView.getResources().getString(R.string.item_table_reserved_by, customer.getFirstName(), customer.getLastName());
            } else {
                if (table.isReserved()) {
                    tableText = rootView.getResources().getString(R.string.item_table_reserved);
                } else {
                    tableText = rootView.getResources().getString(R.string.item_table_free);
                }
            }
            tableButton.setText(tableText);
            tableButton.setOnClickListener(listener == null ? null : v -> listener.onSelect(tableAndCustomer));
        }

        private void clear() {
            position.setText("");
            tableButton.setText("");
            rootView.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), android.R.color.white));
            tableButton.setOnClickListener(null);
        }
    }
}
