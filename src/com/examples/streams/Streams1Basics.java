package com.examples.streams;

import com.examples.domain.Person;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Streams1Basics {

    public static void main(String[] args) {
        //various different ways to construct a Stream:
        Stream.of(1, 2, 3, 4);

        int[] numArray = {1, 2, 3, 4};
        IntStream numbersFromArray = Arrays.stream(numArray);

        try {
            long numberOfLines =
                    Files.lines(Paths.get("README.md"), Charset.defaultCharset())
                            .count();
            System.out.println(String.format("README.md contains %s lines", numberOfLines));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /////////


        //Filter out any odd numbers, then multiply by itself, finally limit to two results and convert to a list
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> twoEvenSquares =
                numbers.stream()
                        .filter(n -> {//Filter out any odd numbers
                            System.out.println("filtering " + n);
                            return n % 2 == 0;
                        })
                        .map(n -> {//Multiply by itself
                            System.out.println("mapping " + n);
                            return n * n;
                        })
                        .limit(2)//Limit to two results
                        .collect(Collectors.toList()); //Finish and convert to a List
        twoEvenSquares.forEach(x -> System.out.print(x + ", "));
        System.out.println();


        List<Person> loadsOfPeople = Person.getLoadsOfPeople();

        //Simple filter then print
        loadsOfPeople.stream()
                .filter((it) -> it.getAge() >= 50)
                .forEach((it) ->
                        System.out.println("Found, " + it.getName()));

        //Map to a List of Names
        System.out.println("\nThe name of everyone in the List:");
        List<String> listOfNames = loadsOfPeople.stream()
                .map(x -> x.getName())
                .collect(Collectors.toList());
        listOfNames.forEach(x -> System.out.print(x + " "));
        System.out.println();

        //filter by age, sort by iq then map to name
        System.out.println("\nPeople younger than 50, sorted by IQ");
        List<String> youngerPeopleSortedByIq =
                loadsOfPeople
                        .stream()
                        .filter(x -> x.getAge() < 50)
                        .sorted(Comparator.comparing(Person::getIq).reversed())
                        .map(Person::getName)
                        .collect(Collectors.toList());
        youngerPeopleSortedByIq.forEach(x -> System.out.print(x + " "));
        System.out.println();


        //combined age of everyone
        int combinedAge =
                loadsOfPeople.stream()
                        .mapToInt(Person::getAge) //converts it to an IntStream
                        .sum(); //this HAS to be a specialised Stream
        System.out.println("The combined age of everyone is: " + combinedAge);


        //average age of everyone
        double averageAge =
                loadsOfPeople.stream()
                        .mapToInt(Person::getAge) //converts it to an IntStream
                        .average().getAsDouble(); //this HAS to be a specialised Stream
        System.out.println("The average age of everyone is: " + averageAge);

        //check if everyone in the list is over 18:
        boolean isOver18 =
                loadsOfPeople.stream()
                        .allMatch(t -> t.getAge() >= 18);
        System.out.println("Is everyone over 18: " + isOver18);

        //xml generator
        String xml =
                "<people data='lastname'>\n" +
                        loadsOfPeople.stream()
                                .map(it -> "\t<person>" + it.getName() + "</person>\n")
                                .reduce("", String::concat) //start with "" and append each mapped person (xml) using concat
                        + "</people>";
        System.out.println("XML:\n" + xml);


        //first taste of optional
        System.out.println("Find any Female: ");
        loadsOfPeople.stream()
                .filter(t -> t.getGender() == Person.Sex.FEMALE)
                .findAny()
                .ifPresent(System.out::println); //if not empty print it


        performanceTestStreams();
    }

    private static void performanceTestStreams() {
        //Lets test the performance adding 1+2+3+4+5+6.....
        long numberOfLongsToSum = 10000000;

        System.out.println("\nOld Style Loop:");
        Function<Long, Long> oldStyleIterativeSum = Streams1Basics::oldStyleIterativeSum;
        doWithPerformance(oldStyleIterativeSum, numberOfLongsToSum);


        //iterate takes the initial element then a function to apply to it to generate the next element
        Function<Long, Long> streamIterateBoxed =
                x -> Stream.iterate(1L, i -> i + 1).
                        limit(x). //limit the number of results
                        reduce(Long::sum).get(); //use reduce to sum all elements
        System.out.println("Boxed Iterate Stream:");
        doWithPerformance(streamIterateBoxed, numberOfLongsToSum);


        //same again but in parallel
        Function<Long, Long> streamParallelIterateBoxed =
                x -> Stream.iterate(1L, i -> i + 1).
                        parallel().
                        limit(x).
                        reduce(Long::sum).get();
        //iterate doesn't run in parallel too well, we can't calculate the next element until we have the previous, etc.
        System.out.println("Boxed Iterate Parallel Stream:");
        doWithPerformance(streamParallelIterateBoxed, numberOfLongsToSum);


        //lets use a LongStream to reduce boxing
        Function<Long, Long> streamIterateUnboxed =
                x -> LongStream.iterate(1L, i -> i + 1).
                        limit(x).
                        reduce(Long::sum).getAsLong();
        System.out.println("Unboxed Iterate Stream:");
        doWithPerformance(streamIterateUnboxed, numberOfLongsToSum);

        //now lets try range instead so we can run it in parallel more efficiently
        Function<Long, Long> streamRangeUnboxed =
                x -> LongStream.rangeClosed(1L, x).
                        reduce(Long::sum).getAsLong();
        System.out.println("Unboxed Range Stream:");
        doWithPerformance(streamRangeUnboxed, numberOfLongsToSum);

        //now lets try range instead so we can run it in parallel more efficiently
        Function<Long, Long> streamRangeParallelUnboxed =
                x -> LongStream.rangeClosed(1L, x).
                        parallel().
                        reduce(Long::sum).getAsLong();
        System.out.println("Unboxed Range Parallel Stream:");
        doWithPerformance(streamRangeParallelUnboxed, numberOfLongsToSum);
    }

    private static long oldStyleIterativeSum(long max) {
        long result = 0;

        for (long i = 0; i <= max; i++) {
            result += i;
        }

        return result;
    }


    private static long doWithPerformance(Function<Long, Long> stream, Long max) {
        Date start = new Date();

        System.out.println("Result" + stream.apply(max));

        Date end = new Date();

        long timeTaken = end.getTime() - start.getTime();
        System.out.println("Time Taken: " + timeTaken + "ms\n");

        return timeTaken;

    }
}

