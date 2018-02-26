package com.quandoo.quandootest.domain.equality;

import android.support.annotation.NonNull;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.repository.network.transport.CustomerApiEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class CustomerEqualitatorTest {

    private CustomerEqualitator subject;

    @Before
    public void setUp() {
        subject = new CustomerEqualitator();
    }

    @Test
    public void isEquals_shouldReturnTrue_forSameObject() throws Exception {
        final Customer left = mock(Customer.class);
        assertTrue(subject.isEquals(left, left));
    }

    @Test
    public void isEquals_shouldReturnTrue_isAllFieldsAreTheSame() throws Exception {
        final long testId = 100L;
        final String testFirstName = "test_first_name";
        final String testSecondName = "test_second_name";
        final Customer left = setUpCustomer(testId, testFirstName, testSecondName);
        final Customer right = setUpCustomer(testId, testFirstName, testSecondName);

        assertTrue(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifLeftIsNullAndRightIsNot() throws Exception {
        final Customer right = mock(Customer.class);
        assertFalse(subject.isEquals(null, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifRightIsNullAndLeftIsNot() throws Exception {
        final Customer left = mock(Customer.class);
        assertFalse(subject.isEquals(left, null));
    }

    @Test
    public void isEquals_shouldReturnFalse_ifClassesAreNotTheSame() throws Exception {
        final Customer left = mock(Customer.class);
        final Customer right = mock(CustomerApiEntity.class);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_IdsAreNotTheSame() throws Exception {
        final long testId = 100L;
        final String testFirstName = "test_first_name";
        final String testSecondName = "test_second_name";
        final Customer left = setUpCustomer(testId, testFirstName, testSecondName);
        final Customer right = setUpCustomer(1L, testFirstName, testSecondName);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_FirstNamesAreNotTheSame() throws Exception {
        final long testId = 100L;
        final String testFirstName = "test_first_name";
        final String testSecondName = "test_second_name";
        final Customer left = setUpCustomer(testId, testFirstName, testSecondName);
        final Customer right = setUpCustomer(testId, "", testSecondName);
        assertFalse(subject.isEquals(left, right));
    }

    @Test
    public void isEquals_shouldReturnFalse_LastNamesAreNotTheSame() throws Exception {
        final long testId = 100L;
        final String testFirstName = "test_first_name";
        final String testSecondName = "test_second_name";
        final Customer left = setUpCustomer(testId, testFirstName, testSecondName);
        final Customer right = setUpCustomer(testId, testFirstName, "");
        assertFalse(subject.isEquals(left, right));
    }

    @NonNull
    private Customer setUpCustomer(
            final long id,
            @NonNull final String firstName,
            @NonNull final String secondName) {
        final Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(id);
        when(customer.getFirstName()).thenReturn(firstName);
        when(customer.getLastName()).thenReturn(secondName);
        return customer;
    }
}
