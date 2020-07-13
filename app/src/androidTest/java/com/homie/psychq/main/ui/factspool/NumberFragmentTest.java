package com.homie.psychq.main.ui.factspool;

import androidx.fragment.app.testing.FragmentScenario;

import com.homie.psychq.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.*;

public class NumberFragmentTest {

    @Before
    public void setUp() throws Exception {
    }


    String input = "51";
    String expected = "51 is the atomic number of antimony.";

    @Test
    public void testUserInputScenario() throws InterruptedException {
        //launch the fragment
        FragmentScenario.launchInContainer(NumberFragment.class);
        //clear the input editText, enter the input
        onView(withId(R.id.year_edt)).perform(replaceText(input));
        //press enter
        onView(withId(R.id.go_btn)).perform(click());
        //wait for the response from api
        Thread.sleep(2000);
        //check the textView for output
        onView(withId(R.id.fact_tv_num))
                .check(matches(withText(expected.toLowerCase())));



    }

    @After
    public void tearDown() throws Exception {
    }
}