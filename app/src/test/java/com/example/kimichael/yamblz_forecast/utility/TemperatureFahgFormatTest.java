package com.example.kimichael.yamblz_forecast.utility;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

/**
 * Created by Sinjvf on 29.07.2017.
 * tests formatTemperature utility method - result right format
 */

@RunWith(MockitoJUnitRunner.class)
public class TemperatureFahgFormatTest {

    private SharedPreferences sharedPrefs;
    private Context context;
    private Resources res;
    private PreferencesManager manager;
    int intervals[] = {3600, 7200, 10800, 18000, 86400};
    @Before
    public void before() throws Exception {
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        this.res = Mockito.mock(Resources.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(context.getString(R.string.format_temperature)).thenReturn("%1.0f\\u00B0%s");
        Mockito.when(sharedPrefs.getInt("temp_units", 0)).thenReturn(1);
        Mockito.when(res.getIntArray(R.array.interval_values)).thenReturn(intervals);
        Mockito.when(context.getResources()).thenReturn(res);
        manager = new PreferencesManager(context);
    }


    @Test
    public void checkPreferencesInitialization() {
        assertNotNull(context);
        assertNotNull(sharedPrefs);
    }


    @Test
    public void farTemperature() {
        double temp = 23.94;
       String expected = context.getString(R.string.format_temperature, temp, manager.getUnit());
        String celsius = Utility.formatTemperature(manager, context, temp);
        assertEquals(expected, celsius);
    }
}
