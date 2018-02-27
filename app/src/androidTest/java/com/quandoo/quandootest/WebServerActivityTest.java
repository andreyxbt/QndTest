package com.quandoo.quandootest;

import android.app.Activity;
import android.content.Context;

import com.quandoo.quandootest.domain.interactor.Repository;
import com.quandoo.quandootest.repository.CacheHelper;
import com.quandoo.quandootest.repository.RepositoryImpl;
import com.quandoo.quandootest.repository.TableReservationGetaway;
import com.quandoo.quandootest.repository.alarm.QuandooAlarmManagerImpl;
import com.quandoo.quandootest.repository.database.CachePrefs;
import com.quandoo.quandootest.repository.database.DatabaseFactoryImpl;
import com.quandoo.quandootest.repository.database.QuandooDatabase;
import com.quandoo.quandootest.repository.network.RetrofitTableReservationGetawayFactory;
import com.quandoo.quandootest.utils.EndpointActivityRule;
import com.quandoo.quandootest.utils.ResponseDispatcher;

import org.junit.Rule;
import org.junit.rules.RuleChain;

import okhttp3.mockwebserver.MockWebServer;

public abstract class WebServerActivityTest<T extends Activity> {

    final MockWebServer server = new MockWebServer();
    final EndpointActivityRule<T> activityRule = new EndpointActivityRule<>(getActivityClass(), server);
    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(server).around(activityRule);

    protected abstract Class<T> getActivityClass();

    public void prepare() {
        activityRule.launchActivity(null);
        server.setDispatcher(new ResponseDispatcher());
        final Context context = activityRule.getActivity();

        final CacheHelper tableCacheHelper = new CacheHelper(
                context.getString(R.string.cache_helper_tables_key),
                CachePrefs.from(context),
                1000L);

        final CacheHelper customerCacheHelper = new CacheHelper(
                context.getString(R.string.cache_helper_customers_key),
                CachePrefs.from(context),
                1000L);

        final QuandooDatabase quandooDatabase =
                new DatabaseFactoryImpl(context, context.getString(R.string.db_name))
                        .getDatabase();

        final TableReservationGetaway tableReservationGetaway =
                new RetrofitTableReservationGetawayFactory(server.url("/").toString())
                        .getTimetableGataway();

        final Repository repository = new RepositoryImpl(
                tableReservationGetaway,
                quandooDatabase,
                tableCacheHelper,
                customerCacheHelper,
                QuandooAlarmManagerImpl.from(context));

        QuandooApp.getInstance().getRepositoryProvider().inject(repository);
    }
}
