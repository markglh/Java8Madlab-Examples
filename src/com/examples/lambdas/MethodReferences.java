package com.examples.lambdas;

import com.examples.domain.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.examples.lambdas.PersonPredicates.printAllPeopleMatchingPredicate;
import static com.examples.lambdas.PersonPredicates.printAndImproveAllPeopleMatchingPredicate;

public class MethodReferences {

    public static <T, R> List<R> map(Function<T, R> function, List<T> source) {
        List<R> destiny = new ArrayList<>();
        for (T item : source) {
            R value = function.apply(item);
            destiny.add(value);
        }
        return destiny;
    }

    public static void main(String[] args) {
        List<Person> loadsOfPeople = Person.getLoadsOfPeople();

        //Type 1: Reference to a static method
        printAllPeopleMatchingPredicate(loadsOfPeople, PersonPredicates::isPersonOlderThan50);
        printAndImproveAllPeopleMatchingPredicate(loadsOfPeople,
                PersonPredicates::isPersonOlderThan50,
                PersonPredicates::improvePerson);
        //The methods must be class level (static)


        //Type 2: Reference to an instance method of a specific object
        List<String> strings = new ArrayList<>();
        Collections.addAll(strings, "Java", "8", "FTW");
        strings.forEach(System.out::print); //print is a method on the "out" PrintStream object

        //this example doesn't make much sense but illustrates the point
        printAllPeopleMatchingPredicate(loadsOfPeople, new PersonPredicates()::isPersonYoungerThan50);


        //Type 3: Reference to an instance method of an arbitrary object supplied as part of the call (and passed to the method reference)
        List<Integer> namesOfPeople = map(Person::getAge, loadsOfPeople);
        List<Integer> namesOfPeople2 = map(person -> person.getAge(), loadsOfPeople); //lambda version


        //Type 4: Reference to a constructor
        //Similar to the previous example, howevers let's just create a new object!
        List<String> digits = Arrays.asList("1", "2", "3", "4", "5");
        List<Integer> numbers = map(Integer::new, digits);
        List<Integer> numbers2 = map(s -> new Integer(s), digits);
    }
}

