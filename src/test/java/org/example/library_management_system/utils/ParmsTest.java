package org.example.library_management_system.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParmsTest {

    @ParameterizedTest
    @ValueSource(ints = {2,4,6,8})
    void  valuess(int input){
        boolean results = input % 2 == 0;

        System.out.println(results);

        assertTrue(results);
    }
}
