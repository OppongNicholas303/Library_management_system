package org.example.library_management_system.controllers;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;

public abstract  class JavaFxTestBase {
    @BeforeAll
    static void initializeJavaFx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown); // Start JavaFX toolkit
        latch.await(); // Wait for toolkit to initialize
    }
}
