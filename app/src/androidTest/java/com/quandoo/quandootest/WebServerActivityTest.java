package com.quandoo.quandootest;

import android.app.Activity;

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
        server.setDispatcher(new ResponseDispatcher());
        activityRule.launchActivity(null);
    }
}
