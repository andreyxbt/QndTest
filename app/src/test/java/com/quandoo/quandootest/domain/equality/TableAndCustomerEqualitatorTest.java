package com.quandoo.quandootest.domain.equality;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.domain.entities.Table;
import com.quandoo.quandootest.domain.entities.TableAndCustomer;
import com.quandoo.quandootest.repository.database.dao.TableAndCustomerDBEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class TableAndCustomerEqualitatorTest {

    @Mock
    private TableEqualitator tableEqualitator;
    @Mock
    private CustomerEqualitator customerEqualitator;

    private TableAndCustomerEqualitator subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new TableAndCustomerEqualitator(tableEqualitator, customerEqualitator);
    }

    @Test
    public void isEquals_shouldReturnTrue_forSameObject() throws Exception {
        final TableAndCustomer left = mock(TableAndCustomer.class);
        assertTrue(subject.isEquals(left, left));
    }

    @Test
    public void isEquals_shouldReturnTrue_isAllFieldsAreTheSame() throws Exception {
        final Table testTable = mock(Table.class);
        final Customer testCustomer = mock(Customer.class);
        final TableAndCustomer left = setUpTableAndCustomer(testTable, testCustomer);
        final TableAndCustomer right = setUpTableAndCustomer(testTable, testCustomer);
        when(tableEqualitator.isEquals(any(Table.class), any(Table.class))).thenReturn(true);
        when(customerEqualitator.isEquals(any(Customer.class), any(Customer.class))).thenReturn(true);
        assertTrue(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifLeftIsNullAndRightIsNot() throws Exception {
        final TableAndCustomer right = mock(TableAndCustomer.class);
        assertFalse(subject.isEquals(null, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifRightIsNullAndLeftIsNot() throws Exception {
        final TableAndCustomer left = mock(TableAndCustomer.class);
        assertFalse(subject.isEquals(left, null));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifClassesAreNotTheSame() throws Exception {
        final TableAndCustomer left = mock(TableAndCustomer.class);
        final TableAndCustomer right = mock(TableAndCustomerDBEntity.class);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifTablesNotTheSame() throws Exception {
        final Table testTable = mock(Table.class);
        final Customer testCustomer = mock(Customer.class);
        final TableAndCustomer left = setUpTableAndCustomer(testTable, testCustomer);
        final TableAndCustomer right = setUpTableAndCustomer(testTable, testCustomer);
        when(tableEqualitator.isEquals(any(Table.class), any(Table.class))).thenReturn(false);
        when(customerEqualitator.isEquals(any(Customer.class), any(Customer.class))).thenReturn(true);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_oneIsCustomersNotTheSame() throws Exception {
        final Table testTable = mock(Table.class);
        final Customer testCustomer = mock(Customer.class);
        final TableAndCustomer left = setUpTableAndCustomer(testTable, testCustomer);
        final TableAndCustomer right = setUpTableAndCustomer(testTable, testCustomer);
        when(tableEqualitator.isEquals(any(Table.class), any(Table.class))).thenReturn(true);
        when(customerEqualitator.isEquals(any(Customer.class), any(Customer.class))).thenReturn(false);
        assertFalse(subject.isEquals(left, right));
    }

    @NonNull
    private TableAndCustomer setUpTableAndCustomer(
            @NonNull final Table table,
            @Nullable final Customer customer) {
        final TableAndCustomer tableAndCustomer = mock(TableAndCustomer.class);
        when(tableAndCustomer.getTable()).thenReturn(table);
        when(tableAndCustomer.getCustomer()).thenReturn(customer);
        return tableAndCustomer;
    }
}
