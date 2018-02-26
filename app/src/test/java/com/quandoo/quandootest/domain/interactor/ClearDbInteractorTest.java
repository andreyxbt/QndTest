package com.quandoo.quandootest.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.Completable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class ClearDbInteractorTest {

    @Mock
    private Repository repository;

    private ClearDbInteractor subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ClearDbInteractor(repository);
        when(repository.clearCustomers()).thenReturn(Completable.complete());
        when(repository.clearTables()).thenReturn(Completable.complete());
    }

    @Test
    public void clearDb_shouldCall_clearCustomers_clearTables() throws Exception {
        subject.clearDb().subscribe();
        verify(repository).clearCustomers();
        verify(repository).clearTables();
    }
}
