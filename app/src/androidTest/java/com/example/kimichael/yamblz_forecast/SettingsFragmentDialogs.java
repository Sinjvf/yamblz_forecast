package com.example.kimichael.yamblz_forecast;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.preference.PreferenceManager;

import com.example.kimichael.yamblz_forecast.presentation.view.MainActivity;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Sinjvf on 30.07.2017.
 * Test dialog behavior in the settings fragment
 */

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentDialogs {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void navigateToSettings() {
        //open settings fragment
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_settings));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        onView(withId(R.id.fragment_settings)).check(matches(isDisplayed()));

    }
    @Test
    public void openAndCloseIntervals(){
        closeDialogsByBackPress(R.id.sync_interval_button);
    }

    @Test
    public void openAndCloseTempUnits(){
        closeDialogsByBackPress(R.id.temp_units_button);
    }


    public void closeDialogsByBackPress(@IdRes int id){
        //open and close dialog
        onView(withId(id)).perform(click());
        pressBack();
        onView(withId(R.id.fragment_settings)).check(matches(isDisplayed()));
    }

    @Test
    //check that after changing temperature unit text in main screen will  show needed tempr unit
    public void tempUnitChange(){
        //select fahrenheit
        onView(withId(R.id.temp_units_button)).perform(click());
        onView(withText(R.string.fahrenheit)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText(R.string.fahrenheit)).inRoot(isDialog()).perform(click());
        onView(withText(R.string.ok)).inRoot(isDialog()).perform(click());

        //goto weather
        pressBack();
        onView(withId(R.id.temp)).check(matches(withText(containsString("F"))));
    }
}
