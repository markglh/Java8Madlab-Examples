package com.examples.streams;

import com.examples.domain.Person;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams3Collectors {

    public static void main(String[] args) {

        List<Person> loadsOfPeople = Person.getLoadsOfPeople();

        Optional<Person> smartestPerson = loadsOfPeople.stream().collect(Collectors.maxBy(Comparator.comparing(Person::getIq)));
        smartestPerson.ifPresent(x -> System.out.println("The smartest person is " + x.getName()));


        Map<Integer, List<Person>> peopleGroupedByAge =
                loadsOfPeople.stream().filter(x -> x.getIq() > 110).
                        collect(Collectors.groupingBy(Person::getAge));
        System.out.println("People Grouped By Age: " + peopleGroupedByAge);
        //{52=[Person{name='Damo', age=52, iq=113, gender=MALE}],
        // 60=[Person{name='Jess', age=60, iq=120, gender=FEMALE}, Person{name='Matt', age=60, iq=115, gender=FEMALE}],
        // 28=[Person{name='Dave', age=28, iq=190, gender=MALE}]}


        //Partition lets you split the list on a predicate, grouping by "true" or "false"
        Map<Boolean, List<Person>> peoplePartitionedByAge = loadsOfPeople.stream().filter(x -> x.getIq() > 110).
                collect(Collectors.partitioningBy(x -> x.getAge() > 55));
        System.out.println("People Partitioned By Age: " + peoplePartitionedByAge);
        //{false=[Person{name='Dave', age=28, iq=190, gender=MALE}, Person{name='Damo', age=52, iq=113, gender=MALE}],
        // true=[Person{name='Jess', age=60, iq=120, gender=FEMALE}, Person{name='Matt', age=60, iq=115, gender=FEMALE}]}


        //Multilevel groupings are also possible
        Map<Integer, Double> peopleGroupedBySexAndAverageAge = loadsOfPeople.stream().filter(x -> x.getIq() > 110).
                collect(Collectors.groupingBy(Person::getAge,
                        Collectors.averagingInt(Person::getIq))); //overloaded groupingBy that takes two params
        System.out.println("People Grouped By Age and average IQ: " + peopleGroupedBySexAndAverageAge);
        //{52=113.0, 60=117.5, 28=190.0}


        //Reducing collector is similar to the reduce method on Stream
        int totalAgeUsingReduce = loadsOfPeople.stream().
                map(Person::getAge).
                collect(Collectors.reducing(0, (total, current) -> total + current));
        System.out.println("Total age: " + totalAgeUsingReduce);


        //Now without specifying the reduce starting value
        int youngestUsingReduce = loadsOfPeople.stream().
                map(Person::getAge).
                collect(Collectors.reducing((total, current) -> current < total ? current : total)).
                get(); //returns an optional because we didn't specify starting value for reduce
        System.out.println("Age of youngest person: " + youngestUsingReduce);

        //the advantage is having reducing as a Collector is it's easier to chain with groupings and other collectors.


        //and finally...

        //Lets count the number of letters in a sentence, then group the counts to each letter
        Stream<String> words = Stream.of("Java", "8", "FTW");
        Map<String, Long> letterToCount =
                words.map(w -> w.split("")) //split into individual letters
                        //here we not have a Stream<String[]>, we want a Stream<String>, where each is a letter
                        .flatMap(Arrays::stream)
                                //Convert the String[] to a Stream, then flatten it so the elements get added to the main Stream
                        .collect(Collectors.groupingBy( //group elements into K->V format
                                Function.identity(), //this is the key, identity just returns the current item (the letter)
                                Collectors.counting())); //counting is a nice helper for, well, counting.

        //{a=2, T=1, F=1, v=1, W=1, 8=1, J=1}
        System.out.println("Counted leters: " + letterToCount.toString());

    }
}

