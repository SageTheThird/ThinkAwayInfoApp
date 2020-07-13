package com.homie.psychq.main.ui;

import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.rule.ActivityTestRule;
import com.homie.psychq.R;
import com.homie.psychq.main.ui.feeds.FeedsFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class); //this will enable launching of activity

    public MainActivity mainActivity = null;
    @Before
    public void setUp() throws Exception {

        mainActivity = mainActivityActivityTestRule.getActivity(); //getting the context


    }

    @Test
    public void testLaunchMainActivity(){
        //if view is not null, activity launch is successful
        assertNotNull(mainActivity.findViewById(R.id.app_title_tv));//junit

        //For clicks or anything related with UI, we use expresso


        FragmentScenario.launchInContainer(FeedsFragment.class);//launches fragment
        onView(withId(R.id.refresh_ab)).check(matches(isDisplayed())); //checks if a view in that fragment is displayed


        onView(withId(R.id.refresh_ab)).perform(click());//clicks refresh button at bottom right
        onView(withId(R.id.page_indicatorAb)).perform(click());//clicks Page Indicator button at bottom right




    }

    @After
    public void tearDown() throws Exception {

        mainActivity = null; //clean up

    }
}