package com.example.kimichael.yamblz_forecast;

import android.support.annotation.IdRes;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kimichael.yamblz_forecast.presentation.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Sinjvf on 30.07.2017.
 * testing navigation between fragments
 */

@RunWith(AndroidJUnit4.class)
public class FragmentsNavigation {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void simpleNavigateToAbout(){
        navigation(R.id.nav_about, R.id.fragment_about);
    }

    @Test
    public void simpleNavigateToSettings(){
        navigation(R.id.nav_settings, R.id.fragment_settings);
    }

    @Test
    public void simpleNavigateToForecast(){
        navigation(R.id.nav_weather, R.id.fragment_weather);
        onView(withId(R.id.action_refresh)).check(matches(isDisplayed()));
    }


    @Test
    public void complexNavigateToAbout(){
        simpleNavigateToAbout();
        simpleNavigateToAbout();
        simpleNavigateToAbout();
        backPress();
    }

    @Test
    public void complexNavigateToSettings(){
        simpleNavigateToSettings();
        simpleNavigateToSettings();
        simpleNavigateToSettings();
        backPress();
    }

    public void navigation(@IdRes int navigationId, @IdRes int rootViewId){
        //open drawer
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        //click on menu
        onView(withId(R.id.nav_view)).perform(navigateTo(navigationId));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        onView(withId(rootViewId)).check(matches(isDisplayed()));
    }
    public void backPress(){
        pressBack();
        onView(withId(R.id.fragment_weather)).check(matches(isDisplayed()));
    }

}
