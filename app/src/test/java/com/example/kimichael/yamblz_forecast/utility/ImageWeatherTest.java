package com.example.kimichael.yamblz_forecast.utility;


import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

/**
 * Created by Sinjvf on 29.07.2017.
 * tests getImageForWeatherCondition utility method - result right weather icon
 */

@RunWith(Parameterized.class)
public class ImageWeatherTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //valid input
                {200, R.drawable.art_storm},
                {232, R.drawable.art_storm},
                {781, R.drawable.art_storm},
                {300, R.drawable.art_light_rain},
                {321, R.drawable.art_light_rain},
                {500, R.drawable.art_rain},
                {504, R.drawable.art_rain},
                {520, R.drawable.art_rain},
                {531, R.drawable.art_rain},
                {600, R.drawable.art_rain},
                {622, R.drawable.art_rain},
                {511, R.drawable.art_snow},
                {701, R.drawable.art_fog},
                {761, R.drawable.art_fog},
                {800, R.drawable.art_clear},
                {801, R.drawable.art_light_clouds},
                {802, R.drawable.art_clouds},
                {804, R.drawable.art_clouds},
                //NON valid input
                {3490, R.drawable.art_clear},
                {-1, R.drawable.art_clear},
                {0, R.drawable.art_clear},
                {906, R.drawable.art_clear},
        });
    }

    private final int weatherId;
    private final int expectedResult;


    public ImageWeatherTest(int weatherId, int expectedResult) {
        this.weatherId = weatherId;
        this.expectedResult = expectedResult;
    }


    @Test
    public void weatherPicture() {
        int result = Utility.getImageForWeatherCondition(weatherId);
        Assert.assertEquals(expectedResult, result);
    }


}
