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
import com.quandoo.quandootest.domain.entities.TableAndCustomer;
import com.quandoo.quandootest.domain.interactor.TablesInteractor;

import io.reactivex.disposables.Disposable;

public class TablesViewModel extends ViewModel {

    @NonNull
    private final TablesInteractor tablesInteractor;
    @NonNull
    private final MutableLiveData<ConnectionStatus> connectionStatus;
    @NonNull
    private final MutableLiveData<Boolean> updating;
    @Nullable
    private Disposable updateDisposable;
    @Nullable
    private Disposable softUpdateDisposable;
    @Nullable
    private LiveData<PagedList<TableAndCustomer>> tables;

    public TablesViewModel() {
        updating = new MutableLiveData<>();
        tablesInteractor = new TablesInteractor(QuandooApp.getInstance().getRepository());
        connectionStatus = new MutableLiveData<>();
        connectionStatus.setValue(new ConnectionStatus(String.valueOf(tablesInteractor.getLastUpdateTime())));
    }

    private static void dispose(@Nullable final Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @NonNull
    public LiveData<PagedList<TableAndCustomer>> getTables() {
        if (tables == null) {
            final DataSource.Factory<Integer, TableAndCustomer> factory = tablesInteractor.getTablesAndCustomers();
            tables = new LivePagedListBuilder<>(factory, 20).build();
        }
        return tables;
    }

    @NonNull
    public LiveData<Boolean> getUpdating() {
        return updating;
    }

    @NonNull
    public LiveData<ConnectionStatus> getConnectionStatus() {
        return connectionStatus;
    }

    public void updateTablesSoft() {
        if (softUpdateDisposable == null || softUpdateDisposable.isDisposed()) {
            updating.postValue(true);
            softUpdateDisposable = tablesInteractor
                    .softUpdate()
                    .doFinally(() -> {
                        dispose(softUpdateDisposable);
                        updating.postValue(false);
                    })
                    .subscribe(
                            () -> connectionStatus.postValue(
                                    new ConnectionStatus("updated: " + DateHelper.format(tablesInteractor.getLastUpdateTime()))
                            ),
                            throwable -> connectionStatus.postValue(new ConnectionStatus("Error", throwable))
                    );
        }
    }

    public void updateTableWithNewCustomer(@NonNull final TableAndCustomer tableAndCustomer, @Nullable final Long customerId) {
        tablesInteractor.updateTableWithNewCustomer(tableAndCustomer, customerId);
    }

    public void updateTables() {
        if (updateDisposable == null || updateDisposable.isDisposed()) {
            updating.postValue(true);
            updateDisposable = tablesInteractor
                    .update()
                    .doFinally(() -> {
                        dispose(softUpdateDisposable);
                        updating.postValue(false);
                    })
                    .subscribe(
                            () -> connectionStatus.postValue(
                                    new ConnectionStatus("updated: " + DateHelper.format(tablesInteractor.getLastUpdateTime()))
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
