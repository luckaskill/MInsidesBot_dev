package com.las.bots.minsides.controller.tools;

import com.las.bots.minsides.shared.shared.exception.StartException;
import com.las.bots.minsides.shared.shared.tools.StartTest;
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
