package com.example.kimichael.yamblz_forecast;

import android.support.annotation.IdRes;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.kimichael.yamblz_forecast.presentation.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Sinjvf on 30.07.2017.
 * testing phoneNavigation between fragments
 */

@RunWith(AndroidJUnit4.class)
public class FragmentsNavigation {
    boolean isTablet;
    String settings;
    String about;
    String add;
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void init(){
        isTablet = rule.getActivity().isTablet();
        settings = rule.getActivity().getString(R.string.action_settings);
        about = rule.getActivity().getString(R.string.action_about);
        add = rule.getActivity().getString(R.string.add_city);

    }
    @Test
    public void simpleNavigateToAbout(){
        if(!isTablet){
            phoneNavigation(R.id.nav_about, R.id.fragment_about);
        }else{
            onView(withId(R.id.recycler_view)).perform(RecyclerViewActions
                    .scrollTo(hasDescendant(withText(about))));
          //  onView(withId(R.id.fragment_about)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void simpleNavigateToSettings(){
        if(!isTablet){
            phoneNavigation(R.id.nav_settings, R.id.fragment_settings);
        }else{

            onView(withId(R.id.recycler_view)).perform(RecyclerViewActions
                    .scrollTo(hasDescendant(withText(settings))));
        /*    onView(withId(R.id.fragment_about)).check(matches(isDisplayed()));*/
        }
    }

    @Test
    public void simpleNavigateToForecast(){
        if(isTablet){
            //right only if we have cities in favorites
            tabletNavigation(0, R.id.fragment_weather);
        }
        else {
            phoneNavigation(R.id.nav_weather, R.id.phone_weather);
        }
    }


    @Test
    public void complexNavigateToAbout(){
        if(!isTablet) {
            simpleNavigateToAbout();
            phoneBackPress();
        }
    }

    @Test
    public void complexNavigateToSettings(){
        if(!isTablet) {
            simpleNavigateToSettings();
            phoneBackPress();
        }
    }

    public void phoneNavigation(@IdRes int navigationId, @IdRes int rootViewId){
        //open drawer
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        //click on menu
        onView(withId(R.id.nav_view)).perform(navigateTo(navigationId));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        onView(withId(rootViewId)).check(matches(isDisplayed()));
    }

    public void tabletNavigation(int navigationPos, @IdRes int rootViewId){
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(navigationPos,click()));
        onView(withId(rootViewId)).check(matches(isDisplayed()));
    }
    public void phoneBackPress(){
        pressBack();
        onView(withId(R.id.phone_weather)).check(matches(isDisplayed()));
    }

}
