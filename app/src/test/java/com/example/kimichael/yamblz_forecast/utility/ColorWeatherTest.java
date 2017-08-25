package com.example.kimichael.yamblz_forecast.utility;


import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

/**
 * Created by Sinjvf on 13.08.2017.
 * tests getColorWeatherCondition utility method - result right weather color
 */

@RunWith(Parameterized.class)
public class ColorWeatherTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //valid input
                {200, R.color.storm},
                {232, R.color.storm},
                {781, R.color.storm},
                {300, R.color.rain},
                {321, R.color.rain},
                {500, R.color.rain},
                {504, R.color.rain},
                {520, R.color.rain},
                {531, R.color.rain},
                {600, R.color.rain},
                {622, R.color.rain},
                {511, R.color.rain},
                {701, R.color.rain},
                {761, R.color.rain},
                {800, R.color.clear},
                {801, R.color.light_clouds},
                {802, R.color.clouds},
                {804, R.color.clouds},
                //NON valid input
                {3490, R.color.clear},
                {-1, R.color.clear},
                {0, R.color.clear},
                {906, R.color.clear},
        });
    }

    private final int weatherId;
    private final int expectedResult;


    public ColorWeatherTest(int weatherId, int expectedResult) {
        this.weatherId = weatherId;
        this.expectedResult = expectedResult;
    }


    @Test
    public void weatherPicture() {
        int result = Utility.getColorWeatherCondition(weatherId);
        Assert.assertEquals(expectedResult, result);
    }


}
