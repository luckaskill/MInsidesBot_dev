package com.http.las.minsides.controller;

import com.http.las.minsides.shared.exceptions.StartException;
import com.http.las.minsides.shared.tools.StartTest;

class StartAppUtil {
    static void preStart() throws StartException {
        System.out.println("Test entities");
        StartTest.test();
        System.out.println("\n\n\nUpdate db");
    }
}
