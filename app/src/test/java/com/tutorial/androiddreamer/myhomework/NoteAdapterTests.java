package com.tutorial.androiddreamer.myhomework;

import org.junit.Assert;
import org.junit.Test;

import com.tutorial.androiddreamer.myhomework.Helpers.DateUtil;


public class NoteAdapterTests {
    @Test
    public void convertUnixTimeStampInReadableForm_isCorrect() {
        long timeInMillis = 1_541_322_212_788L;
        String expected = "2018-11-04";
        Assert.assertEquals(DateUtil.convertUnixTimeStampInReadableForm(timeInMillis), expected);
    }
}
