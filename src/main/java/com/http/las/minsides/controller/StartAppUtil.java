package com.http.las.minsides.controller;

import com.http.las.minsides.shared.exceptions.StartException;
import com.http.las.minsides.shared.tools.StartTest;
import org.springframework.stereotype.Component;

@Component
class StartAppUtil {

    public StartAppUtil() throws StartException {
        startTests();
    }

    private void startTests() throws StartException {
        System.out.println("Test entities...");
        StartTest.test();
        System.out.println("Update db...");
        System.out.println("\nStarted;)");
    }
}
