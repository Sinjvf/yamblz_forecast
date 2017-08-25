package com.example.kimichael.yamblz_forecast.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.booleanThat;

/**
 * Created by Sinjvf on 30.07.2017.
 * Preferenses Manager tests
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PreferencesManagerTest {

    private SharedPreferences sharedPrefs;
    private Context context;
    private Resources res;
    private PreferencesManager manager;
    int intervals[] = {3600, 7200, 10800, 18000, 86400};


    @Before
    public void setup() {
        String SP_NAME = RuntimeEnvironment.application.getPackageName() + "_preferences";
        sharedPrefs = RuntimeEnvironment.
                application.getSharedPreferences(SP_NAME, 0);
        sharedPrefs.edit().clear().apply();
        this.context = Mockito.mock(Context.class);
        this.res = Mockito.mock(Resources.class);
        Mockito.when(res.getIntArray(R.array.interval_values)).thenReturn(intervals);
        Mockito.when(context.getResources()).thenReturn(res);
        manager = new PreferencesManager(context, sharedPrefs);
    }

    @Test
    public void defaultUpdateInterval() {
        int DEFAULT_UPDATE_INTERVAL = 3600;
        assertEquals(DEFAULT_UPDATE_INTERVAL, manager.getInterval());
    }


    @Test
    public void defaultTempUnit() {
        assertEquals(true, manager.isTempCelsius());
    }


    @Test
    public void updateInterval() {
        assertEquals(false, manager.containInterval());
        int UPDATE_INTERVAL_POS = 2;
        int UPDATE_INTERVAL = intervals[2];
        manager.saveInterval(UPDATE_INTERVAL_POS);
        assertEquals(UPDATE_INTERVAL, manager.getInterval());
        assertEquals(UPDATE_INTERVAL_POS, manager.getIntervalPos());

        assertEquals(true, manager.containInterval());
    }

    @Test
    public void tempUnit() {
        int TEMP_UNIT = 1;
        manager.saveTempUnit(TEMP_UNIT);
        assertEquals(false, manager.isTempCelsius());
        assertEquals(TEMP_UNIT, manager.getTempPosition());
    }
    @Test
    public void intervalChanged() {
        boolean key1 = manager.isIntervalChanged("sync_interval");
        boolean key2 = manager.isIntervalChanged("sync_interval_pos");
        assertEquals(true, key1);
        assertEquals(false, key2);
    }

}
