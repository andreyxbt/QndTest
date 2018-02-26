package com.quandoo.quandootest.domain.equality;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.repository.network.transport.TableApiEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class TableEqualitatorTest {

    private TableEqualitator subject;

    @Before
    public void setUp() {
        subject = new TableEqualitator();
    }

    @Test
    public void isEquals_shouldReturnTrue_forSameObject() throws Exception {
        final Table left = mock(Table.class);
        assertTrue(subject.isEquals(left, left));
    }

    @Test
    public void isEquals_shouldReturnTrue_isAllFieldsAreTheSame() throws Exception {
        final long testId = 100L;
        final boolean testIsReserved = true;
        final Long testTableId = 200L;
        final Table left = setUpTable(testId, testIsReserved, testTableId);
        final Table right = setUpTable(testId, testIsReserved, testTableId);

        assertTrue(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifLeftIsNullAndRightIsNot() throws Exception {
        final Table right = mock(Table.class);
        assertFalse(subject.isEquals(null, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifRightIsNullAndLeftIsNot() throws Exception {
        final Table left = mock(Table.class);
        assertFalse(subject.isEquals(left, null));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifClassesAreNotTheSame() throws Exception {
        final Table left = mock(Table.class);
        final Table right = mock(TableApiEntity.class);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_idsAreNotTheSame() throws Exception {
        final long testId = 100L;
        final boolean testIsReserved = true;
        final Long testCustomerId = 200L;
        final Table left = setUpTable(testId, testIsReserved, testCustomerId);
        final Table right = setUpTable(0L, testIsReserved, testCustomerId);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_oneIsNotReserved() throws Exception {
        final long testId = 100L;
        final boolean testIsReserved = true;
        final Long testCustomerId = 200L;
        final Table left = setUpTable(testId, testIsReserved, testCustomerId);
        final Table right = setUpTable(testId, false, testCustomerId);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_customerIdsAreNotTheSame() throws Exception {
        final long testId = 100L;
        final boolean testIsReserved = true;
        final Long testCustomerId = 200L;
        final Table left = setUpTable(testId, testIsReserved, testCustomerId);
        final Table right = setUpTable(testId, testIsReserved, 0L);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_oneCustomerIdIsNull() throws Exception {
        final long testId = 100L;
        final boolean testIsReserved = true;
        final Long testCustomerId = 200L;
        final Table left = setUpTable(testId, testIsReserved, null);
        final Table right = setUpTable(testId, testIsReserved, testCustomerId);
        assertFalse(subject.isEquals(left, right));
    }

    @NonNull
    private Table setUpTable(
            final long id,
            final boolean isReserved,
            @Nullable final Long customerId) {
        final Table table = mock(Table.class);
        when(table.getId()).thenReturn(id);
        when(table.isReserved()).thenReturn(isReserved);
        when(table.getCustomerId()).thenReturn(customerId);
        return table;
    }
}
