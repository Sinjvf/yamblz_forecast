package com.example.kimichael.yamblz_forecast.utility;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Sinjvf on 06.08.2017.
 * test for parsing data from long and back to another String
 */

@RunWith(Parameterized.class)
public class ParseDateTest {

    private final String dateStrOld;
    private final String dateStrNew;
    private final Locale locale;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //valid input
                {"2017-08-06 18:00:00", "Sun, 06 Aug 18:00", Locale.ENGLISH},
                {"2017-08-08 18:00:00", "Tue, 08 Aug 18:00", Locale.ENGLISH},
                //NON valid input
                {"", null, Locale.ENGLISH},
                {null, null, Locale.ENGLISH},
        });
    }

    public ParseDateTest(String dateStrOld, String dateStrNew, Locale locale) {
        this.dateStrOld = dateStrOld;
        this.dateStrNew = dateStrNew;
        this.locale = locale;
    }

    @Test
    public void parseData() {
        String result = Utility.parceToStr(Utility.parceFromStr(dateStrOld, locale), locale);
        Assert.assertEquals(dateStrNew, result);
    }

}
