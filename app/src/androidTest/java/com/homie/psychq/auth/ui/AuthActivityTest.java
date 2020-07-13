package com.homie.psychq.auth.ui;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.homie.psychq.R;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class AuthActivityTest {

    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule<>(AuthActivity.class);
    AuthActivity mActivity = null;

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityRule.getActivity();//gets reference of auth activity
    }


    String email = "ksajid505@gmail.com";
    String password = "Merccury2019";
    @Test
    public void testSimpleLogin() throws InterruptedException {
        /*Clear Cache & Storage before running*/
        Thread.sleep(2000);
        //first click signIn button
        onView(withId(R.id.sign_in_tv)).perform(click());
        //enter email and password in ets
        onView(withId(R.id.email)).perform(replaceText(email));
        onView(withId(R.id.password)).perform(replaceText(password));
        //press signIn again
        onView(withId(R.id.sign_in_tv)).perform(click());
        onView(isRoot()).perform(waitFor(7000));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
        mActivityRule = null;
    }



    /*Custom Matcher for waiting without freezing thread or idling resource*/
    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
}