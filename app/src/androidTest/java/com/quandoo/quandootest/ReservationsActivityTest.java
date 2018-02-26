package com.quandoo.quandootest;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.quandoo.quandootest.domain.entities.Customer;
import com.quandoo.quandootest.presentation.ui.TableReservationActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class ReservationsActivityTest extends WebServerActivityTest<TableReservationActivity> {

    @Before
    public void before() throws Exception {
        prepare();
    }

    @Test
    public void reservationActivity_customersListIsVisible() throws Exception {
        onView(withId(R.id.customer_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void reservationActivity_scrollToTheEndOfCustomersList() throws Exception {
        final RecyclerView.Adapter adapter = ((RecyclerView) activityRule.getActivity().findViewById(R.id.customer_recycler_view)).getAdapter();
        onView(withId(R.id.customer_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(adapter.getItemCount() - 1));
    }

    @Test
    public void reservationActivity_clickOnCustomersList_shouldChangeTab() throws Exception {
        onView(withId(R.id.customer_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tables_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void reservationActivity_clickOnTabs() throws Exception {
        onView(allOf(withText(R.string.page_tite_tables), isDescendantOfA(withId(R.id.reservation_tabs)))).perform(click());
        onView(withId(R.id.tables_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.customer_recycler_view)).check(matches(not(isDisplayed())));

        onView(allOf(withText(R.string.page_tite_customers), isDescendantOfA(withId(R.id.reservation_tabs)))).perform(click());
        onView(withId(R.id.customer_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.tables_recycler_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void reservationActivity_clickOnCustomersList_clickOnTabList_shouldChangeTableItem() throws Exception {
        final PagedListAdapter adapter = (PagedListAdapter) ((RecyclerView) activityRule.getActivity().findViewById(R.id.customer_recycler_view)).getAdapter();
        final Customer customer = (Customer) adapter.getCurrentList().get(1);

        final String tableText = activityRule.getActivity().getString(R.string.item_table_reserved_by, customer.getFirstName(), customer.getLastName());

        onView(withId(R.id.customer_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.tables_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.tables_recycler_view))
                .check(matches(atPosition(1, hasDescendant(withText(tableText)))));
    }

    @NonNull
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(@NonNull final Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    @Override
    protected Class<TableReservationActivity> getActivityClass() {
        return TableReservationActivity.class;
    }
}
