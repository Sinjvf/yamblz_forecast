package com.example.kimichael.yamblz_forecast.presentation.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.presentation.view.about.AboutFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.SettingsFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FRAGMENT_STATUS_WEATHER, FRAGMENT_STATUS_SETTINGS, FRAGMENT_STATUS_ABOUT, FRAGMENT_STATUS_NOT_CHOSEN})
    private @interface ChosenFragmentStatus {}
    public static final int FRAGMENT_STATUS_NOT_CHOSEN = -1;
    public static final int FRAGMENT_STATUS_WEATHER = 0;
    public static final int FRAGMENT_STATUS_SETTINGS = 1;
    public static final int FRAGMENT_STATUS_ABOUT = 2;

    public static final String TAG_SETTINGS = "settings";
    public static final String TAG_FORECAST = "forecast";
    public static final String TAG_ABOUT = "about";



    private @MainActivity.ChosenFragmentStatus int chosenFragment;
    private boolean isHomeAsUp = false;
    private DrawerArrowDrawable homeDrawable;
    DrawerLayout drawer;

    @Override
    @SuppressWarnings("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homeDrawable = new DrawerArrowDrawable(toolbar.getContext());
        toolbar.setNavigationIcon(homeDrawable);
        toolbar.setNavigationOnClickListener(view -> {
            if (drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
            } else if (isHomeAsUp){
                onBackPressed();
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        App.getInstance().getForecastComponent();

        if (savedInstanceState != null)
            changeFragment(savedInstanceState.getInt(getString(R.string.key_chosen_fragment), FRAGMENT_STATUS_WEATHER));
        else {
            chosenFragment = FRAGMENT_STATUS_NOT_CHOSEN;
            changeFragment(FRAGMENT_STATUS_WEATHER);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.key_chosen_fragment), chosenFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeFragment(chosenFragment);
    }


    @Override
    protected void onDestroy() {
        App.getInstance().releaseForecastComponent();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (chosenFragment != FRAGMENT_STATUS_WEATHER) {
            // We don't need to close activity
            changeFragment(FRAGMENT_STATUS_WEATHER);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_settings:
                changeFragment(FRAGMENT_STATUS_SETTINGS);
                break;
            case R.id.nav_about:
                changeFragment(FRAGMENT_STATUS_ABOUT);
                break;
            case R.id.nav_weather:
            default:
                changeFragment(FRAGMENT_STATUS_WEATHER);
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(@ChosenFragmentStatus int chosenFragment) {

        if (this.chosenFragment == chosenFragment)
            return;

        Fragment fragment;
        this.chosenFragment = chosenFragment;
        String tag ;
        switch (chosenFragment) {
            case FRAGMENT_STATUS_SETTINGS:
                fragment = SettingsFragment.newInstance();
                tag = TAG_SETTINGS;
                setHomeAsUp(true);
                break;
            case FRAGMENT_STATUS_ABOUT:
                fragment = AboutFragment.newInstance();
                tag = TAG_ABOUT;
                setHomeAsUp(true);
                break;
            case FRAGMENT_STATUS_WEATHER:
            case FRAGMENT_STATUS_NOT_CHOSEN:
            default:
                fragment = ForecastFragment.newInstance();
                tag = TAG_FORECAST;
                setHomeAsUp(false);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, fragment, tag).commit();

    }

    private void setHomeAsUp(boolean isHomeAsUp){
        if (this.isHomeAsUp != isHomeAsUp) {
            this.isHomeAsUp = isHomeAsUp;
            ValueAnimator anim = isHomeAsUp ? ValueAnimator.ofFloat(0, 1) : ValueAnimator.ofFloat(1, 0);
            anim.addUpdateListener(valueAnimator -> {
                float slideOffset = (float) valueAnimator.getAnimatedValue();
                homeDrawable.setProgress(slideOffset);
            });
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(400);
            anim.start();
        }
    }
}
