package com.back;

import com.back.standard.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AppTestRunner {
//    public static String run(String s) {
//        TestUtil.setInFromString(s);
//        ByteArrayOutputStream testOut = TestUtil.setOutToByteArray();
//        try{
//            App app = new App();
//            app.run();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        TestUtil.clearSetInFromString();
//        TestUtil.clearSetOutToByteArray();
//        return testOut.toString();
//    }

    public static String run(String s) {
//        debug
//        System.out.println(s);
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        String s2 = String.join(System.lineSeparator(),
                "등록",
                "현재를 사랑하라.",
                "작자미상",
                "종료"
        );
        System.out.println("test1\n" + s);
        System.out.println("test2");

        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(testOut);
        ByteArrayInputStream testIn = new ByteArrayInputStream((s2).getBytes());
        int i;
//        while((i = testIn.read()) != -1){
//            System.out.print((char)i);
//        }
        System.out.println();
        System.setIn(testIn);
        System.setOut(printOut);

        try {
            App app = new App();
            printOut.flush();
            app.run();
        } catch (Exception e) {
            e.printStackTrace(printOut);
        } finally {
            String result = testOut.toString();
            System.out.println(result);
            System.setIn(originalIn);
            System.setOut(originalOut);
            return result;
        }
    }
}
