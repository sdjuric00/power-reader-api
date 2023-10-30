package com.powerreaderapi.powerreaderapi.util;

import java.time.LocalDateTime;
import java.util.Random;

public class Helper {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 10;

    //Generates random serial number for device
    public static String generateSerialNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    //Checks if start time is after end time
    public static boolean checkStartTimeAfterEndTime(LocalDateTime startTime, LocalDateTime endTime) {

        return startTime.isAfter(endTime);
    }

}
