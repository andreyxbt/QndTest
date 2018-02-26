package com.quandoo.quandootest.domain.interactor;

import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.shadows.CompletableShadow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;

import static com.quandoo.quandootest.QuandooApp.DB_CLEAN_ALARM_SCHEDULE_MILLIS;
import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class CustomerInteractorTest {
    @Mock
    private Repository repository;

    private CustomerInteractor subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = spy(new CustomerInteractor(repository));
    }

    @Test
    public void getCustomers_shouldReturnCustomersFactoryFromRepository() throws Exception {
        final DataSource.Factory mockedFactory = mock(DataSource.Factory.class);
        when(repository.getCustomers()).thenReturn(mockedFactory);
        final DataSource.Factory factory = subject.getCustomers();
        verify(repository).getCustomers();
        assertEquals(factory, mockedFactory);
    }

    @Test
    public void softUpdate_ifCacheIsNotValid_shouldCallUpdate() throws Exception {
        when(repository.isCustomersCacheValid(anyLong())).thenReturn(false);
        when(repository.updateCustomers()).thenReturn(Completable.complete());
        subject.softUpdate();
        verify(subject).update();
    }

    @Config(shadows = CompletableShadow.class)
    @Test
    public void softUpdate_ifCacheIsValid_shouldReturnEmptyCompletable() throws Exception {
        final Completable mockedCompletable = mock(Completable.class);
        CompletableShadow.mockCompletable(mockedCompletable);
        when(repository.isCustomersCacheValid(anyLong())).thenReturn(true);
        assertEquals(subject.softUpdate(), mockedCompletable);
        verify(subject, never()).update();
    }

    @Test
    public void update_shouldCallUpdateCustomers_andScheduleCleanDbOnComplete() throws Exception {
        when(repository.updateCustomers()).thenReturn(Completable.complete());
        subject.update().subscribe();
        verify(repository).updateCustomers();
        verify(repository).scheduleCleanDb(anyLong());
    }

    @Test
    public void getLastUpdateTime_shouldReturnCustomersLastUpdateTime() throws Exception {
        final long testTime = 999;
        when(repository.getCustomersLastUpdateTime()).thenReturn(testTime);
        assertEquals(subject.getLastUpdateTime(), testTime);
    }
}
