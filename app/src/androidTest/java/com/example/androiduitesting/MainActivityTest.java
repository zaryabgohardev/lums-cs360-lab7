package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddCity(){
// Click on Add City button
        onView(withId(R.id.button_add)).perform(click());
// Type "Edmonton" in the editText
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText("Edmonton"));
// Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());
// Check if text "Edmonton" is matched with any of the textdisplayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity(){
// Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
//Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText("Vancouver"));
                onView(withId(R.id.button_confirm)).perform(click());
//Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }
    @Test
    public void testListView(){
// Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText("Edmonton"));
                onView(withId(R.id.button_confirm)).perform(click());
// Check if in the Adapter view (given id of that adapter view), there is a data
// (which is an instance of String) located at position zero.
// If this data matches the text we provided then Voila! Our test should pass
// You can also use anything() in place of is(instanceOf(String.class))
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list
        )).atPosition(0).check(matches((withText("Edmonton"))));
    }
    @Test
    public void testActivitySwitch() {
        // Add a city to the list first
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in the list
        onData(anything())
                .inAdapterView(withId(R.id.city_list)).atPosition(0)
                .perform(click());

        // Verify the ShowActivity's TextView is displayed (means we switched to ShowActivity)
        onView(withId(R.id.textView_cityName)).check(matches(isDisplayed()));
    }

    @Test
    public void testCityNameIsConsistent() {
        // Add a city to the list first
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText("Montreal"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in the list
        onData(anything())
                .inAdapterView(withId(R.id.city_list)).atPosition(0)
                .perform(click());

        // Verify that the city name displayed in ShowActivity matches the expected string
        onView(withId(R.id.textView_cityName)).check(matches(withText("Montreal")));
    }

    @Test
    public void testBackButton() {
        // Add a city to the list first
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.replaceText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in the list
        onData(anything())
                .inAdapterView(withId(R.id.city_list)).atPosition(0)
                .perform(click());

        // Click the back button on ShowActivity
        onView(withId(R.id.button_back)).perform(click());

        // Verify we are back on MainActivity by checking visibility of its elements
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
