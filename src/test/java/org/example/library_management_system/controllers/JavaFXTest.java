package org.example.library_management_system.controllers;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;

public abstract class JavaFXTest {
    private static boolean jfxIsSetup;

    @BeforeAll
    public static void setupJavaFX() throws InterruptedException {
        if (!jfxIsSetup) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            latch.await();
            jfxIsSetup = true;
        }
    }
}