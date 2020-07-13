package com.homie.psychq.main.ui.factspool;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.testing.FragmentScenario;

import com.homie.psychq.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class YearFragmentTest {


    String input = "101";

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testInputYears() throws InterruptedException {
        //launch the fragment
        FragmentScenario.launchInContainer(YearFragment.class);
        //replace text in editText with desired input
        onView(withId(R.id.year_edt)).perform(replaceText(input));
        //press enter
        onView(withId(R.id.go_btn)).perform(click());
        //wait 2 seconds for response
        Thread.sleep(2000);
        //check if the textView text is null or not
        // passes if the textView does not match the empty string
        onView(withId(R.id.fact_tv_year)).check(matches(not(withText(""))));

    }


    String allInvalidInputResponses = "is a boring number, is an uninteresting number, is an unremarkable number, is a number for which we're missing a fact (submit one at numbersapi at google mail!)., is the year that we do not know what happened., is the year that nothing interesting came to pass.";
    String invalidInput = "9112";
    @Test
    public void testInvalidInputYears() throws InterruptedException {
        //launch the fragment
        FragmentScenario.launchInContainer(YearFragment.class);
        //replace text in editText with desired input
        onView(withId(R.id.year_edt)).perform(replaceText(invalidInput));
        //press enter
        onView(withId(R.id.go_btn)).perform(click());
        //wait 2 seconds for response
        Thread.sleep(2000);
        //check if the textView text is null or not
        // passes if the textView does not match the empty string
        onView(withId(R.id.fact_tv_year))
                .check(matches(withText(containsString(allInvalidInputResponses))));

    }


    @After
    public void tearDown() throws Exception {
    }



    /*Custom Matcher for getting the value of textView or EditText with our own conditions*/
    Matcher<View> hasValueEqualTo(final String content) {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Has EditText/TextView the value:  " + content);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView) && !(view instanceof EditText)) {
                    return false;
                }
                if (view != null) {
                    String text;
                    if (view instanceof TextView) {
                        text = ((TextView) view).getText().toString().replace(" ","");
                    } else {
                        text = ((EditText) view).getText().toString();
                    }

                    return (text.equalsIgnoreCase(content));
                }
                return false;
            }
        };
    }
}