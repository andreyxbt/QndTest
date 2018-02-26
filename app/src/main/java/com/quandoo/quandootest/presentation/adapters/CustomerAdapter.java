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
import android.widget.TextView;

import com.quandoo.quandootest.R;
import com.quandoo.quandootest.domain.entities.Customer;

public class CustomerAdapter extends PagedListAdapter<Customer, CustomerAdapter.ViewHolder> {

    @Nullable
    private OnCustomerSelectListener customerSelectListener;
    @Nullable
    private Long selectedId;

    public CustomerAdapter(@NonNull final DiffCallback<Customer> diffCallback) {
        super(diffCallback);
    }

    public void setCustomerSelectListener(@Nullable final OnCustomerSelectListener customerSelectListener) {
        this.customerSelectListener = customerSelectListener;
    }

    public void setSelectedId(@Nullable final Long id) {
        final Long oldSelectedId = selectedId;
        selectedId = id;
        if (oldSelectedId != null) {
            final Integer position = getCustomerPosition(oldSelectedId);
            if (position != null) {
                notifyItemChanged(position);
            }
        }
        if (selectedId != null) {
            final Integer position = getCustomerPosition(selectedId);
            if (position != null) {
                notifyItemChanged(position);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Customer customer = getItem(position);
        if (customer != null) {
            final boolean selected = selectedId != null && customer.getId() == selectedId;
            holder.bindTo(customer, position, selected, customerSelectListener);
        } else {
            holder.clear();
        }
    }

    @Nullable
    private Integer getCustomerPosition(@NonNull final Long customerId) {
        final int position = customerId.intValue();
        final Customer customer = getItemCount() > position ? getItem(position) : null;
        if (customer == null) {
            return null;
        }
        return customer.getId() == customerId ? position : null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        final TextView positionView;
        @NonNull
        final TextView firstName;
        @NonNull
        final TextView lastName;
        @NonNull
        final View rootView;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            positionView = itemView.findViewById(R.id.position);
            firstName = itemView.findViewById(R.id.first_name);
            lastName = itemView.findViewById(R.id.last_name);
            rootView = itemView;
        }

        private void bindTo(@NonNull final Customer customer,
                            final int position,
                            final boolean selected,
                            @Nullable final OnCustomerSelectListener customerSelectListener) {
            positionView.setText(String.valueOf(position));
            firstName.setText(customer.getFirstName());
            lastName.setText(customer.getLastName());
            final int bgColorId;
            if (selected) {
                bgColorId = R.color.item_customer_selected_bg;
            } else if (position % 2 == 0) {
                bgColorId = R.color.item_customer_light_gray_bg;
            } else {
                bgColorId = android.R.color.white;
            }
            rootView.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), bgColorId));

            rootView.setOnClickListener(customerSelectListener == null
                    ? null
                    : v -> customerSelectListener.onCustomerSelect(customer.getId())
            );
        }

        private void clear() {
            positionView.setText("");
            firstName.setText("");
            lastName.setText("");
            rootView.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), android.R.color.transparent));
        }
    }
}
