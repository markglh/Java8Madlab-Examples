package com.examples.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdasAndLists {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        Collections.addAll(strings, "Java", "7", "FTW");


        strings.forEach(x -> System.out.print(x + " ")); //Will output: Java 8 FTW


        System.out.println("\nLets update to Java 8 :) ...");
        strings.replaceAll(x -> x == "7" ? "8" : x); //similar to something we'll see later...
        strings.forEach(x -> System.out.print(x + " ")); //Will output: Java 8 WAT


        System.out.println("\nNow lets remove the version...");
        strings.removeIf(x -> x == "8");
        strings.forEach(x -> System.out.print(x + " ")); //Will output: Java 8 WAT

    }
}
