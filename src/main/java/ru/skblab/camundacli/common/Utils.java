package ru.skblab.camundacli.common;

import java.util.Map;

public class Utils {

    public static Object[][] getArrayData(Map<String, String> data) {
        Object[][] res = new String[data.size()][2];
        int i = 0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            res[i][0] = entry.getKey();
            res[i][1] = entry.getValue();
            i++;
        }
        return res;
    }
}
