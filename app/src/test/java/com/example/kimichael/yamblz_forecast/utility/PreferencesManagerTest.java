package com.example.kimichael.yamblz_forecast.utility;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.kimichael.yamblz_forecast.utils.PlaceData;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Sinjvf on 30.07.2017.
 * Preferenses Manager tests
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PreferencesManagerTest {


    private SharedPreferences sharedPreferences;
    private PreferencesManager preferencesManager;


    @Before
    public void setup() {
        String SP_NAME = RuntimeEnvironment.application.getPackageName() + "_preferences";
        sharedPreferences = RuntimeEnvironment.
                application.getSharedPreferences(SP_NAME, 0);
        sharedPreferences.edit().clear().apply();
        preferencesManager = new PreferencesManager(sharedPreferences);
    }

    @Test
    public void preferencesInitialization() {
        assertNotNull(sharedPreferences);
        assertNotNull(preferencesManager);
    }

    @Test
    public void defaultUpdateInterval() {
        String DEFAULT_UPDATE_INTERVAL = "3600";
        assertEquals(DEFAULT_UPDATE_INTERVAL, preferencesManager.getInterval());
    }

    @Test
    public void defaultPlace() {
        String DEFAULT_PLACE_NAME = "Moscow";
        assertEquals(DEFAULT_PLACE_NAME, preferencesManager.getPlace().getName());
    }

    @Test
    public void defaultTempUnit() {
        assertEquals(true, preferencesManager.isTempCelsius());
    }

    @Test
    public void updateInterval() {
        String UPDATE_INTERVAL = "7200";
        preferencesManager.saveInterval(UPDATE_INTERVAL);
        assertEquals(UPDATE_INTERVAL, preferencesManager.getInterval());
    }

    @Test
    public void place() {
        String NAME = "Norilsk";
        double LAT = 69.20;
        double LON = 88.13;
        PlaceData data = new PlaceData(NAME, LAT, LON);
        preferencesManager.savePlace(createPlace(data));
        assertEquals(NAME, preferencesManager.getPlace().getName());
        assertEquals(LAT, preferencesManager.getPlace().getLatitude(), 0);
        assertEquals(LON, preferencesManager.getPlace().getLongitude(), 0);
    }

    @Test
    public void tempUnit() {
        int TEMP_UNIT = 1;
        preferencesManager.saveTempUnit(TEMP_UNIT);
        assertEquals(false, preferencesManager.isTempCelsius());
    }



    private Place createPlace(PlaceData data) {
        return  new Place() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public List<Integer> getPlaceTypes() {
                return null;
            }

            @Override
            public CharSequence getAddress() {
                return null;
            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public CharSequence getName() {
                return data.getName();
            }

            @Override
            public LatLng getLatLng() {
                return new LatLng(data.getLatitude(), data.getLongitude());
            }

            @Override
            public LatLngBounds getViewport() {
                return null;
            }

            @Override
            public Uri getWebsiteUri() {
                return null;
            }

            @Override
            public CharSequence getPhoneNumber() {
                return null;
            }

            @Override
            public float getRating() {
                return 0;
            }

            @Override
            public int getPriceLevel() {
                return 0;
            }

            @Override
            public CharSequence getAttributions() {
                return null;
            }

            @Override
            public Place freeze() {
                return null;
            }

            @Override
            public boolean isDataValid() {
                return true;
            }
        };
    }

}
