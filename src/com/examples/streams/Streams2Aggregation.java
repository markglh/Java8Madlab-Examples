package com.examples.streams;

import com.examples.domain.Person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams2Aggregation {

    public static void main(String[] args) {

        List<Person> loadsOfPeople = Person.getLoadsOfPeople();

        //What if we want to clone each person?
        List<Stream<Person>> clonedPeople = loadsOfPeople.stream()
                .map(person -> Stream.of(person, person)) //We now have a Stream<Stream<Person>>???
                .collect(Collectors.toList());
        //what just happened?
        //map returns a Stream, which is added to the main Stream as a new element, meh!


        //If only there was a way around this? ... enter flatMap!
        List<Person> clonedPeople2 = loadsOfPeople.stream()
                .flatMap(person -> Stream.of(person, person.dolly())) //People are added to the main Stream
                .collect(Collectors.toList());
        System.out.println("Cloned People: " + clonedPeople2.toString());


        //Same again but giving us a specific Collection implementation!
        Set<Person> clonedPeopleSet = loadsOfPeople.stream()
                .flatMap(person -> Stream.of(person, person)) //People are added to the main Stream
                .collect(Collectors.toCollection(HashSet::new));
        System.out.println("Cloned People set: " + clonedPeople2.toString());



        //reducing() combines all elements in a stream by repetitively applying an operation until a result is produced
        /* reducing() takes three arguments:
        1) An initial value (it is returned if the stream is empty); in this case, it is 0.
        2) A function to apply to each element of a stream; in this case, we extract the value of each transaction.
        3) An operation to combine two values produced by the extracting function; in this case, we just add up the values.
        */
        int totalAgeUsingReduce = loadsOfPeople.stream().
                map(Person::getAge).
                reduce(0, (total, current) -> total + current);
        System.out.println("Total age: " + totalAgeUsingReduce);


        //Now without specifying the reduce starting value
        int youngestUsingReduce = loadsOfPeople.stream().
                map(Person::getAge).
                reduce((total, current) -> current < total ? current : total).
                get(); //returns an optional because we didn't specify starting value for reduce
        System.out.println("Age of youngest person: " + youngestUsingReduce);

    }
}

