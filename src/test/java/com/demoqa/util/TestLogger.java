package com.demoqa.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amitaarya
 */
public class TestLogger {

        private static final List<String> TEST_LOGS = new ArrayList<>();


        public static void log(Object log) {
            String logString = String.valueOf(log);
            System.out.println(log);
            log(logString);
        }

        public static void log(String log) {
            System.out.println(log);
            TEST_LOGS.add(log);
        }

        public static String getAllLogsAsString() {
            StringBuilder stringBuilder = new StringBuilder();
            TEST_LOGS.forEach(log -> stringBuilder.append(log).append('\n'));
            return stringBuilder.toString();
        }

        public static void clearLogs() {
            TEST_LOGS.clear();
        }

    }
