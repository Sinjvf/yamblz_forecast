package com.example.kimichael.yamblz_forecast.utility;


import android.content.Context;
import android.content.SharedPreferences;

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
public class TemperatureFormatTest {

    private SharedPreferences sharedPrefs;
    private Context context;
    private PreferencesManager manager;
    @Before
    public void before() throws Exception {
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(context.getString(R.string.format_temperature)).thenReturn("%1.0f\\u00B0%s");
        Mockito.when(sharedPrefs.getString("temp_units", "Celsius")).thenReturn("Celsius");
        manager = new PreferencesManager(sharedPrefs);
    }


    @Test
    public void checkPreferencesInitialization() {
        assertNotNull(context);
        assertNotNull(sharedPrefs);
    }


    @Test
    public void celsiusTemperature() {
        double temp = 23.94;
        String expected = context.getString(R.string.format_temperature, temp, manager.getUnit());
        String celsius = Utility.formatTemperature(manager, context, temp);
        assertEquals(expected, celsius);
    }


}
