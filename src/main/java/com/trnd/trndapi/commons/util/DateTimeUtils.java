package com.trnd.trndapi.commons.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    public static LocalDateTime getDateTimeAfterThirtyDaysAtElevenPM() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Add 30 days to the current date
        LocalDateTime thirtyDaysLater = now.plusDays(30);

        // Set the time to 11:00 PM on the date 30 days later
        LocalDateTime thirtyDaysLaterAtElevenPM = thirtyDaysLater
                .withHour(23)   // 11 PM
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return thirtyDaysLaterAtElevenPM;
    }
    public static ZonedDateTime getZonedDateTimeAfterThirtyDaysAtElevenPM(ZoneId zone) {
        if (zone == null) {
            zone = ZoneId.systemDefault();
        }
        // Get the current date and time in a specific time zone
        ZonedDateTime now = ZonedDateTime.now(zone);

        // Add 30 days to the current date and set the time to 11 PM
        ZonedDateTime thirtyDaysLaterAtElevenPM = now.plusDays(30)
                .withHour(23)   // 11 PM
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return thirtyDaysLaterAtElevenPM;
    }
}
