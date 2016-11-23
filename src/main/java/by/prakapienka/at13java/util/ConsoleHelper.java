package by.prakapienka.at13java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String result = "";
        try {
            result = reader.readLine();
        } catch (IOException e) {}

        return result;
    }

    public static int readNumber() {
        int result = -1;
        try {
            result = Integer.parseInt(readString());
        } catch (Exception e) {

        }
        return result;
    }
}
