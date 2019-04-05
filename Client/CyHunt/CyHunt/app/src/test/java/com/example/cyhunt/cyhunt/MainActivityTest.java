package com.example.cyhunt.cyhunt;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {
    @Test
    public void locationListTest() {
        String[] list = {"1","2","3"};
        MainActivity.setLocationList(list);

        assertArrayEquals(list, MainActivity.getLocationList());
    }
}
