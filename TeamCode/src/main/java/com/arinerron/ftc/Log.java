package com.arinerron.ftc;

import java.util.*;

public class Log {
    public static final int MAX_SIZE = 5;

    private static List<LogData> stack = new ArrayList<>();
    private static String data = "";

    private static List<LogData> getStack() {
        return stack;
    }

    public static String generate() {
        StringBuilder b = new StringBuilder();

        for(LogData s : stack)
            b.append(s.getContent() + "\n");

        return b.toString();
    }

    public static void write(String tag, String content) {
        if(getStack().size() >= MAX_SIZE)
            getStack().remove(0);
        getStack().add(new LogData(tag, content));

        data = generate();
    }

    public static String getString() {
        return data;
    }
}
