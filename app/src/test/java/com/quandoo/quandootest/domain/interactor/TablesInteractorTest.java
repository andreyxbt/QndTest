package com.quandoo.quandootest.domain.interactor;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.domain.entities.TableAndCustomer;
import com.quandoo.quandootest.shadows.CompletableShadow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Completable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class TablesInteractorTest {

    @Mock
    private Repository repository;

    private TablesInteractor subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = spy(new TablesInteractor(repository));
    }

    @Test
    public void getTablesAndCustomers_shouldReturnFactoryFromRepository() throws Exception {
        final DataSource.Factory mockedFactory = mock(DataSource.Factory.class);
        when(repository.getTablesAndCustomers()).thenReturn(mockedFactory);
        final DataSource.Factory factory = subject.getTablesAndCustomers();
        verify(repository).getTablesAndCustomers();
        assertEquals(factory, mockedFactory);
    }

    @Test
    public void softUpdate_ifCacheIsNotValid_shouldCallUpdate() throws Exception {
        when(repository.isTablesCacheValid(anyLong())).thenReturn(false);
        when(repository.isTablesCacheValid(anyLong())).thenReturn(false);
        when(repository.updateTables()).thenReturn(Completable.complete());
        subject.softUpdate();
        verify(subject).update();
    }

    @Config(shadows = CompletableShadow.class)
    @Test
    public void softUpdate_ifCacheIsValid_shouldReturnEmptyCompletable() throws Exception {
        final Completable mockedCompletable = mock(Completable.class);
        CompletableShadow.mockCompletable(mockedCompletable);
        when(repository.isTablesCacheValid(anyLong())).thenReturn(true);
        assertEquals(subject.softUpdate(), mockedCompletable);
        verify(subject, never()).update();
    }

    @Test
    public void update_shouldCallUpdateTables_andScheduleCleanDbOnComplete() throws Exception {
        when(repository.updateTables()).thenReturn(Completable.complete());
        subject.update().subscribe();
        verify(repository).updateTables();
        verify(repository).scheduleCleanDb(anyLong());
    }

    @Test
    public void getLastUpdateTime_shouldReturnTablesLastUpdateTime() throws Exception {
        final long testTime = 999;
        when(repository.getTablesLastUpdateTime()).thenReturn(testTime);
        assertEquals(subject.getLastUpdateTime(), testTime);
    }

    @Config(shadows = CompletableShadow.class)
    @Test
    public void updateTableWithNewCustomer_ifTableIsReserved_shouldReturnEmptyCompletable() throws Exception {
        final Completable mockedCompletable = mock(Completable.class);
        CompletableShadow.mockCompletable(mockedCompletable);

        final Table mockedTable = mock(Table.class);
        when(mockedTable.isReserved()).thenReturn(true);
        final TableAndCustomer mockedTandC = mock(TableAndCustomer.class);
        when(mockedTandC.getTable()).thenReturn(mockedTable);

        assertEquals(subject.updateTableWithNewCustomer(mockedTandC, 0L), mockedCompletable);
        verify(repository, never()).updateTableWithNewCustomer(anyLong(), anyBoolean(), anyLong());
    }

    @Config(shadows = CompletableShadow.class)
    @Test
    public void updateTableWithNewCustomer_ifTableHasCustomer_shouldReturnEmptyCompletable() throws Exception {
        final Completable mockedCompletable = mock(Completable.class);
        CompletableShadow.mockCompletable(mockedCompletable);

        final Table mockedTable = mock(Table.class);
        when(mockedTable.isReserved()).thenReturn(false);
        final Customer mockedCustomer = mock(Customer.class);
        final TableAndCustomer mockedTandC = mock(TableAndCustomer.class);
        when(mockedTandC.getTable()).thenReturn(mockedTable);
        when(mockedTandC.getCustomer()).thenReturn(mockedCustomer);

        assertEquals(subject.updateTableWithNewCustomer(mockedTandC, 0L), mockedCompletable);
        verify(repository, never()).updateTableWithNewCustomer(anyLong(), anyBoolean(), anyLong());
    }

    @Config(shadows = CompletableShadow.class)
    @Test
    public void updateTableWithNewCustomer_ifNewCustomerIdIsNull_shouldReturnEmptyCompletable() throws Exception {
        final Completable mockedCompletable = mock(Completable.class);
        CompletableShadow.mockCompletable(mockedCompletable);

        final Table mockedTable = mock(Table.class);
        when(mockedTable.isReserved()).thenReturn(false);
        final TableAndCustomer mockedTandC = mock(TableAndCustomer.class);
        when(mockedTandC.getTable()).thenReturn(mockedTable);

        assertEquals(subject.updateTableWithNewCustomer(mockedTandC, null), mockedCompletable);
        verify(repository, never()).updateTableWithNewCustomer(anyLong(), anyBoolean(), anyLong());
    }

    @Config(shadows = CompletableShadow.class)
    @Test
    public void updateTableWithNewCustomer_shouldCallRepositoryUpdateTableWithNewCustomer() throws Exception {
        final long testTableId = 100L;
        final Long testCustomerId = 200L;

        final Table mockedTable = mock(Table.class);
        when(mockedTable.isReserved()).thenReturn(false);
        when(mockedTable.getId()).thenReturn(testTableId);
        final TableAndCustomer mockedTandC = mock(TableAndCustomer.class);
        when(mockedTandC.getTable()).thenReturn(mockedTable);

        subject.updateTableWithNewCustomer(mockedTandC, testCustomerId);
        verify(repository).updateTableWithNewCustomer(eq(testTableId), eq(true), eq(testCustomerId));
    }
}
