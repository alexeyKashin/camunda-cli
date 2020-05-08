package ru.skblab.camundacli.common;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    void getArrayData() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("first", "first");
        data.put("second", "second");

        Object[][] array = Utils.getArrayData(data);
        assertEquals(2, array.length);
    }
}