package com.http.las.minsides.controller.tools;

import com.http.las.minsides.shared.exceptions.StartException;
import com.http.las.minsides.shared.tools.StartTest;
import org.springframework.stereotype.Component;

@Component
class StartApp {

    private StartApp() throws StartException {
        startTests();
    }

    private void startTests() throws StartException {
        System.out.println("Test entities...");
        StartTest.test();
        System.out.println("Entities are ok.\n");
    }
}
