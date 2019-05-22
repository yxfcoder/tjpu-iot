package com.tjpu.iot.util;

public class TJPUUtil {

    public static String electricityConvert(String electricityHex) {
        int electricityBound = parseHex4(electricityHex.substring(6, 10)) * 100;
        return String.valueOf((float) electricityBound / 10000);
    }

    public static int parseHex4(String num) {
        if (num.length() != 4) {
            throw new NumberFormatException("Wrong length: " + num.length() + ", must be 4.");
        }
        int ret = Integer.parseInt(num, 16);
        ret = ((ret & 0x8000) > 0) ? (ret - 0x10000) : (ret);
        return (int) ret;
    }
}
