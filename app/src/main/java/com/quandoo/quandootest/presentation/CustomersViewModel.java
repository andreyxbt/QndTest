package com.quandoo.quandootest.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.QuandooApp;
import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.domain.interactor.CustomerInteractor;

import io.reactivex.disposables.Disposable;

public class CustomersViewModel extends ViewModel {

    @NonNull
    private final CustomerInteractor customerInteractor;
    @NonNull
    private final MutableLiveData<ConnectionStatus> connectionStatus;
    @NonNull
    private final MutableLiveData<Boolean> updating;
    @Nullable
    private Disposable updateDisposable;
    @Nullable
    private Disposable softUpdateDisposable;
    @Nullable
    private LiveData<PagedList<Customer>> customers;

    public CustomersViewModel() {
        updating = new MutableLiveData<>();
        customerInteractor = new CustomerInteractor(QuandooApp.getInstance().getRepository());
        connectionStatus = new MutableLiveData<>();
        connectionStatus.setValue(new ConnectionStatus(String.valueOf(customerInteractor.getLastUpdateTime())));
    }

    private static void dispose(@Nullable final Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @NonNull
    public LiveData<PagedList<Customer>> getCustomers() {
        if (customers == null) {
            final DataSource.Factory<Integer, Customer> factory = customerInteractor.getCustomers();
            customers = new LivePagedListBuilder<>(factory, 20).build();
        }
        return customers;
    }

    @NonNull
    public LiveData<Boolean> getUpdating() {
        return updating;
    }

    @NonNull
    public LiveData<ConnectionStatus> getConnectionStatus() {
        return connectionStatus;
    }

    public void updateCustomersSoft() {
        if (softUpdateDisposable == null || softUpdateDisposable.isDisposed()) {
            updating.postValue(true);
            softUpdateDisposable = customerInteractor
                    .softUpdate()
                    .doFinally(() -> {
                        dispose(softUpdateDisposable);
                        updating.postValue(false);
                    })
                    .subscribe(
                            () -> connectionStatus.postValue(
                                    new ConnectionStatus("updated: " + DateHelper.format(customerInteractor.getLastUpdateTime()))
                            ),
                            throwable -> connectionStatus.postValue(new ConnectionStatus("Error", throwable))
                    );
        }
    }

    public void updateCustomers() {
        if (updateDisposable == null || updateDisposable.isDisposed()) {
            updating.postValue(true);
            updateDisposable = customerInteractor
                    .update()
                    .doFinally(() -> {
                        dispose(softUpdateDisposable);
                        updating.postValue(false);
                    })
                    .subscribe(
                            () -> connectionStatus.postValue(
                                    new ConnectionStatus("updated: " + DateHelper.format(customerInteractor.getLastUpdateTime()))
                            ),
                            throwable -> connectionStatus.postValue(new ConnectionStatus("Error", throwable))
                    );
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        dispose(updateDisposable);
        dispose(softUpdateDisposable);
    }
}
