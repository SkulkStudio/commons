package com.skulkstudio.commons.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.util.Map.entry;

/**
 * Regular utilities for strings.
 * @since v1.0-SNAPSHOT
 */
public final class StringUtils {

    /**
     * Formats given seconds into a readable format.
     * e.g. 1 day 2 hours 3 minutes
     * @param seconds The seconds to format
     * @return A formatted {@link String} of seconds.
     */
    public static String formatSeconds(Integer seconds) {
        long day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hour = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        String formatted = "";
        if (day > 0) {
            formatted += day + " day";
            if (day > 1) formatted += "s";
        }
        if (hour > 0) {
            if (formatted.length() > 0) formatted += ", ";
            formatted += hour + " hour";
            if (hour > 1) formatted += "s";
        }
        if (minute > 0) {
            if (formatted.length() > 0) formatted += ", ";
            formatted += minute + " minute";
            if (minute > 1) formatted += "s";
        }
        if (second > 0) {
            if (formatted.length() > 0) formatted += ", ";
            formatted += second + " second";
            if (second > 1) formatted += "s";
        }
        return formatted;
    }

    /**
     * Get the closest match in a {@link Collection} by a given identifier.
     * @param haystack The collection of identifiers
     * @param needle The identifier to look for.
     * @param exact Whether to find an exact match or closest match
     * @return The closest matched string to the given identifier.
     */
    public static @Nullable String getClosestMatch(Collection<String> haystack, String needle, Boolean exact) {
        int delta = Integer.MAX_VALUE, curDelta;
        String found = null;
        needle = needle.toLowerCase();
        for (String value : haystack) {
            String lowercase = value.toLowerCase();
            if (exact) {
                if (lowercase.equals(needle)) return value;
                else continue;
            }
            if (lowercase.startsWith(needle)) {
                curDelta = lowercase.length() - needle.length();
                if (curDelta < delta) {
                    found = value;
                    delta = curDelta;
                }
                if (curDelta == 0) break;
            }
        }
        return found;
    }

    /**
     * Check whether a given {@link String} is numeric.
     * @param str The string to check.
     * @return Whether the given string is numeric.
     */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Converts a given string into seconds
     * @param time The timed format
     * @return The seconds the given string represents.
     */
    public static @NotNull Integer strToSeconds(String time) {
        Map<String, Integer> delimeters = Map.ofEntries(
                entry("y", 31556952),
                entry("M", 2592000),
                entry("w", 604800),
                entry("d", 86400),
                entry("h", 3600),
                entry("m", 60)
        );
        int total = 0;
        for (String delim : delimeters.keySet()) {
            String[] split = time.split(delim);
            if (split.length < 1) continue;
            if (!time.contains(delim) || !isNumeric(time.split(delim)[0])) continue;
            Integer value = Integer.valueOf(time.split(delim)[0]);
            total += value * delimeters.get(delim);
        }
        return total;
    }

    /**
     * Checks whether a given string is a {@link java.util.UUID}
     * @param val The value to check
     * @return Whether the string is a {@link java.util.UUID} or not.
     */
    public static boolean isUuid(String val) {
        Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        return UUID_REGEX.matcher(val).matches();
    }
}
